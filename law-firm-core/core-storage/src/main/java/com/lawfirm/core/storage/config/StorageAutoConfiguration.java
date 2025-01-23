package com.lawfirm.core.storage.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 存储自动配置
 */
@Configuration
@ComponentScan("com.lawfirm.core.storage")
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAutoConfiguration {
} 