package com.lawfirm.document.config;

import com.lawfirm.core.storage.strategy.StorageContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * 存储服务备用配置
 * 当存储服务被禁用时，提供必要的Bean实现
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "false", matchIfMissing = true)
public class StorageFallbackConfig {

    /**
     * 创建一个空的StorageContext实现
     */
    @Bean(name = "documentStorageContext")
    @ConditionalOnMissingBean
    public StorageContext storageContext() {
        log.info("创建存储服务空实现的Context");
        return new StorageContext(new ArrayList<>());
    }
} 