package com.ssafy.backend.interceptor;

import com.ssafy.backend.service.user.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (request.getMethod().equals("OPTIONS")) return true;
        else {
            String accessToken = request.getHeader("accessToken");
            map = jwtService.validAccessToken(accessToken);
        }

        response.setStatus((int) map.get("status"));
        response.setHeader("msg", (String) map.get("msg"));

        if ((int) map.get("status") == 200) return true;
        else if ((int) map.get("status") == 201) {
            response.setHeader("accessToken", (String) map.get("accessToken"));
            return true;
        }

        return false;
    }
}
