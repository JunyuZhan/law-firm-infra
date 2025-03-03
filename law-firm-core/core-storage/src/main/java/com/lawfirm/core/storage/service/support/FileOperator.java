package com.lawfirm.core.storage.service.support;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.lawfirm.core.storage.strategy.StorageContext;
import com.lawfirm.core.storage.strategy.StorageStrategy;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.repository.BucketRepository;
import com.lawfirm.model.storage.repository.FileObjectRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件操作类，提供统一的文件操作接口
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileOperator {
    
    private final StorageContext storageContext;
    private final BucketRepository bucketRepository;
    private final FileObjectRepository fileObjectRepository;
    
    /**
     * 上传文件
     * 
     * @param bucketId 存储桶ID
     * @param fileObject 文件对象
     * @param multipartFile 上传的文件
     * @return 是否上传成功
     */
    public boolean uploadFile(Long bucketId, FileObject fileObject, MultipartFile multipartFile) {
        StorageBucket bucket = bucketRepository.findById(bucketId);
        if (bucket == null) {
            log.error("存储桶不存在: {}", bucketId);
            return false;
        }
        
        try (InputStream inputStream = multipartFile.getInputStream()) {
            return uploadFile(bucket, fileObject, inputStream);
        } catch (IOException e) {
            log.error("获取文件流失败", e);
            return false;
        }
    }
    
    /**
     * 上传文件
     * 
     * @param bucketId 存储桶ID
     * @param fileObject 文件对象
     * @param fileContent 文件内容
     * @return 是否上传成功
     */
    public boolean uploadFile(Long bucketId, FileObject fileObject, byte[] fileContent) {
        StorageBucket bucket = bucketRepository.findById(bucketId);
        if (bucket == null) {
            log.error("存储桶不存在: {}", bucketId);
            return false;
        }
        
        try (InputStream inputStream = new ByteArrayInputStream(fileContent)) {
            return uploadFile(bucket, fileObject, inputStream);
        } catch (IOException e) {
            log.error("获取文件流失败", e);
            return false;
        }
    }
    
    /**
     * 上传文件
     * 
     * @param bucket 存储桶
     * @param fileObject 文件对象
     * @param inputStream 文件输入流
     * @return 是否上传成功
     */
    public boolean uploadFile(StorageBucket bucket, FileObject fileObject, InputStream inputStream) {
        StorageStrategy strategy = storageContext.getStrategy(bucket);
        String newStoragePath = fileObject.getStoragePath() + "_copy";
        fileObject.setStoragePath(newStoragePath);
        
        boolean result = strategy.uploadFile(bucket, fileObject, inputStream);
        // 关闭输入流
        try {
            inputStream.close();
        } catch (IOException e) {
            log.error("关闭输入流失败", e);
        }
        
        // 更新文件记录
        fileObject.setStoragePath(newStoragePath);
        try {
            fileObjectRepository.insert(fileObject);
            return true;
        } catch (Exception e) {
            log.error("更新文件记录失败", e);
            // 尝试删除已上传的文件
            strategy.removeObject(bucket, newStoragePath);
            return false;
        }
    }
    
    /**
     * 下载文件
     * 
     * @param fileId 文件ID
     * @return 文件输入流
     */
    public InputStream downloadFile(Long fileId) {
        FileObject fileObject = fileObjectRepository.findById(fileId);
        if (fileObject == null) {
            log.error("文件不存在: {}", fileId);
            return null;
        }
        
        StorageBucket bucket = bucketRepository.findById(fileObject.getBucketId());
        if (bucket == null) {
            log.error("存储桶不存在: {}", fileObject.getBucketId());
            return null;
        }
        
        StorageStrategy strategy = storageContext.getStrategy(bucket);
        
        // 更新访问计数和时间
        fileObject.setAccessCount(fileObject.getAccessCount() + 1);
        fileObject.setLastAccessTime(System.currentTimeMillis());
        fileObjectRepository.updateById(fileObject);
        
        return strategy.getObject(bucket, fileObject.getStoragePath());
    }
    
    /**
     * 删除文件
     * 
     * @param fileId 文件ID
     * @return 是否删除成功
     */
    public boolean deleteFile(Long fileId) {
        FileObject fileObject = fileObjectRepository.findById(fileId);
        if (fileObject == null) {
            log.error("文件不存在: {}", fileId);
            return false;
        }
        
        StorageBucket bucket = bucketRepository.findById(fileObject.getBucketId());
        if (bucket == null) {
            log.error("存储桶不存在: {}", fileObject.getBucketId());
            return false;
        }
        
        StorageStrategy strategy = storageContext.getStrategy(bucket);
        
        boolean result = strategy.removeObject(bucket, fileObject.getStoragePath());
        if (result) {
            try {
                // 删除文件记录
                fileObjectRepository.deleteById(fileObject.getId());
                return true;
            } catch (Exception e) {
                log.error("删除文件记录失败", e);
                return false;
            }
        }
        
        return result;
    }
    
    /**
     * 获取文件访问URL
     * 
     * @param fileId 文件ID
     * @param expireSeconds 过期时间(秒)
     * @return 访问URL
     */
    public String getFileUrl(Long fileId, Integer expireSeconds) {
        FileObject fileObject = fileObjectRepository.findById(fileId);
        if (fileObject == null) {
            return null;
        }
        
        StorageBucket bucket = bucketRepository.findById(fileObject.getBucketId());
        if (bucket == null) {
            return null;
        }
        
        StorageStrategy strategy = storageContext.getStrategy(bucket);
        
        return strategy.generatePresignedUrl(bucket, fileObject.getStoragePath(), expireSeconds);
    }
} 