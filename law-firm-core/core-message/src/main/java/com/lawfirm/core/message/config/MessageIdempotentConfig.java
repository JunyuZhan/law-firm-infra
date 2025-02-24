package com.lawfirm.core.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "message.idempotent")
public class MessageIdempotentConfig {
    private Boolean enabled = true;
    private Long expireTime = 24 * 60 * 60L; // 默认24小时
    private String redisKeyPrefix = "message:idempotent:";
} 