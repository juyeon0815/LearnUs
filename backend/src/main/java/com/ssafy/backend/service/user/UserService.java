package com.ssafy.backend.service.user;

import com.ssafy.backend.dto.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> login(String email, String password, HttpServletResponse res);
    void insert(MultipartFile excelFile) throws IOException;
    void updateList(MultipartFile excelFile) throws IOException;
    void updateUser(User updateUser);
    boolean updatePW(int userId, String originPW, String newPW);
    Map<String, List<User>> getTrackUser(List<String> trackList);
    Map<Integer, List<User>> getUserAll();
    User getUser(String type, String search);
    String getEmail(int userId, String phone);
    boolean getPwCheck(String email, int userId, String phone);
    boolean resetPW(int userId, String newPW);

    void userChatSend(int userId, int broadcastId);
}
