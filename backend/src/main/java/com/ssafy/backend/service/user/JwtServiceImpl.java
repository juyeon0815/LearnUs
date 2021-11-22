package com.ssafy.backend.service.user;

import com.ssafy.backend.service.RedisService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private RedisService redisService;
    @Value("${jwt.salt}")
    private String salt;
    @Value("${jwt.accExpmin}")
    private Long accExpmin;
    @Value("${jwt.refExpmin}")
    private Long refExpmin;

    // JWT 토큰 생성
    @Override
    public String createToken(int userId) {
        String accessToken = createAccessToken(userId);
        String refreshToken = createRefreshToken();

        List<String> refreshInfo = new ArrayList<>();
        refreshInfo.add(String.valueOf(userId));
        refreshInfo.add(refreshToken);

        redisService.setListValue(accessToken, refreshInfo, refExpmin);
        return accessToken;
    }

    @Override
    public String createAccessToken(int userId) {
        JwtBuilder builder = Jwts.builder();

        // header 설정
        builder.setHeaderParam("typ", "JWT");

        // payload 설정
        return builder.setSubject("accessToken")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * accExpmin))
                .claim("UserId", userId)
                .signWith(SignatureAlgorithm.HS256, salt.getBytes()) // signature 설정
                .compact(); // 직렬화 처리
    }

    public String createRefreshToken() {
        JwtBuilder builder = Jwts.builder();

        // header 설정
        builder.setHeaderParam("typ", "JWT");

        // payload 설정
        return builder.setSubject("refreshToken")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * refExpmin))
                .signWith(SignatureAlgorithm.HS256, salt.getBytes())
                .compact();
    }

    @Override
    public String decodeToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(token).getBody();
            return "valid";
        } catch (ExpiredJwtException expireE) {
            return "expire";
        } catch (JwtException jwtE) {
            return "invalid";
        }
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 토큰의 유효성 + 만료일자 확인
    @Override
    public Map<String, Object> validAccessToken(String accessToken) {
        Map<String, Object> resultMap = new HashMap<>();

        String validAccessToken = decodeToken(accessToken);
        if (validAccessToken.equals("invalid")) {
            resultMap.put("status", 403);
            resultMap.put("msg", "AccessToken Not Valid.");
        } else if (validAccessToken.equals("valid")) { // 유효성 확인
            resultMap.put("status", 200);
            resultMap.put("msg", "AccessToken Valid.");
        } else { // 만료 시
            resultMap = validRefreshToken(accessToken);
        }

        return resultMap;
    }

    @Override
    public Map<String, Object> validRefreshToken(String accessToken) {
        List<String> refreshInfo = redisService.getListValue(accessToken);
        Map<String, Object> resultMap = new HashMap<>();

        // refreshToken size = 0 -> refreshToken 만료
        if (refreshInfo.size() == 0) {
            resultMap.put("status", 401);
            resultMap.put("msg", "RefreshToken has been expired");
        } else {
            String validRefreshToken = decodeToken(refreshInfo.get(1));

            if (validRefreshToken.equals("invalid")) {
                resultMap.put("status", 403);
                resultMap.put("msg", "RefreshToken Not Valid");
            } else if (validRefreshToken.equals("valid")) {
                // accessToken 새로 발급
                String newToken = createAccessToken(Integer.parseInt(refreshInfo.get(0)));
                resultMap.put("status", 201);
                resultMap.put("msg", "AccessToken Updated");
                resultMap.put("accessToken", newToken);
                redisService.update(accessToken, newToken);
            }
        }

        return resultMap;
    }

    public Map<String, Object> getInfo(String jwtToken) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return claims.getBody();
    }
}
