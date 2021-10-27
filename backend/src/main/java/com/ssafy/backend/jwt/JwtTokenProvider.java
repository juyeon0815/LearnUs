package com.ssafy.backend.jwt;

import com.ssafy.backend.dto.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.expmin}")
    private Long expireMin;

    // JWT 토큰 생성
    public String createToken(User user) {
        final JwtBuilder builder = Jwts.builder();

        // header 설정
        builder.setHeaderParam("typ", "JWT");

        // payload 설정
        builder.setSubject("로그인 토큰")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
                .claim("User", user);

        // signature 설정
        builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

        // 직렬화 처리
        final String jwtToken = builder.compact();

        return jwtToken;
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
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
