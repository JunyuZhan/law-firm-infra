package com.lawfirm.core.message.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认消息配置
 * <p>
 * 当没有其他MessageProperties bean时，提供一个默认配置
 * </p>
 *
 * @author JunyuZhan
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class DefaultMessageConfig {

    /**
     * 创建默认消息属性对象
     * 
     * @return 消息属性配置
     */
    @Bean(name = "defaultCoreMessageProperties")
    @ConditionalOnMissingBean(MessageProperties.class)
    public MessageProperties messageProperties() {
        log.info("创建默认消息属性配置");
        MessageProperties properties = new MessageProperties();
        properties.setEnabled(true);
        properties.getStorage().setType("memory");
        return properties;
    }
} 