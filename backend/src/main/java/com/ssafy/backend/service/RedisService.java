package com.ssafy.backend.service;

import com.ssafy.backend.dto.info.ChatInfo;

import java.util.List;

public interface RedisService {
    public void setListValue(String key, List<String> value, Long expireMin);
    public void setChatInfoValue(String key, ChatInfo value);
    public void setValue(String key, String value);
    public void update(String key, String newKey);
    public List<String> getListValue(String key);
    public List<ChatInfo> getChatInfoValue(String key);
    public String getValue(String key);
}
