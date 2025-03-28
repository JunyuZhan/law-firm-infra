package com.lawfirm.core.message.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

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
    RetryConfig.class
})
public class MessageAutoConfiguration {

    /**
     * 创建消息配置Bean，如果业务层没有提供
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(MessagePropertiesProvider.class)
    public MessageProperties messageProperties(MessagePropertiesProvider provider) {
        log.info("从业务层获取消息配置");
        return provider.getMessageProperties();
    }
} 