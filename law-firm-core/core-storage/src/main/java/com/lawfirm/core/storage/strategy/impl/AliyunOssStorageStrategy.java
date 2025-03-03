package com.lawfirm.core.storage.strategy.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.net.URL;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.strategy.AbstractStorageStrategy;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.enums.StorageTypeEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 阿里云OSS存储策略实现类
 */
@Component
@Slf4j
public class AliyunOssStorageStrategy extends AbstractStorageStrategy {

    private OSS ossClient;
    private final StorageProperties storageProperties;
    
    public AliyunOssStorageStrategy(StorageProperties storageProperties) {
        super(StorageTypeEnum.ALIYUN_OSS);
        this.storageProperties = storageProperties;
    }
    
    @Override
    public void initialize() {
        if (initialized) {
            return;
        }
        
        try {
            log.info("初始化阿里云OSS存储客户端...");
            StorageProperties.AliyunOssConfig aliyunConfig = storageProperties.getAliyunOss();
            
            // 创建阿里云OSS客户端
            ossClient = new OSSClientBuilder().build(
                    aliyunConfig.getEndpoint(),
                    aliyunConfig.getAccessKey(),
                    aliyunConfig.getSecretKey());
            
            log.info("阿里云OSS存储客户端初始化成功");
            initialized = true;
        } catch (Exception e) {
            log.error("初始化阿里云OSS存储客户端失败", e);
            throw new RuntimeException("初始化阿里云OSS存储客户端失败: " + e.getMessage());
        }
    }
    
    @Override
    public boolean isEnabled() {
        StorageProperties.AliyunOssConfig aliyunConfig = storageProperties.getAliyunOss();
        return aliyunConfig != null && aliyunConfig.isEnabled() && 
                StringUtils.hasText(aliyunConfig.getEndpoint()) && 
                StringUtils.hasText(aliyunConfig.getAccessKey()) && 
                StringUtils.hasText(aliyunConfig.getSecretKey());
    }

    @Override
    public boolean createBucket(String bucketName, boolean isPublic) {
        ensureInitialized();
        try {
            // 检查存储桶是否已存在
            boolean exists = bucketExists(bucketName);
            if (exists) {
                log.info("存储桶 {} 已存在，无需创建", bucketName);
                return true;
            }
            
            // 创建存储桶
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            
            // 设置存储桶访问权限
            if (isPublic) {
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                log.info("创建公开访问的存储桶 {}", bucketName);
            } else {
                createBucketRequest.setCannedACL(CannedAccessControlList.Private);
                log.info("创建私有访问的存储桶 {}", bucketName);
            }
            
            ossClient.createBucket(createBucketRequest);
            return true;
        } catch (Exception e) {
            log.error("创建存储桶 {} 失败", bucketName, e);
            return false;
        }
    }

    @Override
    public boolean bucketExists(String bucketName) {
        ensureInitialized();
        try {
            return ossClient.doesBucketExist(bucketName);
        } catch (Exception e) {
            log.error("检查存储桶 {} 是否存在时发生错误", bucketName, e);
            return false;
        }
    }

    @Override
    public boolean removeBucket(String bucketName) {
        ensureInitialized();
        try {
            ossClient.deleteBucket(bucketName);
            return true;
        } catch (Exception e) {
            log.error("删除存储桶 {} 失败", bucketName, e);
            return false;
        }
    }

    @Override
    public boolean uploadFile(StorageBucket bucket, FileObject fileObject, InputStream inputStream) {
        ensureInitialized();
        
        try {
            String bucketName = bucket.getBucketName();
            String objectName = formatObjectName(fileObject.getStoragePath());
            
            // 准备文件元数据
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(fileObject.getStorageSize());
            metadata.setContentType(getContentType(fileObject.getExtension()));
            
            // 设置自定义元数据
            metadata.getUserMetadata().put("FileName", fileObject.getFileName());
            metadata.getUserMetadata().put("FileType", fileObject.getFileType() != null ? 
                    fileObject.getFileType().name() : "");
            
            // 上传文件
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                bucketName, objectName, inputStream, metadata);
            
            ossClient.putObject(putObjectRequest);
            return true;
        } catch (Exception e) {
            log.error("上传文件 {} 到存储桶 {} 失败", fileObject.getFileName(), bucket.getBucketName(), e);
            return false;
        }
    }

    @Override
    public Object getObjectMetadata(StorageBucket bucket, String objectName) {
        ensureInitialized();
        
        try {
            objectName = formatObjectName(objectName);
            
            // 获取对象元数据
            return ossClient.getObjectMetadata(bucket.getBucketName(), objectName);
        } catch (Exception e) {
            log.error("获取对象 {} 元数据失败", objectName, e);
            return null;
        }
    }

    @Override
    public InputStream getObject(StorageBucket bucket, String objectName) {
        ensureInitialized();
        
        try {
            objectName = formatObjectName(objectName);
            
            // 获取对象
            return ossClient.getObject(bucket.getBucketName(), objectName).getObjectContent();
        } catch (Exception e) {
            log.error("获取对象 {} 失败", objectName, e);
            return new ByteArrayInputStream(new byte[0]); // 返回空流
        }
    }

    @Override
    public boolean removeObject(StorageBucket bucket, String objectName) {
        ensureInitialized();
        
        try {
            objectName = formatObjectName(objectName);
            
            // 删除对象
            ossClient.deleteObject(bucket.getBucketName(), objectName);
            return true;
        } catch (Exception e) {
            log.error("删除对象 {} 失败", objectName, e);
            return false;
        }
    }

    @Override
    public String generatePresignedUrl(StorageBucket bucket, String objectName, Integer expireSeconds) {
        ensureInitialized();
        
        try {
            objectName = formatObjectName(objectName);
            
            // 处理过期时间，-1表示永不过期，使用默认的1小时
            int expirationSeconds = (expireSeconds == null || expireSeconds < 0) ? 3600 : expireSeconds;
            
            // 计算过期时间
            Date expiration = new Date(System.currentTimeMillis() + expirationSeconds * 1000L);
            
            // 生成URL
            URL url = ossClient.generatePresignedUrl(bucket.getBucketName(), objectName, expiration);
            return url.toString();
        } catch (Exception e) {
            log.error("生成预签名URL失败: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 根据文件扩展名获取内容类型
     */
    private String getContentType(String extension) {
        if (extension == null) {
            return "application/octet-stream";
        }
        
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "pdf":
                return "application/pdf";
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "txt":
                return "text/plain";
            case "html":
                return "text/html";
            case "xml":
                return "application/xml";
            case "json":
                return "application/json";
            default:
                return "application/octet-stream";
        }
    }
} 