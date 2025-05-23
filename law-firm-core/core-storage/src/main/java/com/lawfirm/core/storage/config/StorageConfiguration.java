package com.lawfirm.core.storage.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lawfirm.core.storage.strategy.StorageContext;
import com.lawfirm.core.storage.strategy.StorageStrategy;
import com.lawfirm.model.storage.enums.StorageTypeEnum;
import com.lawfirm.model.storage.service.FileService;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储配置类
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnBean(StorageProperties.class)
public class StorageConfiguration {

    private final StorageProperties storageProperties;
    private final Environment environment;
    
    /**
     * 配置StorageContext
     * 当没有找到StorageContext时提供一个备用实现
     */
    @Bean(name = "storageContext")
    @ConditionalOnMissingBean(StorageContext.class)
    public StorageContext storageContext(List<StorageStrategy> strategies) {
        log.info("创建存储策略上下文 (备用实现)");
        return new StorageContext(strategies != null ? strategies : new ArrayList<>());
    }
    
    /**
     * 配置MinIO客户端
     * 注意：这里复用common-data模块中的配置，如果已经存在则不需要重复配置
     */
    @Bean(name = "coreMinioClient")
    public MinioClient minioClient() {
        StorageProperties.MinioConfig minioConfig = storageProperties.getMinio();
        
        if (!minioConfig.isEnabled()) {
            log.info("MinIO客户端配置未启用，跳过初始化");
            return null;
        }
        
        log.info("初始化MinIO客户端...");
        
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
    @Bean(name = "aliyunOssClient")
    public Object aliyunOssClient() {
        StorageProperties.AliyunOssConfig ossConfig = storageProperties.getAliyunOss();
        
        if (!ossConfig.isEnabled()) {
            log.info("阿里云OSS客户端配置未启用，跳过初始化");
            return null;
        }
        
        // 在实际项目中，这里应该引用common-data模块中已配置的阿里云OSS客户端
        log.info("初始化阿里云OSS客户端...");
        
        // TODO: 使用common-data模块中的OSS客户端
        
        return new Object(); // 占位，实际项目中应返回OSS客户端
    }
    
    /**
     * 配置腾讯云COS客户端
     * 注意：实际生产中应该复用common-data模块中的COS客户端配置
     */
    @Bean(name = "tencentCosClient")
    public Object tencentCosClient() {
        StorageProperties.TencentCosConfig cosConfig = storageProperties.getTencentCos();
        
        if (!cosConfig.isEnabled()) {
            log.info("腾讯云COS客户端配置未启用，跳过初始化");
            return null;
        }
        
        // 在实际项目中，这里应该引用common-data模块中已配置的腾讯云COS客户端
        log.info("初始化腾讯云COS客户端...");
        
        // TODO: 使用common-data模块中的COS客户端
        
        return new Object(); // 占位，实际项目中应返回COS客户端
    }
    
    /**
     * 注册配置提供者Bean
     */
    @Bean(name = "providerStorageProperties")
    @ConditionalOnBean(StoragePropertiesProvider.class)
    public StorageProperties storagePropertiesFromProvider(StoragePropertiesProvider provider) {
        log.info("从业务层获取存储配置");
        return provider.getStorageProperties();
    }

    /**
     * 包装storageFileServiceImpl为coreFileServiceImpl，解决多Bean注入歧义
     */
    @Bean(name = "coreFileServiceImpl")
    public FileService coreFileServiceImpl(@Qualifier("storageFileServiceImpl") FileService fileService) {
        return fileService;
    }
} 