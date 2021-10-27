package com.ssafy.backend.service;

import com.ssafy.backend.dao.TrackDao;
import com.ssafy.backend.dao.TrackSettingDao;
import com.ssafy.backend.dao.UserDao;
import com.ssafy.backend.dto.Track;
import com.ssafy.backend.dto.TrackSetting;
import com.ssafy.backend.dto.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    private JwtServiceImpl jwtTokenProvider;

    public static Sheet excel(MultipartFile excelFile) throws IOException{
        String extension = FilenameUtils.getExtension(excelFile.getOriginalFilename());

        if (!extension.equals("xlsx") && !extension.equals("xls")) throw new IOException("엑셀 파일만 업로드 가능");

        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(excelFile.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(excelFile.getInputStream());
        }

        return workbook.getSheetAt(0);
    }

    @Override
    public Map<String, Object> login(String email, String password, HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            User loginUser = userDao.findUserByEmail(email);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (loginUser != null && encoder.matches(password, loginUser.getPassword())) {
                String token = jwtTokenProvider.createToken(loginUser.getUserId());

                res.setHeader("accessToken", token);

                resultMap.putAll(jwtTokenProvider.getInfo(token));

                resultMap.put("status", 200);

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
        Sheet worksheet = excel(excelFile);

        // 기존에 존재하는 기수들이 있다면 +1씩
        List<User> userList = userDao.findAll();
        if (userList.size()>100) {
            List<TrackSetting> trackSettingList = trackSettingDao.findAll();
            for (int i=1;i<trackSettingList.size();i++) {
                TrackSetting now = trackSettingList.get(i);
                now.setOrdinalNo(now.getOrdinalNo()+1);
                trackSettingDao.save(now);
            }
        }

        for (int i=1;i<worksheet.getPhysicalNumberOfRows();i++) {
            Row row = worksheet.getRow(i);

            User user = new User();
            user.setOrdinalNo((int) row.getCell(0).getNumericCellValue());
            user.setUserId((int) row.getCell(1).getNumericCellValue());
            user.setName(row.getCell(2).getStringCellValue());
            user.setEmail(row.getCell(3).getStringCellValue());
            user.setRegion(row.getCell(5).getStringCellValue());
            user.setClassNo((int) row.getCell(6).getNumericCellValue());
            user.setPhone(row.getCell(7).getStringCellValue());

            user.setProfileUrl("");
            Track nowTrack = trackDao.findTRACKByName(row.getCell(4).getStringCellValue());

            String pw = "S" + user.getEmail() + user.getOrdinalNo();
            user.setPassword(passwordEncoder.encode(pw));
            String nickName = user.getRegion() + "_" + user.getClassNo() + "반_" + user.getName();
            user.setNickname(nickName);
            user.setTrack(nowTrack);
            user.setType(1);
            user.setStatusYn("Y");

            userDao.save(user);
        }
    }

    @Override
    public void updateList(MultipartFile excelFile) throws IOException {
        Sheet worksheet = excel(excelFile);

        for (int i=1;i<worksheet.getPhysicalNumberOfRows();i++) {
            Row row = worksheet.getRow(i);

            User user = userDao.findUserByUserId((int) row.getCell(0).getNumericCellValue());

            user.setRegion(row.getCell(4).getStringCellValue());
            user.setClassNo((int) row.getCell(5).getNumericCellValue());
            user.setPhone(row.getCell(6).getStringCellValue());

            Track nowTrack = trackDao.findTRACKByName(row.getCell(3).getStringCellValue());

            String nickName = user.getRegion() + "_" + user.getClassNo() + "반_" + user.getName();
            user.setNickname(nickName);
            user.setTrack(nowTrack);

            userDao.save(user);
        }
    }

    @Override
    public void updateUser(User updateUser) {
        userDao.save(updateUser);
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
            Track trackNow = trackDao.findTRACKByName(trackList.get(i));
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
        return true;
    }
}
