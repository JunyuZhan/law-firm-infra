package com.lawfirm.document.config;

import com.lawfirm.common.cache.config.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * 文档模块缓存配置
 */
@Configuration
public class DocumentCacheConfig {

    /**
     * 提供文档模块使用的缓存属性
     * 将通用缓存属性注入并设置为文档模块专用Bean
     */
    @Bean("appCacheProperties")
    @ConditionalOnMissingBean(name = "appCacheProperties")
    public CacheProperties appCacheProperties(CacheProperties commonCacheProperties) {
        // 使用通用缓存属性，也可以自定义特殊配置
        return commonCacheProperties;
    }
} 