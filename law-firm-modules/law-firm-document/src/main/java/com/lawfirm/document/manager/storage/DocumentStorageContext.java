package com.lawfirm.document.manager.storage;

import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 文档存储上下文，提供文档存储相关的配置和上下文信息
 */
@Component
public class DocumentStorageContext {
    
    private final FileService fileService;
    private final BucketService bucketService;
    
    public DocumentStorageContext(@Qualifier("storageFileServiceImpl") FileService fileService, BucketService bucketService) {
        this.fileService = fileService;
        this.bucketService = bucketService;
    }
    
    /**
     * 获取文档默认存储桶ID
     * 
     * @return 存储桶ID
     */
    public String getDocumentBucketId() {
        return "documents";
    }
    
    /**
     * 获取模板默认存储桶ID
     * 
     * @return 存储桶ID
     */
    public String getTemplateBucketId() {
        return "templates";
    }
    
    /**
     * 获取文件服务
     * 
     * @return 文件服务
     */
    public FileService getFileService() {
        return fileService;
    }
    
    /**
     * 获取存储桶服务
     * 
     * @return 存储桶服务
     */
    public BucketService getBucketService() {
        return bucketService;
    }
}