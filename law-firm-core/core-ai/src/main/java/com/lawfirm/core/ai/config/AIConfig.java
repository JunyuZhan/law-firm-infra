package com.lawfirm.core.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * AI核心配置类
 *
 * @author JunyuZhan
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "law-firm.ai")
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
     * 是否启用缓存
     */
    private Boolean enableCache = true;

    /**
     * 敏感信息过滤开关
     */
    private Boolean enableSensitiveFilter = true;

    /**
     * 是否开启风险评估
     */
    private Boolean enableRiskAssessment = true;

    /**
     * 是否启用调试模式
     */
    private Boolean debug = false;

    /**
     * 初始化方法
     */
    public void init() {
        // 在这里可以添加配置验证和初始化逻辑
    }
} 
