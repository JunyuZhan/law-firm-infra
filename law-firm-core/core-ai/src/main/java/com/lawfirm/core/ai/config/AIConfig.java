package com.lawfirm.core.ai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import lombok.Data;

/**
 * AI核心配置类
 *
 * @author AI开发团队
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "lawfirm.ai")
@EnableConfigurationProperties
@ComponentScan("com.lawfirm.core.ai")
public class AIConfig {

    /**
     * 默认AI提供商（openai, baidu, local）
     */
    private String defaultProvider = "openai";
    
    /**
     * 模型超时时间（秒）
     */
    private Integer timeout = 30;
    
    /**
     * 并发请求数限制
     */
    private Integer maxConcurrentRequests = 10;
    
    /**
     * 是否启用日志记录
     */
    private Boolean enableLogging = true;
    
    /**
     * 敏感信息过滤开关
     */
    private Boolean enablePrivacyFilter = true;
    
    /**
     * 是否开启风险评估
     */
    private Boolean enableRiskAssessment = true;
    
    /**
     * 本地模型目录
     */
    private String localModelPath;
    
    /**
     * 初始化方法
     */
    @Bean
    public void init() {
        // 初始化操作
    }
} 