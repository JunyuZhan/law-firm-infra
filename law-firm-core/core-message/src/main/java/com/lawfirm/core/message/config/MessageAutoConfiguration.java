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
    MessageFacadeConfig.class,
    WebSocketConfig.class  // 添加WebSocket配置
})
@PropertySource(value = "classpath:default-message-config.properties", ignoreResourceNotFound = true)
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MessageAutoConfiguration {
    
    public MessageAutoConfiguration() {
        log.info("=== 消息模块自动配置启动 ===");
        log.info("消息服务已启用，包含以下功能:");
        log.info("- 邮件通知服务");
        log.info("- 短信通知服务");
        log.info("- 站内通知服务");
        log.info("- WebSocket实时推送");
        log.info("- 消息模板服务");
        log.info("- 消息门面服务");
        log.info("=========================");
    }
}