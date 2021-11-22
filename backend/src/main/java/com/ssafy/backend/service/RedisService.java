package com.ssafy.backend.service;

import com.ssafy.backend.dto.info.ChatInfo;

import java.util.List;

public interface RedisService {
    void setListValue(String key, List<String> value, Long expireMin);
    void setChatInfoValue(String key, ChatInfo value);
    void setValue(String key, String value);
    void setValueExpire(String key, String value, Long expireMin);
    void update(String key, String newKey);
    void delete(String key);
    List<String> getListValue(String key);
    List<ChatInfo> getChatInfoValue(String key);
    String getValue(String key);
}
