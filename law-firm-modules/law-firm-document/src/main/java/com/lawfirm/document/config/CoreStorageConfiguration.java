package com.lawfirm.document.config;

import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.strategy.StorageContext;
import com.lawfirm.document.manager.storage.StorageManager;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.storage.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 核心存储服务配置
 * 提供兼容性配置，确保启动脚本和现有配置都能正常工作
 */
@Slf4j
@Configuration
public class CoreStorageConfiguration {

    /**
     * 创建StorageManager
     * 使用条件注解确保兼容现有配置
     * 当lawfirm.storage.enabled=true或law-firm.storage.enabled=true时创建
     */
    @Bean("storageManager")
    @Primary
    @ConditionalOnMissingBean(StorageManager.class)
    @ConditionalOnProperty(name = {"lawfirm.storage.enabled", "law-firm.storage.enabled"}, havingValue = "true", matchIfMissing = true)
    public StorageManager storageManager(
            FileService fileService,
            BucketService bucketService,
            StorageContext storageContext,
            StorageProperties storageProperties) {
        log.info("创建StorageManager Bean (名称: storageManager)");
        return new StorageManager(fileService, bucketService, storageContext, storageProperties);
    }
    
    /**
     * 兼容配置，当使用DefaultStorageManager时提供
     */
    @Bean("defaultStorageManagerConfig")
    @ConditionalOnProperty(name = {"lawfirm.storage.enabled", "law-firm.storage.enabled"}, havingValue = "true", matchIfMissing = true)
    public StorageProperties defaultStorageConfig() {
        log.info("创建默认存储配置");
        return new StorageProperties();
    }
} 