package com.ssafy.backend.service;

import java.util.Map;

public interface JwtService {
    public String createToken(int userId);
    public String createAccessToken(int userId);
    public String createRefreshToken();
    public String decodeToken(String token);
    public Map<String, Object> validAccessToken(String accessToken);
    public Map<String, Object> validRefreshToken(String accessToken);
}
