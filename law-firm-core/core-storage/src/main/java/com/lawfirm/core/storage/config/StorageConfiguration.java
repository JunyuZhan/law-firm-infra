package com.lawfirm.core.storage.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.lawfirm.model.storage.enums.StorageTypeEnum;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 存储配置类
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class StorageConfiguration {

    private final StorageProperties storageProperties;
    private final Environment environment;
    
    /**
     * 配置MinIO客户端
     * 注意：这里复用common-data模块中的配置，如果已经存在则不需要重复配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "storage.minio", name = "enabled", havingValue = "true")
    public MinioClient minioClient() {
        log.info("初始化MinIO客户端...");
        StorageProperties.MinioConfig minioConfig = storageProperties.getMinio();
        
        // 创建MinIO客户端
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioConfig.getEndpoint(), minioConfig.getPort(), minioConfig.isUseSsl())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
                
        log.info("MinIO客户端初始化完成: {}", minioConfig.getEndpoint());
        return minioClient;
    }
    
    /**
     * 配置阿里云OSS客户端
     * 注意：实际生产中应该复用common-data模块中的OSS客户端配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "storage.aliyun-oss", name = "enabled", havingValue = "true")
    public Object aliyunOssClient() {
        // 在实际项目中，这里应该引用common-data模块中已配置的阿里云OSS客户端
        log.info("初始化阿里云OSS客户端...");
        
        // TODO: 使用common-data模块中的OSS客户端
        
        return new Object(); // 占位，实际项目中应返回OSS客户端
    }
    
    /**
     * 配置腾讯云COS客户端
     * 注意：实际生产中应该复用common-data模块中的COS客户端配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "storage.tencent-cos", name = "enabled", havingValue = "true")
    public Object tencentCosClient() {
        // 在实际项目中，这里应该引用common-data模块中已配置的腾讯云COS客户端
        log.info("初始化腾讯云COS客户端...");
        
        // TODO: 使用common-data模块中的COS客户端
        
        return new Object(); // 占位，实际项目中应返回COS客户端
    }
} 