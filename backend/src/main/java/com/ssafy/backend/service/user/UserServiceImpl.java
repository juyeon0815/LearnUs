package com.ssafy.backend.service.user;

import com.ssafy.backend.dao.*;
import com.ssafy.backend.dto.*;
import com.ssafy.backend.service.ExcelService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private TrackSettingDao trackSettingDao;
    @Autowired
    private TrackDao trackDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private BroadcastDao broadcastDao;
    @Autowired
    private AttendanceDao attendanceDao;

    @Override
    public Map<String, Object> login(String email, String password, HttpServletResponse res) {
        try {
            Map<String, Object> resultMap = new HashMap<>();

            try {
                User loginUser = userDao.findUserByEmail(email);
                if (loginUser.getStatusCode().equals("N")) {
                    resultMap.put("fail", 403);
                    return resultMap;
                }
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                if (loginUser != null && encoder.matches(password, loginUser.getPassword())) {
                    String token = jwtService.createToken(loginUser.getUserId());

                    res.setHeader("accessToken", token);
                    resultMap.put("userId", loginUser.getUserId());

                } else {
                    resultMap.put("msg", "Login failed");
                }
            } catch (RuntimeException e) {
                resultMap.put("msg", e.getMessage());
            }
            return resultMap;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean completion(int comOrdinalNo) {
        try {
            // 트랙 셋팅 +1
            List<TrackSetting> trackSettingList = trackSettingDao.findAll();
            for (int i = 1; i < trackSettingList.size(); i++) {
                TrackSetting now = trackSettingList.get(i);
                now.setOrdinalNo(now.getOrdinalNo() + 1);
                trackSettingDao.save(now);
            }

            // 교육생 수료 처리
            List<User> userList = userDao.findUserByOrdinalNoAndStatusCode(comOrdinalNo, "Y");
            Track track = trackDao.findTrackByTrackId(1);
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                user.setStatusCode("N");
                user.setTrack(track);
                userDao.save(user);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean insert(MultipartFile excelFile) throws IOException{
        try {
            Sheet worksheet = excelService.excelCheck(excelFile);

            // 기존 1학기 기수 가져오기
            int originOrdinalNo = trackSettingDao.findTrackSettingBySemester(1).getOrdinalNo();
            int originOrdinalNo2 = trackSettingDao.findTrackSettingBySemester(2).getOrdinalNo();

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                Row row = worksheet.getRow(i);

                if (i == 1) {
                    int newOrdinalNo = (int) row.getCell(0).getNumericCellValue();
                    // 현재 1학기 기수보다 더 높은 기수가 들어오면 새로운 기수
                    if (newOrdinalNo > originOrdinalNo) {
                        if (!completion(originOrdinalNo2)) return false;
                    }
                }

                Track nowTrack = trackDao.findTrackByTrackName(row.getCell(4).getStringCellValue());

                User user = new User();

                user.setOrdinalNo((int) row.getCell(0).getNumericCellValue());
                user.setUserId(Integer.parseInt(row.getCell(1).getStringCellValue()));
                user.setName(row.getCell(2).getStringCellValue());
                user.setEmail(row.getCell(3).getStringCellValue());
                user.setRegion(row.getCell(5).getStringCellValue());
                user.setClassNo((int) row.getCell(6).getNumericCellValue());
                user.setPhone(row.getCell(7).getStringCellValue());
                user.setProfileUrl("https://mann-goofy.s3.ap-northeast-2.amazonaws.com/profiles/default.jpg");
                user.setStatusCode("Y");
                user.setTrack(nowTrack);

                String pw = "S" + user.getEmail() + user.getOrdinalNo();
                user.setPassword(passwordEncoder.encode(pw));
                String nickName = user.getRegion() + "_" + user.getClassNo() + "반_" + user.getName();
                user.setNickname(nickName);

                userDao.save(user);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateList(MultipartFile excelFile) throws IOException {
        try {
            Sheet worksheet = excelService.excelCheck(excelFile);

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                Row row = worksheet.getRow(i);

                User user = userDao.findUserByUserId(Integer.parseInt(row.getCell(1).getStringCellValue()));
                user.setRegion(row.getCell(5).getStringCellValue());
                user.setClassNo((int) row.getCell(6).getNumericCellValue());
                user.setPhone(row.getCell(7).getStringCellValue());

                Track nowTrack = trackDao.findTrackByTrackName(row.getCell(4).getStringCellValue());

                String nickName = user.getRegion() + "_" + user.getClassNo() + "반_" + user.getName();
                user.setNickname(nickName);
                user.setTrack(nowTrack);

                userDao.save(user);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateUser(User updateUser) {
        try {
            User user = userDao.findUserByUserId(updateUser.getUserId());
            user.setName(updateUser.getName());
            user.setOrdinalNo(updateUser.getOrdinalNo());
            user.setRegion(updateUser.getRegion());
            user.setClassNo(updateUser.getClassNo());
            user.setProfileUrl(updateUser.getProfileUrl());
            user.setPhone(updateUser.getPhone());
            user.setTrack(updateUser.getTrack());
            user.setStatusCode(updateUser.getStatusCode());
            String nickName = updateUser.getNickname();
            if (user.getStatusCode().equals("Y")) nickName = user.getRegion() + "_" + user.getClassNo() + "반_" + user.getName();
            user.setNickname(nickName);
            Track track = trackDao.findTrackByTrackName(updateUser.getTrack().getTrackName());
            if (track != null) user.setTrack(track);

            userDao.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updatePW(int userId, String originPW, String newPW) {
        try {
            User user = userDao.findUserByUserId(userId);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (!encoder.matches(originPW, user.getPassword())) return false;

            user.setPassword(passwordEncoder.encode(newPW));
            userDao.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Map<String, List<User>> getTrackUser(List<String> trackList) {
        try {
            Map<String, List<User>> map = new HashMap<>();
            List<User> userList = new ArrayList<>();
            for (int i = 0; i < trackList.size(); i++) {
                Track trackNow = trackDao.findTrackByTrackName(trackList.get(i));
                // 현재 트랙에 해당되는 학생들 뽑기
                userList = userDao.findUserByTrack(trackNow);
                map.put(trackList.get(i), userList);
            }
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<Integer, List<User>> getUserAll() {
        try {
            Map<Integer, List<User>> map = new HashMap<>();

            // 현재 교육 중인 기수 불러오기
            List<TrackSetting> trackSettingList = trackSettingDao.findAll();

            for (int i = 1; i < trackSettingList.size(); i++) {
                int nowOrdinalNo = trackSettingList.get(i).getOrdinalNo();
                List<User> userList = userDao.findUserByOrdinalNoAndStatusCode(nowOrdinalNo, "Y");
                map.put(nowOrdinalNo, userList);
            }
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User getUser(String type, String search) {
        try {
            if (type.equals("id")) return userDao.findUserByUserId(Integer.parseInt(search));
            else return userDao.findUserByName(search);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getEmail(int userId, String phone) {
        try {
            User user = userDao.findUserByUserIdAndPhone(userId, phone);
            if (user != null) return user.getEmail();
            else return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean getPwCheck(String email, int userId, String phone) {
        try {
            User user = userDao.findUserByUserIdAndEmailAndPhone(userId, email, phone);
            if (user != null) return true;
            else return false;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean resetPW(int userId, String newPW) {
        try {
            User user = userDao.findUserByUserId(userId);
            user.setPassword(passwordEncoder.encode(newPW));
            userDao.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> getRegionList() {
        try {
            List<String> regionList = userDao.findRegions();
            Collections.sort(regionList, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            return regionList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean userChatSend(int userId, int broadcastId) {
        try {
            User user = userDao.findUserByUserId(userId);
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
            Attendance attendance = attendanceDao.findAttendanceByBroadcastAndUser(broadcast, user);

            attendance.setChatScore(attendance.getChatScore() + 1);
            attendanceDao.save(attendance);

            broadcast.setChatCount(broadcast.getChatCount() + 1);
            broadcastDao.save(broadcast);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
