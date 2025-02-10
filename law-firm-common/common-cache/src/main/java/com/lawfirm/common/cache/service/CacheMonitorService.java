package com.lawfirm.common.cache.service;

import com.lawfirm.common.cache.model.CacheStats;

/**
 * 缓存监控服务接口
 */
public interface CacheMonitorService {
    
    /**
     * 获取缓存统计信息
     */
    CacheStats getStats();
    
    /**
     * 清理缓存
     */
    void clearCache();
    
    /**
     * 获取指定key的缓存信息
     */
    Object get(String key);
    
    /**
     * 删除指定key的缓存
     */
    void delete(String key);
    
    /**
     * 获取所有缓存key
     */
    Iterable<String> getAllKeys();
} 