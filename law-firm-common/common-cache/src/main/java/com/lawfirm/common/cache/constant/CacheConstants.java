package com.lawfirm.common.cache.constant;

/**
 * 缓存常量
 * 只包含基础设施相关的常量
 */
public final class CacheConstants {

    private CacheConstants() {
        // 防止实例化
    }

    /**
     * 缓存键前缀
     */
    public static final String CACHE_KEY_PREFIX = "cache:";

    /**
     * 限流缓存键前缀
     */
    public static final String RATE_LIMIT_KEY = CACHE_KEY_PREFIX + "rate_limit:";

    /**
     * 防重提交缓存键前缀
     */
    public static final String REPEAT_SUBMIT_KEY = CACHE_KEY_PREFIX + "repeat_submit:";

    /**
     * 简单缓存前缀
     */
    public static final String SIMPLE_CACHE_PREFIX = CACHE_KEY_PREFIX + "simple:";

    /**
     * 缓存预热前缀
     */
    public static final String WARM_UP_PREFIX = CACHE_KEY_PREFIX + "warm_up:";
} 