package com.ssafy.backend.service;

public interface MattermostMessageService {
    public void send(String message, String pathName, String webhook);
}
