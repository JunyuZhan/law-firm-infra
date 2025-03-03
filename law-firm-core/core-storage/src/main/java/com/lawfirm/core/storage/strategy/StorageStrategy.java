package com.lawfirm.core.storage.strategy;

import java.io.InputStream;

import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.enums.StorageTypeEnum;

/**
 * 存储策略接口，定义不同存储实现的通用操作
 */
public interface StorageStrategy {

    /**
     * 获取存储类型
     * 
     * @return 存储类型
     */
    StorageTypeEnum getStorageType();
    
    /**
     * 初始化存储策略
     */
    void initialize();
    
    /**
     * 创建存储桶
     * 
     * @param bucketName 桶名称
     * @param isPublic 是否公开
     * @return 是否创建成功
     */
    boolean createBucket(String bucketName, boolean isPublic);
    
    /**
     * 检查存储桶是否存在
     * 
     * @param bucketName 桶名称
     * @return 是否存在
     */
    boolean bucketExists(String bucketName);
    
    /**
     * 删除存储桶
     * 
     * @param bucketName 桶名称
     * @return 是否删除成功
     */
    boolean removeBucket(String bucketName);
    
    /**
     * 上传文件
     * 
     * @param bucket 存储桶信息
     * @param fileObject 文件对象
     * @param inputStream 文件输入流
     * @return 是否上传成功
     */
    boolean uploadFile(StorageBucket bucket, FileObject fileObject, InputStream inputStream);
    
    /**
     * 获取文件元数据
     * 
     * @param bucket 存储桶信息
     * @param objectName 对象名称
     * @return 文件元数据
     */
    Object getObjectMetadata(StorageBucket bucket, String objectName);
    
    /**
     * 获取文件输入流
     * 
     * @param bucket 存储桶信息
     * @param objectName 对象名称
     * @return 文件输入流
     */
    InputStream getObject(StorageBucket bucket, String objectName);
    
    /**
     * 删除文件
     * 
     * @param bucket 存储桶信息
     * @param objectName 对象名称
     * @return 是否删除成功
     */
    boolean removeObject(StorageBucket bucket, String objectName);
    
    /**
     * 生成文件访问URL
     * 
     * @param bucket 存储桶信息
     * @param objectName 对象名称
     * @param expireSeconds 过期时间(秒)，-1表示永不过期
     * @return 访问URL
     */
    String generatePresignedUrl(StorageBucket bucket, String objectName, Integer expireSeconds);
} 