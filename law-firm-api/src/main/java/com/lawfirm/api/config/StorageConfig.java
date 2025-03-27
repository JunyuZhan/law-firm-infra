package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.lawfirm.core.storage.config.StorageConfiguration;
import com.lawfirm.core.storage.config.StorageProperties;

/**
 * 存储服务配置类
 * 负责引入core-storage模块中的组件和服务
 */
@Configuration
@ConditionalOnProperty(prefix = "lawfirm", name = "storage.enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackages = "com.lawfirm.core.storage")
@Import({StorageConfiguration.class, StorageProperties.class})
public class StorageConfig {
    // 通过ComponentScan引入存储服务相关组件
} 