package com.ssafy.backend.service.user;

import java.util.Map;

public interface JwtService {
    String createToken(int userId);
    String createAccessToken(int userId);
    String createRefreshToken();
    String decodeToken(String token);
    Map<String, Object> validAccessToken(String accessToken);
    Map<String, Object> validRefreshToken(String accessToken);
}
