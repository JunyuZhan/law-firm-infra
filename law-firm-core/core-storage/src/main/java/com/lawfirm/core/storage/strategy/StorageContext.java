package com.lawfirm.core.storage.strategy;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.enums.StorageTypeEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 存储上下文，管理不同的存储策略
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "law-firm.core.storage.enabled", havingValue = "true", matchIfMissing = false)
public class StorageContext {

    /**
     * 所有存储策略列表
     */
    private final List<StorageStrategy> strategies;
    
    /**
     * 存储策略缓存
     */
    private final Map<StorageTypeEnum, StorageStrategy> strategyCache = new ConcurrentHashMap<>();
    
    /**
     * 获取存储策略
     * 
     * @param storageType 存储类型
     * @return 存储策略
     */
    public StorageStrategy getStrategy(StorageTypeEnum storageType) {
        if (strategyCache.containsKey(storageType)) {
            return strategyCache.get(storageType);
        }
        
        synchronized (this) {
            if (strategyCache.containsKey(storageType)) {
                return strategyCache.get(storageType);
            }
            
            for (StorageStrategy strategy : strategies) {
                if (strategy.getStorageType() == storageType) {
                    strategyCache.put(storageType, strategy);
                    return strategy;
                }
            }
        }
        
        throw new IllegalArgumentException("未找到存储类型为 " + storageType + " 的存储策略");
    }
    
    /**
     * 获取存储策略
     * 
     * @param bucket 存储桶
     * @return 存储策略
     */
    public StorageStrategy getStrategy(StorageBucket bucket) {
        if (bucket == null) {
            throw new IllegalArgumentException("存储桶不能为空");
        }
        return getStrategy(bucket.getStorageType());
    }
    
    /**
     * 是否支持指定的存储类型
     * 
     * @param storageType 存储类型
     * @return 是否支持
     */
    public boolean isSupported(StorageTypeEnum storageType) {
        try {
            getStrategy(storageType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
} 