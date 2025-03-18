package com.lawfirm.finance.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "finance")
public class FinanceConfig {
    private CacheConfig cache;
    private EventConfig event;
    private BusinessConfig business;

    @Data
    public static class CacheConfig {
        private boolean enabled;
        private long expireTime;
    }

    @Data
    public static class EventConfig {
        private boolean async;
        private int retryTimes;
    }

    @Data
    public static class BusinessConfig {
        private int maxPageSize;
        private int exportLimit;
    }
} 