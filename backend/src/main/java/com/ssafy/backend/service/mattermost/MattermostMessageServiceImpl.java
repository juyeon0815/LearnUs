package com.ssafy.backend.service.mattermost;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MattermostMessageServiceImpl implements MattermostMessageService{
    @Override
    public void send(String message, String pathName, String webhook) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> request = new HashMap<>();
            request.put("text", message);
            request.put("channel", "#" + pathName);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request);
            restTemplate.exchange(webhook, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}
