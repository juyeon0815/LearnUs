package com.ssafy.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void setListValue(String key, List<String> value, Long expireMin) {
        redisTemplate.opsForList().rightPush(key, value.get(0)); // userId
        redisTemplate.opsForList().rightPush(key, value.get(1)); // refreshToken
        redisTemplate.expire(key, expireMin, TimeUnit.MINUTES);
    }

    @Override
    public void update(String key, String newKey) {
        redisTemplate.rename(key, newKey);
    }

    @Override
    public List<String> getValue(String key) {
        List<String> list = redisTemplate.opsForList().range(key, 0, -1);
        return list;
    }
}
