package com.ssafy.backend.service;

import com.ssafy.backend.dto.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
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
