package com.poc.redis.cache.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void cacheUserSubscription(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public Set<Object> getUserSubscription(String key) {
        return redisTemplate.opsForSet().members(key);
    }
}

