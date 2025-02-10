package com.lawfirm.core.storage.strategy;

import com.lawfirm.model.base.storage.model.FileMetadata;
import com.lawfirm.core.storage.config.StorageProperties;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * MinIO存储策略实现
 */
@Slf4j
@Component
public class MinioStorageStrategy extends AbstractStorageStrategy {
    
    @Autowired
    private MinioClient minioClient;
    
    @Autowired
    private StorageProperties storageProperties;

    @Override
    protected String getStorageType() {
        return "minio";
    }

    @Override
    protected void doUpload(InputStream inputStream, String path) {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(storageProperties.getBucketName())
                    .object(path)
                    .stream(inputStream, -1, 10485760)
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            log.error("上传文件到MinIO失败", e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    protected void doDelete(String path) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(storageProperties.getBucketName())
                            .object(path)
                            .build()
            );
        } catch (Exception e) {
            log.error("从MinIO删除文件失败", e);
            throw new RuntimeException("删除文件失败", e);
        }
    }

    @Override
    protected String doGetUrl(String path) {
        return doGetUrl(path, storageProperties.getDefaultUrlExpiry());
    }

    @Override
    protected String doGetUrl(String path, long expireSeconds) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(storageProperties.getBucketName())
                            .object(path)
                            .expiry((int) expireSeconds, TimeUnit.SECONDS)
                            .method(Method.GET)
                            .build()
            );
        } catch (Exception e) {
            log.error("获取MinIO文件URL失败", e);
            throw new RuntimeException("获取文件URL失败", e);
        }
    }

    @Override
    protected InputStream doDownload(String path) {
        try {
            GetObjectResponse response = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(storageProperties.getBucketName())
                            .object(path)
                            .build()
            );
            
            // 将响应流转换为字节数组，以便多次读取
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = response.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            log.error("从MinIO下载文件失败", e);
            throw new RuntimeException("下载文件失败", e);
        }
    }

    @Override
    protected String uploadFile(String objectName, InputStream inputStream, long size, String contentType) {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(storageProperties.getBucketName())
                    .object(objectName)
                    .stream(inputStream, size, 10485760)
                    .contentType(contentType)
                    .build();
            minioClient.putObject(args);
            return objectName;
        } catch (Exception e) {
            log.error("上传文件到MinIO失败", e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public boolean isFileExist(String objectName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(storageProperties.getBucketName())
                            .object(objectName)
                            .build()
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String initMultipartUpload(String objectName) {
        try {
            CreateMultipartUploadResponse response = minioClient.createMultipartUpload(
                    CreateMultipartUploadArgs.builder()
                            .bucket(storageProperties.getBucketName())
                            .object(objectName)
                            .build()
            );
            return response.result().uploadId();
        } catch (Exception e) {
            log.error("初始化分片上传失败", e);
            throw new RuntimeException("初始化分片上传失败", e);
        }
    }
    
    @Override
    public String uploadPart(String uploadId, int partNumber, MultipartFile part) {
        try {
            UploadPartResponse response = minioClient.uploadPart(
                    UploadPartArgs.builder()
                            .bucket(storageProperties.getBucketName())
                            .object(part.getOriginalFilename())
                            .uploadId(uploadId)
                            .partNumber(partNumber)
                            .stream(part.getInputStream(), part.getSize(), -1)
                            .build()
            );
            return response.etag();
        } catch (Exception e) {
            log.error("上传分片失败", e);
            throw new RuntimeException("上传分片失败", e);
        }
    }
    
    @Override
    public String completeMultipartUpload(String uploadId, String objectName) {
        try {
            minioClient.completeMultipartUpload(
                    CompleteMultipartUploadArgs.builder()
                            .bucket(storageProperties.getBucketName())
                            .object(objectName)
                            .uploadId(uploadId)
                            .build()
            );
            return objectName;
        } catch (Exception e) {
            log.error("完成分片上传失败", e);
            throw new RuntimeException("完成分片上传失败", e);
        }
    }
    
    @Override
    public void abortMultipartUpload(String uploadId) {
        try {
            minioClient.abortMultipartUpload(
                    AbortMultipartUploadArgs.builder()
                            .bucket(storageProperties.getBucketName())
                            .object(uploadId)
                            .uploadId(uploadId)
                            .build()
            );
        } catch (Exception e) {
            log.error("终止分片上传失败", e);
            throw new RuntimeException("终止分片上传失败", e);
        }
    }
} 