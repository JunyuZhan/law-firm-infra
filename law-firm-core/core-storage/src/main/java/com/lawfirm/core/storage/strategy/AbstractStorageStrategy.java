package com.lawfirm.core.storage.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import com.lawfirm.model.storage.enums.StorageTypeEnum;

/**
 * 抽象存储策略基类，提供通用实现
 */
@Slf4j
public abstract class AbstractStorageStrategy implements StorageStrategy, InitializingBean {

    /**
     * 是否已初始化
     */
    protected boolean initialized = false;
    
    /**
     * 存储类型
     */
    protected final StorageTypeEnum storageType;
    
    /**
     * 构造函数
     * 
     * @param storageType 存储类型
     */
    protected AbstractStorageStrategy(StorageTypeEnum storageType) {
        this.storageType = storageType;
    }
    
    @Override
    public StorageTypeEnum getStorageType() {
        return storageType;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!isEnabled()) {
            log.info("[存储策略] {} 未启用，跳过初始化", storageType);
            return;
        }
        
        log.info("[存储策略] 开始初始化 {}", storageType);
        initialize();
        initialized = true;
        log.info("[存储策略] {} 初始化完成", storageType);
    }
    
    /**
     * 检查并确保已初始化
     */
    protected void ensureInitialized() {
        if (!initialized) {
            throw new IllegalStateException("存储策略 " + storageType + " 尚未初始化");
        }
    }
    
    /**
     * 是否启用此存储策略
     * 
     * @return 是否启用
     */
    protected abstract boolean isEnabled();
    
    /**
     * 格式化对象名称，移除开头的/
     * 
     * @param objectName 对象名称
     * @return 格式化后的对象名称
     */
    protected String formatObjectName(String objectName) {
        if (objectName == null) {
            return null;
        }
        
        // 移除开头的/
        if (objectName.startsWith("/")) {
            return objectName.substring(1);
        }
        
        return objectName;
    }
} 