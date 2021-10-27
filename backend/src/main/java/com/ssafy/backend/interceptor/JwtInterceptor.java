package com.ssafy.backend.interceptor;

import com.ssafy.backend.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getMethod().equals("OPTIONS")) return true;
        else {
            String token = request.getHeader("access-token");
            if (token != null && token.length() > 0) {
                if (jwtTokenProvider.validateToken(token)) return true;
                else throw new RuntimeException("토큰이 만료 됐습니다.");
            } else {
                throw new RuntimeException("인증 토큰이 없습니다.");
            }
        }
    }
}
