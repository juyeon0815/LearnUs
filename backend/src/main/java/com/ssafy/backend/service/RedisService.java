package com.ssafy.backend.service;

import java.util.List;

public interface RedisService {
    public void setListValue(String key, List<String> value, Long expireMin);
    public void update(String key, String newKey);
    public List<String> getValue(String key);
}
