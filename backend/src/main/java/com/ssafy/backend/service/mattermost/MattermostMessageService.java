package com.ssafy.backend.service.mattermost;

public interface MattermostMessageService {
    public void send(String message, String pathName, String webhook);
}
