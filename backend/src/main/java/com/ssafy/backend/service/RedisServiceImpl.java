package com.ssafy.backend.service;

import com.ssafy.backend.dto.info.ChatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisTemplate<String, ChatInfo> redisChatTemplate;

    @Override
    public void setListValue(String key, List<String> value, Long expireMin) {
        redisTemplate.opsForList().rightPush(key, value.get(0)); // userId
        redisTemplate.opsForList().rightPush(key, value.get(1)); // refreshToken
        redisTemplate.expire(key, expireMin, TimeUnit.MINUTES);
    }

    @Override
    public void setChatInfoValue(String key, ChatInfo value) {
        redisChatTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setValueExpire(String key, String value, Long expireMin) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expireMin, TimeUnit.MINUTES);
    }

    @Override
    public void update(String key, String newKey) {
        redisTemplate.rename(key, newKey);
    }

    @Override
    public void delete(String key) { redisTemplate.delete(key); }

    @Override
    public List<String> getListValue(String key) {
        List<String> list = redisTemplate.opsForList().range(key, 0, -1);
        return list;
    }

    @Override
    public List<ChatInfo> getChatInfoValue(String key) {
        List<ChatInfo> chatInfoList = redisChatTemplate.opsForList().range(key, 0, -1);
        return chatInfoList;
    }

    @Override
    public String getValue(String key) {
        String value = redisTemplate.opsForValue().get(key);
        return value;
    }
}
