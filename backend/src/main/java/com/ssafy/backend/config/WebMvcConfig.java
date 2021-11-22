package com.ssafy.backend.config;

import com.ssafy.backend.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns(Arrays.asList("/user/**", "/broadcast/**", "/broadcastReplay/**", "/mattermost/**", "/schedule/**", "/track/**", "/trackSubject/**", "/gifticon/**", "/quiz/**","/stomp/**"))
                .excludePathPatterns(Arrays.asList("/user/login", "/user/searchId/{userId}/{phone}", "/user/searchPW/{email}/{userId}/{phone}", "/user/resetPW"));

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("accessToken", "msg", "content-disposition");
    }
}
