package com.lawfirm.document.config;

import com.lawfirm.document.manager.storage.StorageManager;
import com.lawfirm.document.manager.storage.impl.DefaultStorageManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文档存储服务配置
 * 已弃用，改为使用CoreStorageConfiguration
 * 保留此类以避免类加载错误，但不再创建bean
 */
@Slf4j
@Configuration("documentStorageConfiguration")
public class StorageConfiguration {

    // 已弃用，改为使用CoreStorageConfiguration
} 