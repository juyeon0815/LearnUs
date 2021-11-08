package com.ssafy.backend.service.user;

import com.ssafy.backend.dto.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    public Map<String, Object> login(String email, String password, HttpServletResponse res);
    public void insert(MultipartFile excelFile) throws IOException;
    public void updateList(MultipartFile excelFile) throws IOException;
    public void updateUser(User updateUser);
    public boolean updatePW(int userId, String originPW, String newPW);
    public Map<String, List<User>> getTrackUser(List<String> trackList);
    public Map<Integer, List<User>> getUserAll();
    public User getUser(String type, String search);
    public String getEmail(int userId, String phone);
    public boolean getPwCheck(String email, int userId, String phone);
    public boolean resetPW(int userId, String newPW);
}
