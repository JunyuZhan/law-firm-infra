package com.lawfirm.core.message.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息自动配置类
 */
@Slf4j
@AutoConfiguration
@Import({
    MessageConfig.class,
    MessageSecurityConfig.class,
    MessageRedisConfig.class,
    RetryConfig.class,
    MessageServiceConfig.class
})
public class MessageAutoConfiguration {

    /**
     * 创建消息配置Bean，如果业务层没有提供
     */
    @Bean(name = "messageProperties")
    @ConditionalOnMissingBean(MessageProperties.class)
    @ConditionalOnBean(MessagePropertiesProvider.class)
    public MessageProperties messageProperties(MessagePropertiesProvider provider) {
        log.info("从业务层获取消息配置");
        return provider.getMessageProperties();
    }
    
    /**
     * 提供默认消息配置提供者实现
     */
    @Bean(name = "defaultMessagePropertiesProvider")
    @ConditionalOnMissingBean(MessagePropertiesProvider.class)
    public MessagePropertiesProvider defaultMessagePropertiesProvider(MessageProperties messageProperties) {
        log.info("创建默认消息配置提供者");
        return () -> messageProperties;
    }
} 