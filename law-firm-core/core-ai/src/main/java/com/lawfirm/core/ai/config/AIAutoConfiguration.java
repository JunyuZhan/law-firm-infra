package com.lawfirm.core.ai.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;

/**
 * AI自动配置类
 */
@Slf4j
@AutoConfiguration
@Import({AIConfigLoader.class})
public class AIAutoConfiguration {

    /**
     * 创建AI配置Bean，如果业务层没有提供
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(AIPropertiesProvider.class)
    public AIConfig aiConfig(AIPropertiesProvider provider) {
        log.info("从业务层获取AI配置");
        return provider.getAIConfig();
    }
} 