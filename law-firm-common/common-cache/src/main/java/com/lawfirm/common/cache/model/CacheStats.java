package com.lawfirm.common.cache.model;

import lombok.Data;

/**
 * 缓存统计信息
 */
@Data
public class CacheStats {
    
    /**
     * 缓存命中次数
     */
    private Long hits;
    
    /**
     * 缓存未命中次数
     */
    private Long misses;
    
    /**
     * 缓存命中率
     */
    private Double hitRate;
    
    /**
     * 已使用内存(MB)
     */
    private Long usedMemory;
    
    /**
     * 最大内存(MB)
     */
    private Long maxMemory;
    
    /**
     * 内存使用率
     */
    private Double memoryUsageRate;
    
    /**
     * 键总数
     */
    private Long keyCount;
    
    /**
     * 过期键数量
     */
    private Long expiredCount;
} 