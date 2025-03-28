package com.lawfirm.core.storage.config;

/**
 * 存储配置提供者接口
 * 业务层实现此接口以提供存储配置
 */
public interface StoragePropertiesProvider {
    
    /**
     * 获取存储配置
     * 
     * @return 存储配置属性
     */
    StorageProperties getStorageProperties();
} 