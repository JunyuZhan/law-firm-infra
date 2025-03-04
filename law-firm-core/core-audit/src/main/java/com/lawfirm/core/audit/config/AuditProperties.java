package com.lawfirm.core.audit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 审计配置属性
 */
@Data
@ConfigurationProperties(prefix = "lawfirm.audit")
public class AuditProperties {
    
    /**
     * 是否启用审计
     */
    private boolean enabled = true;

    /**
     * 异步配置
     */
    private Async async = new Async();

    /**
     * 模块配置
     */
    private Map<String, ModuleConfig> modules = new HashMap<>();

    @Data
    public static class Async {
        /**
         * 是否启用异步
         */
        private boolean enabled = true;

        /**
         * 线程池大小
         */
        private int poolSize = 4;

        /**
         * 队列容量
         */
        private int queueCapacity = 1000;
    }

    @Data
    public static class ModuleConfig {
        /**
         * 是否启用
         */
        private boolean enabled = true;

        /**
         * 是否异步
         */
        private boolean async = true;

        /**
         * 数据保留天数
         */
        private int retentionDays = 365;
    }
} 