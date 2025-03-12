package com.lawfirm.document.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.lawfirm.document.config.properties.DocumentProperties;
import com.lawfirm.document.manager.storage.DocumentStorageManager;
import com.lawfirm.document.manager.storage.impl.LocalStorageManager;
import com.lawfirm.document.manager.storage.impl.OssStorageManager;
import com.lawfirm.document.manager.storage.impl.S3StorageManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 存储配置类
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(DocumentProperties.class)
public class StorageConfig {

    private final DocumentProperties properties;

    /**
     * 配置OSS客户端
     */
    @Bean
    public OSS ossClient() {
        DocumentProperties.Storage storage = properties.getStorage();
        if ("oss".equals(storage.getType())) {
            return new OSSClientBuilder()
                    .build(storage.getOssEndpoint(),
                          storage.getOssAccessKeyId(),
                          storage.getOssAccessKeySecret());
        }
        return null;
    }

    /**
     * 配置存储管理器
     */
    @Bean
    public DocumentStorageManager storageManager(OSS ossClient) {
        DocumentProperties.Storage storageProps = properties.getStorage();
        switch (storageProps.getType().toLowerCase()) {
            case "oss":
                return new OssStorageManager(ossClient, properties);
            case "s3":
                return new S3StorageManager(properties);
            case "local":
            default:
                return new LocalStorageManager(properties);
        }
    }
} 