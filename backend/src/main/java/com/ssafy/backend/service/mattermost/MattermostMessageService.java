package com.ssafy.backend.service.mattermost;

public interface MattermostMessageService {
    void send(String message, String pathName, String webhook);
}
