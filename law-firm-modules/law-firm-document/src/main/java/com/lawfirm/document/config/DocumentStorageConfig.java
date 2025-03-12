package com.lawfirm.document.config;

import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.core.storage.service.impl.FileServiceImpl;
import com.lawfirm.core.storage.service.impl.BucketServiceImpl;
import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.service.support.FileOperator;
import com.lawfirm.core.storage.service.support.FileUploader;
import com.lawfirm.core.storage.strategy.StorageContext;
import com.lawfirm.model.storage.mapper.FileObjectMapper;
import com.lawfirm.model.storage.mapper.StorageBucketMapper;
import com.lawfirm.document.config.properties.DocumentProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文档存储配置
 */
@Configuration
@EnableConfigurationProperties({DocumentProperties.class, StorageProperties.class})
public class DocumentStorageConfig {

    /**
     * 配置文件存储服务
     */
    @Bean
    public FileService fileService(FileObjectMapper fileObjectMapper,
                                 FileUploader fileUploader,
                                 FileOperator fileOperator,
                                 StorageProperties storageProperties) {
        return new FileServiceImpl(fileObjectMapper, fileUploader, fileOperator, storageProperties);
    }

    /**
     * 配置存储桶服务
     */
    @Bean
    public BucketService bucketService(StorageBucketMapper storageBucketMapper,
                                     StorageContext storageContext,
                                     StorageProperties storageProperties) {
        return new BucketServiceImpl(storageBucketMapper, storageContext, storageProperties);
    }
} 