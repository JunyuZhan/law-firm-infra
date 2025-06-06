package com.lawfirm.core.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * RocketMQ自动配置类
 * 
 * 当RocketMQ被禁用时，提供明确的日志信息
 */
@Slf4j
@Configuration("coreMessageRocketMQAutoConfiguration")
public class RocketMQAutoConfiguration {
    
    /**
     * RocketMQ启用配置
     */
    @Configuration
    @ConditionalOnProperty(name = "rocketmq.enabled", havingValue = "true")
    static class RocketMQEnabledConfiguration {
        public RocketMQEnabledConfiguration() {
            log.info("RocketMQ消息服务已启用");
        }
    }
    
    /**
     * RocketMQ禁用配置
     */
    @Configuration
    @ConditionalOnProperty(name = "rocketmq.enabled", havingValue = "false", matchIfMissing = true)
    static class RocketMQDisabledConfiguration {
        public RocketMQDisabledConfiguration() {
            log.info("RocketMQ消息服务已禁用，使用本地消息服务");
        }
    }
} 