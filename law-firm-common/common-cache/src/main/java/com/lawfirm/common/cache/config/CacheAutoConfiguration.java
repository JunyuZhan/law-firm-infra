package com.lawfirm.common.cache.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 缓存自动配置类
 */
@Configuration
@ComponentScan(basePackages = "com.lawfirm.common.cache")
@Import(CacheConfig.class)
public class CacheAutoConfiguration {
} 