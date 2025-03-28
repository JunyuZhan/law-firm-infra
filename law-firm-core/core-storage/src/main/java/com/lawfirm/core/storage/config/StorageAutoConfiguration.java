package com.lawfirm.core.storage.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;

/**
 * 存储自动配置类
 */
@Slf4j
@AutoConfiguration
@Import(StorageConfiguration.class)
public class StorageAutoConfiguration {

    /**
     * 创建存储配置Bean，如果业务层没有提供
     */
    @Bean
    @ConditionalOnMissingBean(StorageProperties.class)
    public StorageProperties defaultStorageProperties() {
        log.info("创建默认存储配置");
        return new StorageProperties();
    }
} 