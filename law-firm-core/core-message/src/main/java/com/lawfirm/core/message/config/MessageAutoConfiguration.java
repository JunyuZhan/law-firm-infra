package com.lawfirm.core.message.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息自动配置类
 */
@Slf4j
@Configuration
@Import({
    MessageConfig.class,
    MessageSecurityConfig.class,
    // MessageRedisConfig.class, // 已移除，使用cacheRedisTemplate替代
    RetryConfig.class,
    MessageServiceConfig.class,
    MessageFacadeConfig.class
})
@PropertySource(value = "classpath:default-message-config.properties", ignoreResourceNotFound = true)
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MessageAutoConfiguration {
    
} 