package com.ssafy.backend.service.user;

import com.ssafy.backend.dao.TrackDao;
import com.ssafy.backend.dao.TrackSettingDao;
import com.ssafy.backend.dao.UserDao;
import com.ssafy.backend.dto.Track;
import com.ssafy.backend.dto.TrackSetting;
import com.ssafy.backend.dto.User;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> login(String email, String password, HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            User loginUser = userDao.findUserByEmail(email);
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
    }

    @Override
    public void insert(MultipartFile excelFile) throws IOException{
        Sheet worksheet = excelService.excelCheck(excelFile);

        // 기존 1학기 기수 가져오기
        int originOrdinalNo = trackSettingDao.findTrackSettingBySemester(1).getOrdinalNo();

        for (int i=1;i<worksheet.getPhysicalNumberOfRows();i++) {
            Row row = worksheet.getRow(i);

            if (i==1) {
                int newOrdinalNo = (int) row.getCell(0).getNumericCellValue();
                // 현재 1학기 기수보다 더 높은 기수가 들어오면 새로운 기수
                if (newOrdinalNo > originOrdinalNo) {
                    List<TrackSetting> trackSettingList = trackSettingDao.findAll();
                    for (int j=1;j<trackSettingList.size();j++) {
                        TrackSetting now = trackSettingList.get(j);
                        now.setOrdinalNo(now.getOrdinalNo()+1);
                        trackSettingDao.save(now);
                    }
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
            user.setType(1);
            user.setStatusCode("Y");
            user.setTrack(nowTrack);

            String pw = "S" + user.getEmail() + user.getOrdinalNo();
            user.setPassword(passwordEncoder.encode(pw));
            String nickName = user.getRegion() + "_" + user.getClassNo() + "반_" + user.getName();
            user.setNickname(nickName);

            userDao.save(user);
        }
    }

    @Override
    public void updateList(MultipartFile excelFile) throws IOException {
        Sheet worksheet = excelService.excelCheck(excelFile);

        for (int i=1;i<worksheet.getPhysicalNumberOfRows();i++) {
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
    }

    @Override
    public void updateUser(User updateUser) {
        User user = userDao.findUserByUserId(updateUser.getUserId());
        user.setName(updateUser.getName());
        user.setOrdinalNo(updateUser.getOrdinalNo());
        user.setRegion(updateUser.getRegion());
        user.setClassNo(updateUser.getClassNo());
        user.setProfileUrl(updateUser.getProfileUrl());
        user.setPhone(updateUser.getPhone());
        user.setTrack(updateUser.getTrack());
        user.setStatusCode(updateUser.getStatusCode());
        String nickName = user.getRegion() + "_" + user.getClassNo() + "반_" + user.getName();
        user.setNickname(nickName);
        Track track = trackDao.findTrackByTrackName(updateUser.getTrack().getTrackName());
        if (track != null) user.setTrack(track);

        userDao.save(user);
    }

    @Override
    public boolean updatePW(int userId, String originPW, String newPW) {
        User user = userDao.findUserByUserId(userId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(originPW, user.getPassword())) return false;

        user.setPassword(passwordEncoder.encode(newPW));
        userDao.save(user);
        return true;
    }

    @Override
    public Map<String, List<User>> getTrackUser(List<String> trackList) {
        Map<String, List<User>> map = new HashMap<>();
        List<User> userList = new ArrayList<>();
        for (int i=0;i<trackList.size();i++) {
            Track trackNow = trackDao.findTrackByTrackName(trackList.get(i));
            // 현재 트랙에 해당되는 학생들 뽑기
            userList = userDao.findUserByTrack(trackNow);
            map.put(trackList.get(i), userList);
        }
        return map;
    }

    @Override
    public Map<Integer, List<User>> getUserAll() {
        Map<Integer, List<User>> map = new HashMap<>();

        // 현재 교육 중인 기수 불러오기
        List<TrackSetting> trackSettingList = trackSettingDao.findAll();

        for (int i=1;i<trackSettingList.size();i++) {
            int nowOrdinalNo = trackSettingList.get(i).getOrdinalNo();
            List<User> userList = userDao.findUserByOrdinalNo(nowOrdinalNo);
            map.put(nowOrdinalNo, userList);
        }
        return map;
    }

    @Override
    public User getUser(String type, String search) {
        if (type.equals("id")) return userDao.findUserByUserId(Integer.parseInt(search));
        else return userDao.findUserByName(search);
    }

    @Override
    public String getEmail(int userId, String phone) {
        User user = userDao.findUserByUserIdAndPhone(userId, phone);
        if (user != null) return user.getEmail();
        else return null;
    }

    @Override
    public boolean getPwCheck(String email, int userId, String phone) {
        User user = userDao.findUserByUserIdAndEmailAndPhone(userId, email, phone);
        if (user != null) return true;
        return false;
    }

    @Override
    public boolean resetPW(int userId, String newPW) {
        User user = userDao.findUserByUserId(userId);
        if (user == null) return false;
        user.setPassword(passwordEncoder.encode(newPW));
        userDao.save(user);
        return true;
    }
}
