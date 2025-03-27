package com.lawfirm.api.config;

import com.lawfirm.common.cache.config.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 缓存配置类
 * 为开发环境提供缓存配置
 */
@Configuration
public class CacheConfig {

    /**
     * 提供缓存属性Bean
     * 用于配置文档模块的缓存行为
     */
    @Bean
    @Primary
    public CacheProperties cacheProperties() {
        CacheProperties props = new CacheProperties();
        props.setEnabled(false); // 开发环境禁用缓存
        props.setType(CacheProperties.CacheType.LOCAL); // 使用本地缓存
        props.setExpiration(30); // 过期时间30分钟
        props.setRefreshTime(10); // 刷新时间10分钟
        return props;
    }
} 