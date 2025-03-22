package com.lawfirm.document.service.strategy.storage;

import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.strategy.impl.LocalStorageStrategy;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.entity.file.FileObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 文档模块本地存储策略，扩展自核心模块的本地存储策略
 * 提供文档模块特定的存储需求
 */
@Slf4j
@Component
public class DocumentLocalStorageStrategy extends LocalStorageStrategy {

    @Value("${document.storage.urlPrefix:/api/document/file}")
    private String documentUrlPrefix;

    /**
     * 构造函数，调用父类构造器
     */
    @Autowired
    public DocumentLocalStorageStrategy(StorageProperties storageProperties) {
        super(storageProperties);
        log.info("初始化文档模块本地存储策略");
    }

    /**
     * 重写URL生成方法，适应文档模块的需求
     */
    @Override
    public String generatePresignedUrl(StorageBucket bucket, String objectName, Integer expireSeconds) {
        // 为文档提供特定的URL路径
        return documentUrlPrefix + "/" + bucket.getBucketName() + "/" + objectName;
    }
    
    /**
     * 在父类处理文件上传后，添加文档模块特定的处理
     */
    @Override
    public boolean uploadFile(StorageBucket bucket, FileObject fileObject, InputStream inputStream) {
        // 调用父类的上传方法
        boolean result = super.uploadFile(bucket, fileObject, inputStream);
        
        if (result) {
            log.info("文档模块本地存储成功: {}", fileObject.getFileName());
            // 可以添加文档模块特定的处理逻辑
        }
        
        return result;
    }
} 