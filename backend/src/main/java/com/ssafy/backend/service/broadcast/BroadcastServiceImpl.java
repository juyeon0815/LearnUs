package com.ssafy.backend.service.broadcast;

import com.ssafy.backend.dao.*;
import com.ssafy.backend.dto.*;
import com.ssafy.backend.dto.info.BroadcastInfo;
import com.ssafy.backend.dto.info.ChatInfo;
import com.ssafy.backend.service.AwardService;
import com.ssafy.backend.service.ExcelService;
import com.ssafy.backend.service.RedisService;
import com.ssafy.backend.service.mattermost.MattermostMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BroadcastServiceImpl implements BroadcastService {

    @Autowired
    private BroadcastDao broadcastDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BroadcastTrackDao broadcastTrackDao;
    @Autowired
    private AttendanceDao attendanceDao;
    @Autowired
    private TextbookDao textbookDao;
    @Autowired
    private MattermostTrackDao mattermostTrackDao;
    @Autowired
    private MattermostMessageService mattermostMessageService;
    @Autowired
    private MattermostDao mattermostDao;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private GifticonDao gifticonDao;
    @Autowired
    private BroadcastReplayDao broadcastReplayDao;
    @Autowired
    private BroadcastReplayOrdinalDao broadcastReplayOrdinalDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AwardService awardService;

    @Override
    public boolean insert(BroadcastInfo broadcastInfo) {
        try {
            String thumbnailUrl = broadcastInfo.getThumbnailUrl();
            if (thumbnailUrl == null || thumbnailUrl.length() == 0)
                thumbnailUrl = "https://mann-goofy.s3.ap-northeast-2.amazonaws.com/thumbnails/default.jpg";
            // 방송 생성
            Broadcast broadcast = Broadcast.builder().streamingKey(UUID.randomUUID().toString())
                    .thumbnailUrl(thumbnailUrl)
                    .broadcastDate(broadcastInfo.getBroadcastDate())
                    .title(broadcastInfo.getTitle())
                    .teacher(broadcastInfo.getTeacher())
                    .liveCode("C")
                    .description(broadcastInfo.getDescription()).build();
            broadcastDao.save(broadcast);

            // 참석 명단 생성 및 트랙 목록 생성
            for (int i = 0; i < broadcastInfo.getTrackList().size(); i++) {
                Track track = broadcastInfo.getTrackList().get(i);
                BroadcastTrack broadcastTrack = BroadcastTrack.builder().broadcast(broadcast).track(track).build();
                broadcastTrackDao.save(broadcastTrack);
                List<User> userList = userDao.findUsersByTrackAndStatusCode(track, "Y");
                for (int j = 0; j < userList.size(); j++) {
                    User user = userList.get(j);
                    Attendance attendance = Attendance.builder().user(user).broadcast(broadcast).broadcastTrack(broadcastTrack).attend("N").gifticonYn("N").build();
                    attendanceDao.save(attendance);
                }
            }

            // 교재 생성
            Iterator<String> keys = broadcastInfo.getTextbook().keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = broadcastInfo.getTextbook().get(key);
                Textbook textbook = Textbook.builder().name(key).textbookUrl(value).broadcast(broadcast).build();
                textbookDao.save(textbook);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(BroadcastInfo broadcastInfo) {
        try {
            // 방송 관련 변경
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastInfo.getBroadcastId());
            broadcast.setThumbnailUrl(broadcastInfo.getThumbnailUrl());
            broadcast.setBroadcastDate(broadcastInfo.getBroadcastDate());
            broadcast.setTitle(broadcastInfo.getTitle());
            broadcast.setTeacher(broadcastInfo.getTeacher());
            broadcast.setDescription(broadcastInfo.getDescription());
            broadcastDao.save(broadcast);

            // 트랙 수정이 있을 때만 수정
            if (broadcastInfo.getTrackList().size() > 0) {
                // 참석 명단 및 트랙 목록 변경 ( 방송 객체로 가지고 있는거 가져와서 일치하지 않으면 삭제, 일치하면 냅두기 )
                List<BroadcastTrack> broadcastTrackList = broadcastTrackDao.findBroadcastTracksByBroadcast(broadcast);

                // 기존 트랙 목록과 비교 ( 수정된 트랙 목록에 기존에 있는 트랙이 없다면 삭제 )
                for (int i = 0; i < broadcastTrackList.size(); i++) {
                    // 수정된 트랙 목록 ( 트랙 이름만 존재 ) 와 기존의 트랙 목록 이름과 비교하여 일치하지 않다면 -> 삭제
                    if (!broadcastInfo.getTrackList().contains(broadcastTrackList.get(i).getTrack().getTrackName())) {
                        broadcastTrackDao.delete(broadcastTrackList.get(i)); // 방송 트랙 삭제 시 참석 명단도 함께 삭제됨
                    }
                }

                // 수정된 트랙 목록과 비교 ( 기존 트랙 목록에 수정된 트랙이 없다면 추가 )
                for (int i = 0; i < broadcastInfo.getTrackList().size(); i++) {
                    // 트랙 리스트 이름과 일치하는 트랙 객체 구하기
                    Track track = broadcastInfo.getTrackList().get(i);
                    // 해당 방송 객체와 트랙 객체와 일치하는 방송 트랙 객체 구하기
                    BroadcastTrack broadcastTrack = broadcastTrackDao.findBroadcastTracksByBroadcastAndTrack(broadcast, track);
                    // 기존의 방송 트랙에 존재하지 않다면 -> 새로 추가해줘야됨
                    if (broadcastTrack == null) {
                        // 새로 추가
                        BroadcastTrack saveBroadcastTrack = BroadcastTrack.builder().broadcast(broadcast).track(track).build();
                        broadcastTrackDao.save(saveBroadcastTrack);
                        // 참가 명단 추가
                        List<User> userList = userDao.findUsersByTrackAndStatusCode(track, "Y");
                        for (int j = 0; j < userList.size(); j++) {
                            User user = userList.get(j);

                            Gifticon gifticon = gifticonDao.findGifticonByUserAndBroadcast(user, broadcast);
                            String gifticonYn = gifticon == null?"N":"Y";
                            Attendance attendance = Attendance.builder().user(user).broadcast(broadcast).broadcastTrack(saveBroadcastTrack).attend("N").gifticonYn(gifticonYn).build();
                            attendanceDao.save(attendance);
                        }
                    }
                }
            }

            // 교재 변경 있을 때만 수정 ( 교재 전체 삭제 후 새로 추가 )
            if (broadcastInfo.getTextbook().size() > 0) {
                // 교재 전체 삭제
                List<Textbook> textbookList = textbookDao.findTextbooksByBroadcast(broadcast);
                for (int i = 0; i < textbookList.size(); i++) {
                    textbookDao.delete(textbookList.get(i));
                }

                // 교재 새로 추가
                Iterator<String> keys = broadcastInfo.getTextbook().keySet().iterator();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = broadcastInfo.getTextbook().get(key);
                    Textbook textbook = Textbook.builder().name(key).textbookUrl(value).broadcast(broadcast).build();
                    textbookDao.save(textbook);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(int broadcastId) {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
            broadcastDao.delete(broadcast);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<BroadcastInfo> getBroadcastAll(String liveCode, String accessToken) {
        try {
            // 객체 정보 가져오기
            List<String> userInfo = redisService.getListValue(accessToken);
            User user = userDao.findUserByUserId(Integer.parseInt(userInfo.get(0)));

            List<Broadcast> broadcastList = new ArrayList<>();
            if (user.getStatusCode().equals("A")) {
                broadcastList = broadcastDao.findBroadcastsByLiveCodeOrderByBroadcastDateDesc(liveCode);
            } else {
                // 객체에 연관된 방송만 보여주기
                Track track = user.getTrack();

                List<BroadcastTrack> broadcastTrackList = broadcastTrackDao.findBroadcastTracksByTrack(track);
                for (int i=0;i<broadcastTrackList.size();i++) {
                    BroadcastTrack broadcastTrack = broadcastTrackList.get(i);
                    Broadcast broadcast = broadcastTrack.getBroadcast();
                    if (broadcast.getLiveCode().equals(liveCode)) broadcastList.add(broadcast);
                }

                // 방송 정렬
                Collections.sort(broadcastList, (o1, o2)-> {
                    return o2.getBroadcastDate().compareTo(o1.getBroadcastDate());
                });
            }

            List<BroadcastInfo> broadcastInfoList = new ArrayList<>();
            for (int i = 0; i < broadcastList.size(); i++) {
                Broadcast broadcast = broadcastList.get(i);

                Map<String, String> textbookMap = new HashMap<>();
                List<Track> trackList = new ArrayList<>();

                List<Textbook> textbookList = textbookDao.findTextbooksByBroadcast(broadcast);
                List<BroadcastTrack> broadcastTrackList = broadcastTrackDao.findBroadcastTracksByBroadcast(broadcast);

                // Broadcast에 해당하는 교재 정보 가져오기
                for (int j = 0; j < textbookList.size(); j++) {
                    Textbook textbook = textbookList.get(j);
                    textbookMap.put(textbook.getName(), textbook.getTextbookUrl());
                }

                // Broadcast에 해당하는 트랙 정보 가져오기
                for (int j = 0; j < broadcastTrackList.size(); j++) {
                    BroadcastTrack broadcastTrack = broadcastTrackList.get(j);
                    trackList.add(broadcastTrack.getTrack());
                }

                // BroadcastInfo 객체에 정보 넣어서 보내주기
                BroadcastInfo broadcastInfo = BroadcastInfo.builder().broadcastId(broadcast.getBroadcastId())
                        .streamingKey(broadcast.getStreamingKey())
                        .thumbnailUrl(broadcast.getThumbnailUrl()).broadcastDate(broadcast.getBroadcastDate())
                        .title(broadcast.getTitle()).teacher(broadcast.getTeacher()).description(broadcast.getDescription())
                        .textbook(textbookMap).trackList(trackList).liveCode(broadcast.getLiveCode()).chatCount(broadcast.getChatCount()).build();

                broadcastInfoList.add(broadcastInfo);
            }

            return broadcastInfoList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BroadcastInfo getBroadcast(int broadcastId) {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);

            Map<String, String> textbookMap = new HashMap<>();
            List<Track> trackList = new ArrayList<>();

            List<Textbook> textbookList = textbookDao.findTextbooksByBroadcast(broadcast);
            List<BroadcastTrack> broadcastTrackList = broadcastTrackDao.findBroadcastTracksByBroadcast(broadcast);

            // Broadcast에 해당하는 교재 정보 가져오기
            for (int i = 0; i < textbookList.size(); i++) {
                Textbook textbook = textbookList.get(i);
                textbookMap.put(textbook.getName(), textbook.getTextbookUrl());
            }

            // Broadcast에 해당하는 트랙 정보 가져오기
            for (int i = 0; i < broadcastTrackList.size(); i++) {
                BroadcastTrack broadcastTrack = broadcastTrackList.get(i);
                trackList.add(broadcastTrack.getTrack());
            }

            BroadcastInfo broadcastInfo = BroadcastInfo.builder().broadcastId(broadcastId)
                    .streamingKey(broadcast.getStreamingKey())
                    .thumbnailUrl(broadcast.getThumbnailUrl()).broadcastDate(broadcast.getBroadcastDate())
                    .title(broadcast.getTitle()).teacher(broadcast.getTeacher()).description(broadcast.getDescription())
                    .textbook(textbookMap).trackList(trackList).liveCode(broadcast.getLiveCode()).chatCount(broadcast.getChatCount()).build();

            return broadcastInfo;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<String, List<Attendance>> getAttendance(int broadcastId) {
        try {
            Map<String, List<Attendance>> map = new HashMap<>();

            List<Attendance> attendanceList = attendanceDao.findAttendancesByBroadcastOrderByUserId(broadcastId);
            for (int i = 0; i < attendanceList.size(); i++) {
                List<Attendance> saveAttendanceList = new ArrayList<>();
                User user = attendanceList.get(i).getUser();
                String region_class = user.getRegion() + "_" + user.getClassNo() + "반";
                if (map.containsKey(region_class)) saveAttendanceList = map.get(region_class);
                saveAttendanceList.add(attendanceList.get(i));
                map.put(region_class, saveAttendanceList);
            }

            return map;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean attend(int broadcastId, int userId) {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
            User user = userDao.findUserByUserId(userId);
            Attendance attendance = attendanceDao.findAttendanceByBroadcastAndUser(broadcast, user);
            LocalDateTime localDateTime = LocalDateTime.now();
            attendance.setAttendanceDate(localDateTime);
            attendance.setAttend("Y");
            attendanceDao.save(attendance);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean start(int broadcastId) {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);

            broadcast.setLiveCode("Y");
            broadcastDao.save(broadcast);

            // 해당 방송과 연관된 트랙 가져오기
            List<BroadcastTrack> broadcastTrackList = broadcastTrackDao.findBroadcastTracksByBroadcast(broadcast);
            Set<Mattermost> mattermostSet = new HashSet<>();
            for (int i = 0; i < broadcastTrackList.size(); i++) {
                Track track = broadcastTrackList.get(i).getTrack();
                // 트랙들과 연관된 매터모스트 가져오기
                List<MattermostTrack> mattermostTrackList = mattermostTrackDao.findMattermostTracksByTrack(track);
                for (int j = 0; j < mattermostTrackList.size(); j++) {
                    Mattermost mattermost = mattermostTrackList.get(j).getMattermost();
                    // 중복 방지
                    if (!mattermostSet.contains(mattermost)) mattermostSet.add(mattermost);
                }
            }

            StringBuilder sb = new StringBuilder();
            // 날짜 format
            String formatDate = broadcast.getBroadcastDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
            // 메시지 작성
            sb.append("@here\n").append("### :learnus: ").append(formatDate).append(" 라이브 방송 안내 :learnus:\n")
                    .append(broadcast.getBroadcastDate().getHour() + "시 라이브 방송 ```").append(broadcast.getTitle() + "```")
                    .append("가 곧 시작합니다!\n모두 접속해주세요~ :breeze_san:");

            Iterator<Mattermost> iterator = mattermostSet.iterator();
            while (iterator.hasNext()) {
                Mattermost mattermost = iterator.next();
                mattermostMessageService.send(sb.toString(), mattermost.getPathName(), mattermost.getWebhook());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int isAttend(int broadcastId) {
        try {
            String attend = redisService.getValue("attendance" + broadcastId);
            if (attend != null) return 1;
            return 2;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Map<String, List<Attendance>> end(int broadcastId, int type) {
        try {
            // 퀴즈왕
            List<Attendance> quizKingList = attendanceDao.findQuizKing(broadcastId);
            // 참여왕
            List<Attendance> chatKingList = attendanceDao.findChatKing(broadcastId);

            Map<String, List<Attendance>> map = new HashMap<>();
            map.put("quiz", quizKingList);
            map.put("chat", chatKingList);

            if (type == 0) {
                awardService.insert(broadcastId, chatKingList, quizKingList);

                redisService.delete("viewer"+broadcastId);
                redisService.delete("chat"+broadcastId);
            }

            return map;
        } catch (Exception e) {
            return null;
        }
    }

    public void attendanceSort(List<Attendance> sortList) {
        Collections.sort(sortList, new Comparator<Attendance>() {
            @Override
            public int compare(Attendance o1, Attendance o2) {
                if (o1.getUser().getOrdinalNo() == o2.getUser().getOrdinalNo()) { // 기수가 같다면
                    if (o1.getUser().getRegion().equals(o2.getUser().getRegion())) { // 지역이 같다면
                        if (o1.getUser().getClassNo() == o2.getUser().getClassNo()) { // 반이 같다면
                            // 아이디 정렬
                            return Integer.compare(o1.getUser().getUserId(), o2.getUser().getUserId());
                        }
                        // 반 정렬
                        return Integer.compare(o1.getUser().getClassNo(), o2.getUser().getClassNo());
                    }
                    // 지역 정렬
                    return o1.getUser().getRegion().compareTo(o2.getUser().getRegion());
                }
                // 기수 정렬
                return Integer.compare(o1.getUser().getOrdinalNo(), o2.getUser().getOrdinalNo());
            }
        });
    }

    public void gifticonSort(List<Gifticon> sortList) {
        Collections.sort(sortList, new Comparator<Gifticon>() {
            @Override
            public int compare(Gifticon o1, Gifticon o2) {
                if (o1.getUser().getOrdinalNo() == o2.getUser().getOrdinalNo()) { // 기수가 같다면
                    if (o1.getUser().getRegion().equals(o2.getUser().getRegion())) { // 지역이 같다면
                        if (o1.getUser().getClassNo() == o2.getUser().getClassNo()) { // 반이 같다면
                            // 아이디 정렬
                            return Integer.compare(o1.getUser().getUserId(), o2.getUser().getUserId());
                        }
                        // 반 정렬
                        return Integer.compare(o1.getUser().getClassNo(), o2.getUser().getClassNo());
                    }
                    // 지역 정렬
                    return o1.getUser().getRegion().compareTo(o2.getUser().getRegion());
                }
                // 기수 정렬
                return Integer.compare(o1.getUser().getOrdinalNo(), o2.getUser().getOrdinalNo());
            }
        });
    }

    @Override
    public boolean endAttendance(int broadcastId) {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);

            // 메시지 보낼 MM 선정
            List<Mattermost> mattermostList = new ArrayList<>(); // 메시지 보낼 MM list
            Set<TrackSetting> trackSettingSet = new HashSet<>(); // 기수 저장할 set
            List<BroadcastTrack> broadcastTrackList = broadcastTrackDao.findBroadcastTracksByBroadcast(broadcast);

            for (int i = 0; i < broadcastTrackList.size(); i++) {
                TrackSetting trackSetting = broadcastTrackList.get(i).getTrack().getTrackSubject().getTrackSetting();
                if (!trackSettingSet.contains(trackSetting)) trackSettingSet.add(trackSetting);
            }

            Iterator<TrackSetting> iterator = trackSettingSet.iterator();
            while (iterator.hasNext()) {
                TrackSetting trackSetting = iterator.next();
                List<Mattermost> mattermostTrackSettingList = mattermostDao.findMattermostsByTrackSetting(trackSetting);
                for (int i=0;i<mattermostTrackSettingList.size();i++) {
                    Mattermost mattermost = mattermostTrackSettingList.get(i);
                    mattermostList.add(mattermost);
                }
            }

            // 미참석자 명단 가져오기
            List<Attendance> attendanceList = attendanceDao.findAttendancesByBroadcastAndAttend(broadcast, "N");

            // 미참석자 명단 정렬
            attendanceSort(attendanceList);

            StringBuilder sb = new StringBuilder();
            // 날짜 format
            String formatDate = broadcast.getBroadcastDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

            sb.append("### :breeze_shin: [").append(formatDate).append("] ```").append(broadcast.getTitle()).append("``` 방송 미참석 명단 :breeze_shin: \n")
                    .append("|기수|학번|이름|지역|반\n").append("|---|------|-----|----|---|\n");

            for (int i = 0; i < attendanceList.size(); i++) {
                User user = attendanceList.get(i).getUser();
                sb.append("|").append(user.getOrdinalNo()).append("|")
                        .append(user.getUserId()).append("|")
                        .append(user.getName()).append("|")
                        .append(user.getRegion()).append("|")
                        .append(user.getClassNo()).append("|").append("\n");
            }

            // 메시지 전송
            for (int i = 0; i < mattermostList.size(); i++) {
                Mattermost mattermost = mattermostList.get(i);
                mattermostMessageService.send(sb.toString(), mattermost.getPathName(), mattermost.getWebhook());
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean endAttendanceDownload(int broadcastId, HttpServletResponse response) throws IOException {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);

            List<Attendance> attendanceList = attendanceDao.findAttendancesByBroadcastAndAttend(broadcast, "N");
            // 명단 정렬
            attendanceSort(attendanceList);
            if (!excelService.createExcelAttendance(broadcast, attendanceList, response)) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean endGifticon(int broadcastId) {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);

            // 해당 방송과 연관된 트랙 가져오기
            List<BroadcastTrack> broadcastTrackList = broadcastTrackDao.findBroadcastTracksByBroadcast(broadcast);
            Set<Mattermost> mattermostSet = new HashSet<>();
            for (int i = 0; i < broadcastTrackList.size(); i++) {
                Track track = broadcastTrackList.get(i).getTrack();
                // 트랙들과 연관된 매터모스트 가져오기
                List<MattermostTrack> mattermostTrackList = mattermostTrackDao.findMattermostTracksByTrack(track);
                for (int j = 0; j < mattermostTrackList.size(); j++) {
                    Mattermost mattermost = mattermostTrackList.get(j).getMattermost();
                    // 중복 방지
                    if (!mattermostSet.contains(mattermost)) mattermostSet.add(mattermost);
                }
            }

            // 기프티콘 명단 가져오기
            List<Gifticon> gifticonList = gifticonDao.findGifticonsByBroadcast(broadcast);

            // 기프티콘 명단 정렬
            gifticonSort(gifticonList);

            StringBuilder sb = new StringBuilder();
            // 날짜 format
            String formatDate = broadcast.getBroadcastDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

            sb.append("### :breeze_flower: [").append(formatDate).append("] ```").append(broadcast.getTitle()).append("``` 방송 기프티콘 당첨자 명단 :breeze_flower:\n")
                    .append("|기수|학번|이름|지역|반\n").append("|---|------|-----|----|---|\n");

            for (int i = 0; i < gifticonList.size(); i++) {
                User user = gifticonList.get(i).getUser();
                sb.append("|").append(user.getOrdinalNo()).append("|")
                        .append(user.getUserId()).append("|")
                        .append(user.getName()).append("|")
                        .append(user.getRegion()).append("|")
                        .append(user.getClassNo()).append("|").append("\n");
            }

            Iterator<Mattermost> iterator = mattermostSet.iterator();
            while (iterator.hasNext()) {
                Mattermost mattermost = iterator.next();
                mattermostMessageService.send(sb.toString(), mattermost.getPathName(), mattermost.getWebhook());
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean endGifticonDownload(int broadcastId, HttpServletResponse response) throws IOException {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);

            List<Gifticon> gifticonList = gifticonDao.findGifticonsByBroadcast(broadcast);
            // 기프티콘 명단 정렬
            gifticonSort(gifticonList);
            if (!excelService.createExcelGifticon(broadcast, gifticonList, response)) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //다시보기 데이터 생성
    @Override
    public boolean endReplayInsert(int broadcastId, String autoUploadYn) {
        try {
            //방송 객체를 불러와 스트림키를 확인한다.
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);

            broadcast.setLiveCode("N");
            broadcastDao.save(broadcast);

            //스트림키로 cdn url를 생성한다
            String broadcastReplyUrl = "";
            if (autoUploadYn.equals("Y"))
                broadcastReplyUrl = "https://d31f0osw72yf0h.cloudfront.net/" + broadcast.getStreamingKey() + ".m3u8";

            BroadcastReplay broadcastReplay = BroadcastReplay.builder().replayUrl(broadcastReplyUrl).openYn(autoUploadYn)
                    .broadcast(broadcast).build();

            broadcastReplayDao.save(broadcastReplay);

            // 방송 객체에서 관련 트랙 가져오기
            List<BroadcastTrack> broadcastTrackList = broadcastTrackDao.findBroadcastTracksByBroadcast(broadcast);
            Set<Integer> ordinalSet = new HashSet<>();
            // 방송과 관련된 기수 가져오기
            for (int i = 0; i < broadcastTrackList.size(); i++) {
                BroadcastTrack broadcastTrack = broadcastTrackList.get(i);
                TrackSetting trackSetting = broadcastTrack.getTrack().getTrackSubject().getTrackSetting();
                if (!ordinalSet.contains(trackSetting.getOrdinalNo())) ordinalSet.add(trackSetting.getOrdinalNo());
            }

            // 방송 다시보기 기수 객체 추가
            Iterator<Integer> iterator = ordinalSet.iterator();
            while (iterator.hasNext()) {
                BroadcastReplayOrdinal broadcastReplayOrdinal = BroadcastReplayOrdinal.builder().ordinalNo(iterator.next())
                        .broadcastReplay(broadcastReplay).build();

                broadcastReplayOrdinalDao.save(broadcastReplayOrdinal);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ChatInfo> getChatInfoList(int broadcastId) {
        try {
            List<ChatInfo> chatInfoList = redisService.getChatInfoValue("chat" + broadcastId);
            return chatInfoList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<String, Integer> getAttendanceAfter(int broadcastId) {
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put("totalAttend", attendanceDao.findTotalAttendCount(broadcastId));
            map.put("attend", attendanceDao.findCompleteAttendCount(broadcastId));

            return map;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> getBroadcastAndBroadcastReplay(int broadcastId) {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
            BroadcastReplay broadcastReplay = broadcastReplayDao.findBroadcastReplayByBroadcast(broadcast);

            Map<String, Object> map = new HashMap<>();
            map.put("broadcast", broadcast);
            map.put("broadcastReplay", broadcastReplay);
            return map;
        } catch (Exception e) {
            return null;
        }
    }
}
