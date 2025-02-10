package com.lawfirm.common.message.handler.impl;

import com.lawfirm.common.message.constant.MessageConstants;
import com.lawfirm.common.message.handler.MessageIdempotentHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Redis实现的消息幂等处理器
 */
@RequiredArgsConstructor
public class RedisMessageIdempotentHandler implements MessageIdempotentHandler {
    private final StringRedisTemplate redisTemplate;
    private final int expireHours;

    @Override
    public boolean isProcessed(String messageId) {
        String key = MessageConstants.RedisKeyPrefix.MESSAGE_IDEMPOTENT + messageId;
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, "1", expireHours, TimeUnit.HOURS);
        return result != null && !result;
    }

    @Override
    public void markAsProcessed(String messageId) {
        String key = MessageConstants.RedisKeyPrefix.MESSAGE_IDEMPOTENT + messageId;
        redisTemplate.opsForValue().set(key, "1", expireHours, TimeUnit.HOURS);
    }

    @Override
    public void clear(String messageId) {
        String key = MessageConstants.RedisKeyPrefix.MESSAGE_IDEMPOTENT + messageId;
        redisTemplate.delete(key);
    }
} 