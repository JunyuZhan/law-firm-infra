package com.lawfirm.core.storage.strategy.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.strategy.AbstractStorageStrategy;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.entity.file.FileInfo;
import com.lawfirm.model.storage.enums.StorageTypeEnum;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.SetBucketPolicyArgs;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MinIO存储策略实现类
 */
@Component
@Slf4j
@ConditionalOnProperty(name = "law-firm.core.storage.enabled", havingValue = "true", matchIfMissing = false)
public class MinIOStorageStrategy extends AbstractStorageStrategy {

    private MinioClient minioClient;
    private final StorageProperties storageProperties;
    
    @Autowired
    public MinIOStorageStrategy(StorageProperties storageProperties) {
        super(StorageTypeEnum.MINIO);
        this.storageProperties = storageProperties;
    }
    
    @Override
    public void initialize() {
        if (initialized) {
            return;
        }
        
        try {
            log.info("初始化MinIO存储客户端...");
            StorageProperties.MinioConfig minioConfig = storageProperties.getMinio();
            
            // 创建MinIO客户端
            minioClient = MinioClient.builder()
                    .endpoint(minioConfig.getEndpoint())
                    .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                    .build();
            
            log.info("MinIO存储客户端初始化成功");
            initialized = true;
        } catch (Exception e) {
            log.error("初始化MinIO存储客户端失败", e);
            throw new RuntimeException("初始化MinIO存储客户端失败: " + e.getMessage());
        }
    }
    
    @Override
    public boolean isEnabled() {
        StorageProperties.MinioConfig minioConfig = storageProperties.getMinio();
        return minioConfig != null && minioConfig.isEnabled() && 
                StringUtils.hasText(minioConfig.getEndpoint()) && 
                StringUtils.hasText(minioConfig.getAccessKey()) && 
                StringUtils.hasText(minioConfig.getSecretKey());
    }

    @Override
    public boolean createBucket(String bucketName, boolean isPublic) {
        ensureInitialized();
        try {
            boolean bucketExists = bucketExists(bucketName);
            
            if (!bucketExists) {
                // 创建桶
                minioClient.makeBucket(
                    MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build()
                );
                
                // 设置桶访问策略
                if (isPublic) {
                    String policy = String.format(
                        "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::%s/*\"]}]}",
                        bucketName);
                    
                    minioClient.setBucketPolicy(
                        SetBucketPolicyArgs.builder()
                            .bucket(bucketName)
                            .config(policy)
                            .build()
                    );
                }
                
                log.info("成功创建MinIO存储桶: {}", bucketName);
                return true;
            }
            
            log.info("MinIO存储桶已存在: {}", bucketName);
            return true;
        } catch (Exception e) {
            log.error("创建MinIO存储桶失败: {} - {}", bucketName, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean bucketExists(String bucketName) {
        ensureInitialized();
        try {
            return minioClient.bucketExists(
                BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build()
            );
        } catch (Exception e) {
            log.error("检查MinIO存储桶是否存在失败: {} - {}", bucketName, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean removeBucket(String bucketName) {
        ensureInitialized();
        try {
            minioClient.removeBucket(
                RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build()
            );
            log.info("成功删除MinIO存储桶: {}", bucketName);
            return true;
        } catch (Exception e) {
            log.error("删除MinIO存储桶失败: {} - {}", bucketName, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean uploadFile(StorageBucket bucket, FileObject fileObject, InputStream inputStream) {
        ensureInitialized();
        
        if (bucket == null || fileObject == null || inputStream == null) {
            log.error("上传MinIO文件参数无效");
            return false;
        }
        
        try {
            String bucketName = bucket.getBucketName();
            String objectName = fileObject.getStoragePath();
            
            // 设置文件元数据
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", getContentType(fileObject.getExtension()));
            headers.put("X-File-Name", fileObject.getFileName());
            headers.put("X-File-Size", String.valueOf(fileObject.getStorageSize()));
            
            // 添加自定义元数据
            FileInfo fileInfo = fileObject.getFileInfo();
            if (fileInfo != null) {
                if (StringUtils.hasText(fileInfo.getDescription())) {
                    headers.put("X-Description", fileInfo.getDescription());
                }
                if (StringUtils.hasText(fileInfo.getTags())) {
                    headers.put("X-Tags", fileInfo.getTags());
                }
            }
            
            // 上传文件
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, fileObject.getStorageSize(), -1)
                    .contentType(getContentType(fileObject.getExtension()))
                    .headers(headers)
                    .build()
            );
            
            log.info("成功上传文件到MinIO: bucket={}, object={}", bucketName, objectName);
            return true;
        } catch (Exception e) {
            log.error("上传文件到MinIO失败: {} - {}", fileObject.getFileName(), e.getMessage(), e);
            return false;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.warn("关闭输入流失败", e);
            }
        }
    }

    @Override
    public Object getObjectMetadata(StorageBucket bucket, String objectName) {
        ensureInitialized();
        
        if (bucket == null || objectName == null) {
            return null;
        }
        
        try {
            StatObjectResponse stat = minioClient.statObject(
                StatObjectArgs.builder()
                    .bucket(bucket.getBucketName())
                    .object(objectName)
                    .build()
            );
            
            return stat;
        } catch (Exception e) {
            log.error("获取MinIO文件元数据失败: bucket={}, object={}, error={}", 
                    bucket.getBucketName(), objectName, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public InputStream getObject(StorageBucket bucket, String objectName) {
        ensureInitialized();
        
        if (bucket == null || objectName == null) {
            return null;
        }
        
        try {
            return minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(bucket.getBucketName())
                    .object(objectName)
                    .build()
            );
        } catch (Exception e) {
            log.error("获取MinIO文件失败: bucket={}, object={}, error={}", 
                    bucket.getBucketName(), objectName, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean removeObject(StorageBucket bucket, String objectName) {
        ensureInitialized();
        
        if (bucket == null || objectName == null) {
            return false;
        }
        
        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(bucket.getBucketName())
                    .object(objectName)
                    .build()
            );
            
            log.info("成功删除MinIO文件: bucket={}, object={}", bucket.getBucketName(), objectName);
            return true;
        } catch (Exception e) {
            log.error("删除MinIO文件失败: bucket={}, object={}, error={}", 
                    bucket.getBucketName(), objectName, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public String generatePresignedUrl(StorageBucket bucket, String objectName, Integer expireSeconds) {
        ensureInitialized();
        
        try {
            String bucketName = bucket.getBucketName();
            objectName = formatObjectName(objectName);
            
            // 处理过期时间，-1表示永不过期，使用默认的1小时
            int expirationSeconds = (expireSeconds == null || expireSeconds < 0) ? 3600 : expireSeconds;
            
            // 生成预签名URL
            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(expirationSeconds)
                    .build());
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