package com.lawfirm.core.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * RocketMQ自动配置类
 * 
 * 当RocketMQ被禁用时，提供明确的日志信息和Bean处理
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
            log.info("✅ RocketMQ消息服务已启用");
        }
        
        /**
         * RocketMQ功能启用标记
         */
        @Bean(name = "rocketMQEnabledMarker")
        @Primary
        public String rocketMQEnabledMarker() {
            log.info("RocketMQ功能已启用，消息队列服务正常运行");
            return "RocketMQ_ENABLED";
        }
    }
    
    /**
     * RocketMQ禁用配置
     */
    @Configuration
    @ConditionalOnProperty(name = "rocketmq.enabled", havingValue = "false", matchIfMissing = true)
    static class RocketMQDisabledConfiguration {
        public RocketMQDisabledConfiguration() {
            log.info("🔶 RocketMQ消息服务已禁用，使用本地消息服务（这是正常的配置）");
        }
        
        /**
         * RocketMQ功能禁用标记
         */
        @Bean(name = "rocketMQDisabledMarker")
        @Primary
        public String rocketMQDisabledMarker() {
            log.info("RocketMQ功能已禁用，系统将使用本地消息处理机制");
            return "RocketMQ_DISABLED";
        }
        
        /**
         * 提供默认的RocketMQ配置以避免BeanPostProcessor警告
         */
        @Bean(name = "rocketMQDefaultConfig")
        @ConditionalOnMissingBean(name = "rocketMQDefaultConfig")
        public RocketMQDefaultConfig rocketMQDefaultConfig() {
            return new RocketMQDefaultConfig();
        }
    }
    
    /**
     * RocketMQ默认配置类
     * 提供一个空的配置对象，避免BeanPostProcessor处理时的警告
     */
    public static class RocketMQDefaultConfig {
        public String getStatus() {
            return "DISABLED";
        }
        
        public String getMessage() {
            return "RocketMQ功能已禁用，使用本地消息处理";
        }
    }
} 