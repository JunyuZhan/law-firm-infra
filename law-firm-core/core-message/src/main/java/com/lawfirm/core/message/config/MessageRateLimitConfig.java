package com.lawfirm.core.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "message.rate-limit")
public class MessageRateLimitConfig {
    
    private boolean enabled = true;
    private String redisKeyPrefix = "message:rate:";
    private int maxRequestsPerSecond = 10;
    private int maxRequestsPerMinute = 100;
    private int maxRequestsPerHour = 1000;
    private int expireTime = 3600; // 1小时
}