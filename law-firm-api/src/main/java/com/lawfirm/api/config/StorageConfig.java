package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.lawfirm.core.storage.config.StorageConfiguration;
import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.config.StoragePropertiesProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 存储服务配置类
 * 负责引入core-storage模块中的组件和服务
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lawfirm", name = "storage.enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackages = "com.lawfirm.core.storage")
@Import({StorageConfiguration.class, StorageProperties.class})
public class StorageConfig {
    
    private final StorageProperties storageProperties;
    
    /**
     * 提供存储配置属性Bean
     * 实现StoragePropertiesProvider接口，为存储模块提供配置
     */
    @Bean
    public StoragePropertiesProvider storagePropertiesProvider() {
        log.info("创建存储配置提供者");
        return new StoragePropertiesProvider() {
            @Override
            public StorageProperties getStorageProperties() {
                return storageProperties;
            }
        };
    }
} 