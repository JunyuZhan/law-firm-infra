package com.lawfirm.core.storage.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 存储服务自动配置
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.lawfirm.core.storage"})
@ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "true", matchIfMissing = false)
public class StorageAutoConfiguration {

    /**
     * 构造函数
     */
    public StorageAutoConfiguration() {
        log.info("初始化存储服务自动配置");
    }
} 