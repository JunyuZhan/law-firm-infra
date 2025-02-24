package com.lawfirm.core.message.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MessageIdempotentHandler {
    private final RedisTemplate<String, Object> redisTemplate;
    private final MessageIdempotentConfig config;

    public boolean isProcessed(String messageId) {
        if (!config.getEnabled()) {
            return false;
        }
        String key = config.getRedisKeyPrefix() + messageId;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void markAsProcessed(String messageId) {
        if (!config.getEnabled()) {
            return;
        }
        String key = config.getRedisKeyPrefix() + messageId;
        redisTemplate.opsForValue().set(key, "1", config.getExpireTime(), TimeUnit.SECONDS);
    }

    public void clear(String messageId) {
        if (!config.getEnabled()) {
            return;
        }
        String key = config.getRedisKeyPrefix() + messageId;
        redisTemplate.delete(key);
    }
} 