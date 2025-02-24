package com.lawfirm.core.message.handler;

import com.lawfirm.common.message.handler.MessageRateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class MessageRateLimiterImpl implements MessageRateLimiter {
    
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "message:rate:";
    private static final int WINDOW_SECONDS = 60;
    private static final int MAX_MESSAGES = 100;
    
    @Override
    public boolean tryAcquire(String userId) {
        String key = KEY_PREFIX + userId;
        Long count = redisTemplate.opsForValue().increment(key);
        if (count == 1) {
            redisTemplate.expire(key, WINDOW_SECONDS, TimeUnit.SECONDS);
        }
        return count != null && count <= MAX_MESSAGES;
    }
    
    @Override
    public void reset(String userId) {
        String key = KEY_PREFIX + userId;
        redisTemplate.delete(key);
    }

    @Override
    public long getAvailableTokens() {
        return MAX_MESSAGES;
    }
} 