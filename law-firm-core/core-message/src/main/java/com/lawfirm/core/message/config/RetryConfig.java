package com.lawfirm.core.message.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

/**
 * 重试配置类
 */
@Configuration
public class RetryConfig {

    @Bean(name = "messageRetryTemplate")
    public RetryTemplate retryTemplate() {
        return RetryTemplate.builder()
                .maxAttempts(3)
                .fixedBackoff(1000)
                .build();
    }
} 