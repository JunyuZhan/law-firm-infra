package com.lawfirm.common.cache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 缓存属性配置类
 */
@Data
@ConfigurationProperties(prefix = "law-firm.cache")
public class CacheProperties {

    /**
     * 是否开启缓存
     */
    private boolean enabled = true;

    /**
     * 缓存类型
     */
    private CacheType type = CacheType.REDIS;

    /**
     * 缓存有效期（分钟）
     */
    private long expiration = 720;

    /**
     * 缓存刷新时间（分钟）
     */
    private long refreshTime = 120;

    /**
     * 缓存类型枚举
     */
    public enum CacheType {
        /**
         * Redis缓存
         */
        REDIS,
        /**
         * 本地缓存
         */
        LOCAL
    }
} 