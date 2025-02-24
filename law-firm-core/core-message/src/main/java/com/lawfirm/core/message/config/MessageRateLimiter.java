package com.lawfirm.core.message.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MessageRateLimiter {
    private final RedisTemplate<String, Object> redisTemplate;
    private final MessageRateLimitConfig config;

    public boolean tryAcquire(String userId) {
        if (!config.getEnabled()) {
            return true;
        }

        String secondKey = config.getRedisKeyPrefix() + "second:" + userId;
        String minuteKey = config.getRedisKeyPrefix() + "minute:" + userId;
        String hourKey = config.getRedisKeyPrefix() + "hour:" + userId;

        Long secondCount = redisTemplate.opsForValue().increment(secondKey);
        Long minuteCount = redisTemplate.opsForValue().increment(minuteKey);
        Long hourCount = redisTemplate.opsForValue().increment(hourKey);

        if (secondCount != null && secondCount == 1) {
            redisTemplate.expire(secondKey, 1, TimeUnit.SECONDS);
        }
        if (minuteCount != null && minuteCount == 1) {
            redisTemplate.expire(minuteKey, 1, TimeUnit.MINUTES);
        }
        if (hourCount != null && hourCount == 1) {
            redisTemplate.expire(hourKey, 1, TimeUnit.HOURS);
        }

        return (secondCount == null || secondCount <= config.getMaxRequestsPerSecond()) &&
               (minuteCount == null || minuteCount <= config.getMaxRequestsPerMinute()) &&
               (hourCount == null || hourCount <= config.getMaxRequestsPerHour());
    }
} 