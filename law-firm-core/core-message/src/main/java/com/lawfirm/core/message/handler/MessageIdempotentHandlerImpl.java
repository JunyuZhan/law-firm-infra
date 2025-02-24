package com.lawfirm.core.message.handler;

import com.lawfirm.common.message.handler.MessageIdempotentHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class MessageIdempotentHandlerImpl implements MessageIdempotentHandler {
    
    private final RedisTemplate<String, Object> redisTemplate;
    private final int expireDays;
    private static final String KEY_PREFIX = "message:idempotent:";
    
    @Override
    public boolean isProcessed(String messageId) {
        String key = KEY_PREFIX + messageId;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    
    @Override
    public void markAsProcessed(String messageId) {
        String key = KEY_PREFIX + messageId;
        redisTemplate.opsForValue().set(key, "1", expireDays, TimeUnit.DAYS);
    }
} 