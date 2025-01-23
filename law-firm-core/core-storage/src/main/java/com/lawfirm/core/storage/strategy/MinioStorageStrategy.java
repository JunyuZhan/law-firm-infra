package com.lawfirm.core.storage.strategy;

import com.lawfirm.core.storage.config.StorageProperties;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * MinIO 存储策略实现
 */
@Slf4j
public class MinioStorageStrategy extends AbstractStorageStrategy {
    
    private final MinioClient minioClient;
    private final StorageProperties.MinioConfig config;
    
    public MinioStorageStrategy(StorageProperties storageProperties) {
        this.config = storageProperties.getMinio();
        this.minioClient = MinioClient.builder()
                .endpoint(config.getEndpoint())
                .credentials(config.getAccessKey(), config.getSecretKey())
                .build();
        
        try {
            // 确保 bucket 存在
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(config.getBucketName())
                    .build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(config.getBucketName())
                        .build());
                log.info("Created MinIO bucket: {}", config.getBucketName());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize MinIO client", e);
        }
    }
    
    @Override
    protected String getStorageType() {
        return "minio";
    }
    
    @Override
    protected void doUpload(InputStream inputStream, String path) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(config.getBucketName())
                    .object(path)
                    .stream(inputStream, -1, 10485760)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to MinIO", e);
        }
    }
    
    @Override
    public void doDelete(String path) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(config.getBucketName())
                    .object(path)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file from MinIO", e);
        }
    }
    
    @Override
    public String doGetUrl(String path) {
        return doGetUrl(path, config.getDefaultUrlExpiry());
    }
    
    @Override
    public String doGetUrl(String path, long expireSeconds) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(config.getBucketName())
                    .object(path)
                    .expiry((int) expireSeconds, TimeUnit.SECONDS)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get MinIO file URL", e);
        }
    }
    
    @Override
    public InputStream doDownload(String path) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(config.getBucketName())
                    .object(path)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file from MinIO", e);
        }
    }
} 