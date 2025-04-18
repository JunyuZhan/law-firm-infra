package com.lawfirm.core.message.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息服务自动配置类
 * 此类将自动导入消息服务相关的所有配置
 */
@Slf4j
@AutoConfiguration
@ComponentScan("com.lawfirm.core.message")
@Import({
    MessageConfig.class,
    MessageSecurityConfig.class,
    MessageRedisConfig.class,
    RetryConfig.class,
    MessageServiceConfig.class,
    MessageFacadeConfig.class
})
@PropertySource(value = "classpath:default-message-config.properties", ignoreResourceNotFound = true)
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