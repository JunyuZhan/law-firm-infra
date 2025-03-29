package com.lawfirm.core.ai.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

/**
 * AI模块自动配置类
 * 负责初始化AI相关的配置和组件
 */
@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = "com.lawfirm.core.ai")
@ConditionalOnProperty(prefix = "law.firm.ai", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AIAutoConfiguration {

    /**
     * 创建AI核心配置
     */
    @Bean
    public AIConfig aiConfig() {
        log.info("创建AI核心配置");
        return new AIConfig();
    }
    
    /**
     * 创建AI模型配置
     */
    @Bean
    public ModelConfig modelConfig() {
        log.info("创建AI模型配置");
        return new ModelConfig();
    }
    
    /**
     * 当没有AIPropertiesProvider实现类时提供默认实现
     */
    @Bean
    @ConditionalOnMissingBean(AIPropertiesProvider.class)
    public AIPropertiesProvider defaultAIPropertiesProvider(AIConfig aiConfig) {
        log.info("创建默认AI配置提供者");
        return () -> aiConfig;
    }
    
    /**
     * 创建RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        log.info("创建AI模块RestTemplate");
        return builder
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
    }
} 