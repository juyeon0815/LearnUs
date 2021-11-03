package com.ssafy.backend.service;

import com.ssafy.backend.dao.*;
import com.ssafy.backend.dto.*;
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
    private TrackDao trackDao;
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

    @Override
    public void insert(BroadcastInfo broadcastInfo) {
        // 방송 생성
        Broadcast broadcast = Broadcast.builder().streamingKey(UUID.randomUUID().toString())
                .thumbnailUrl(broadcastInfo.getThumbnailUrl())
                .broadcastDate(broadcastInfo.getBroadcastDate())
                .title(broadcastInfo.getTitle())
                .teacher(broadcastInfo.getTeacher())
                .liveYn("Y")
                .description(broadcastInfo.getDescription()).build();
        broadcastDao.save(broadcast);

        // 참석 명단 생성 및 트랙 목록 생성
        for (int i = 0; i < broadcastInfo.getTrackList().size(); i++) {
            String trackName = broadcastInfo.getTrackList().get(i);
            Track track = trackDao.findTRACKByTrackName(trackName);
            BroadcastTrack broadcastTrack = BroadcastTrack.builder().broadcast(broadcast).track(track).build();
            broadcastTrackDao.save(broadcastTrack);
            List<User> userList = userDao.findUserByTrack(track);
            for (int j = 0; j < userList.size(); j++) {
                User user = userList.get(j);
                Attendance attendance = Attendance.builder().user(user).broadcast(broadcast).broadcastTrack(broadcastTrack).attend("N").build();
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
    }

    @Override
    public void update(BroadcastInfo broadcastInfo) {
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
                Track track = trackDao.findTRACKByTrackName(broadcastInfo.getTrackList().get(i));
                // 해당 방송 객체와 트랙 객체와 일치하는 방송 트랙 객체 구하기
                BroadcastTrack broadcastTrack = broadcastTrackDao.findBroadcastTracksByBroadcastAndTrack(broadcast, track);
                // 기존의 방송 트랙에 존재하지 않다면 -> 새로 추가해줘야됨
                if (broadcastTrack == null) {
                    // 새로 추가
                    BroadcastTrack saveBroadcastTrack = BroadcastTrack.builder().broadcast(broadcast).track(track).build();
                    broadcastTrackDao.save(saveBroadcastTrack);
                    // 참가 명단 추가
                    List<User> userList = userDao.findUserByTrack(track);
                    for (int j = 0; j < userList.size(); j++) {
                        User user = userList.get(j);
                        Attendance attendance = Attendance.builder().user(user).broadcast(broadcast).broadcastTrack(saveBroadcastTrack).attend("N").build();
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
    }

    @Override
    public void delete(int broadcastId) {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
        broadcastDao.delete(broadcast);
    }

    @Override
    public List<BroadcastInfo> getBroadcastAll() {
        List<Broadcast> broadcastList = broadcastDao.findBroadcastsByLiveYn("Y");

        List<BroadcastInfo> broadcastInfoList = new ArrayList<>();
        for (int i = 0; i < broadcastList.size(); i++) {
            Broadcast broadcast = broadcastList.get(i);

            Map<String, String> textbookMap = new HashMap<>();
            List<String> trackList = new ArrayList<>();

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
                trackList.add(broadcastTrack.getTrack().getTrackName());
            }

            // BroadcastInfo 객체에 정보 넣어서 보내주기
            BroadcastInfo broadcastInfo = BroadcastInfo.builder().broadcastId(broadcast.getBroadcastId())
                    .streamingKey(broadcast.getStreamingKey())
                    .thumbnailUrl(broadcast.getThumbnailUrl()).broadcastDate(broadcast.getBroadcastDate())
                    .title(broadcast.getTitle()).teacher(broadcast.getTeacher()).description(broadcast.getDescription())
                    .textbook(textbookMap).trackList(trackList).build();

            broadcastInfoList.add(broadcastInfo);
        }

        return broadcastInfoList;
    }

    @Override
    public Map<String, List<Attendance>> getAttendance(int broadcastId) {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
        Map<String, List<Attendance>> map = new HashMap<>();

        List<Attendance> attendanceList = attendanceDao.findAttendancesByBroadcast(broadcast);
        for (int i = 0; i < attendanceList.size(); i++) {
            List<Attendance> saveAttendanceList = new ArrayList<>();
            User user = attendanceList.get(i).getUser();
            String region_class = user.getRegion() + "_" + user.getClassNo() + "반";
            if (map.containsKey(region_class)) saveAttendanceList = map.get(region_class);
            saveAttendanceList.add(attendanceList.get(i));
            map.put(region_class, saveAttendanceList);
        }

        return map;
    }

    @Override
    public void attend(int broadcastId, int userId) {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
        User user = userDao.findUserByUserId(userId);
        Attendance attendance = attendanceDao.findAttendanceByBroadcastAndUser(broadcast, user);
        LocalDateTime localDateTime = LocalDateTime.now();
        attendance.setAttendanceDate(localDateTime);
        attendance.setAttend("Y");
        attendanceDao.save(attendance);
    }

    @Override
    public void start(int broadcastId) {
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

        StringBuilder sb = new StringBuilder();
        // 날짜 format
        String formatDate = broadcast.getBroadcastDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        // 메시지 작성
        sb.append("### ").append(formatDate).append(" 방송 안내\n")
                .append(broadcast.getBroadcastDate().getHour() + "시 라이브 방송 [").append(broadcast.getTitle() + "]")
                .append("가 곧 시작합니다!\n모두 접속해주세요~ :ssafyface:");

        Iterator<Mattermost> iterator = mattermostSet.iterator();
        while (iterator.hasNext()) {
            Mattermost mattermost = iterator.next();
            mattermostMessageService.send(sb.toString(), mattermost.getPathName(), mattermost.getWebhook());
        }
    }

    @Override
    public void endAttendance(int broadcastId) {
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
            Mattermost mattermost = mattermostDao.findMattermostByTrackSetting(trackSetting);
            if (mattermost != null) mattermostList.add(mattermost);
        }

        // 미참석자 명단 가져오기
        List<Attendance> attendanceList = attendanceDao.findAttendancesByBroadcastAndAttend(broadcast, "N");
        StringBuilder sb = new StringBuilder();
        // 날짜 format
        String formatDate = broadcast.getBroadcastDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

        sb.append("### [").append(formatDate).append("] ").append(broadcast.getTitle()).append(" 방송 미참석 명단\n")
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
    }

    @Override
    public void endAttendanceDownload(int broadcastId, HttpServletResponse response) throws IOException {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);

        List<Attendance> attendanceList = attendanceDao.findAttendancesByBroadcastAndAttend(broadcast, "N");
        excelService.createExcelAttendance(broadcast, attendanceList, response);
    }

    @Override
    public void endGifticon(int broadcastId) {
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

        StringBuilder sb = new StringBuilder();
        // 날짜 format
        String formatDate = broadcast.getBroadcastDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

        sb.append("### [").append(formatDate).append("] ").append(broadcast.getTitle()).append(" 방송 기프티콘 당첨자 명단\n")
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
    }

    @Override
    public void endGifticonDownload(int broadcastId, HttpServletResponse response) throws IOException {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);

        List<Gifticon> gifticonList = gifticonDao.findGifticonsByBroadcast(broadcast);
        excelService.createExcelGifticon(broadcast, gifticonList, response);
    }

    //다시보기 데이터 생성
    @Override
    public void endReplayInsert(int broadcastId, String autoUploadYn) {
        //방송 객체를 불러와 스트림키를 확인한다.
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);

        broadcast.setLiveYn("N");
        broadcastDao.save(broadcast);

        //스트림키로 cdn url를 생성한다
        String broadcastReplyUrl = "";
        if (autoUploadYn.equals("Y")) broadcastReplyUrl = "https://d31f0osw72yf0h.cloudfront.net/" + broadcast.getStreamingKey() + "/index.m3u8";

        BroadcastReplay broadcastReplay = BroadcastReplay.builder().replayUrl(broadcastReplyUrl).openYn(autoUploadYn)
                        .broadcast(broadcast).build();

        broadcastReplayDao.save(broadcastReplay);
    }
}
