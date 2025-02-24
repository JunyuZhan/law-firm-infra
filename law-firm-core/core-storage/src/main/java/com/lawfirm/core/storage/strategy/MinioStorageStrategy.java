package com.lawfirm.core.storage.strategy;

import com.lawfirm.core.storage.strategy.StorageStrategy;
import com.lawfirm.model.base.storage.model.FileMetadata;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MinioStorageStrategy implements StorageStrategy {

    private final MinioClient minioClient;
    private final String bucketName;

    public MinioStorageStrategy(MinioClient minioClient, String bucketName) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    @Override
    public FileMetadata uploadFile(MultipartFile file) {
        try {
            String objectName = generateObjectName(file.getOriginalFilename());
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()
            );
            
            return new FileMetadata()
                .setFilename(objectName)
                .setOriginalFilename(file.getOriginalFilename())
                .setSize(file.getSize())
                .setContentType(file.getContentType())
                .setPath(objectName)
                .setCreateTime(LocalDateTime.now());
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public FileMetadata uploadFile(String fileName, InputStream inputStream, long size, String contentType) {
        try {
            String objectName = generateObjectName(fileName);
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, size, -1)
                    .contentType(contentType)
                    .build()
            );
            
            return new FileMetadata()
                .setFilename(objectName)
                .setOriginalFilename(fileName)
                .setSize(size)
                .setContentType(contentType)
                .setPath(objectName)
                .setCreateTime(LocalDateTime.now());
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public byte[] downloadFile(String objectName) {
        try {
            GetObjectResponse response = minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build()
            );
            return response.readAllBytes();
        } catch (Exception e) {
            log.error("下载文件失败", e);
            throw new RuntimeException("下载文件失败", e);
        }
    }

    @Override
    public InputStream downloadFileAsStream(String objectName) {
        try {
            return minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build()
            );
        } catch (Exception e) {
            log.error("下载文件失败", e);
            throw new RuntimeException("下载文件失败", e);
        }
    }

    @Override
    public void deleteFile(String objectName) {
        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build()
            );
        } catch (Exception e) {
            log.error("删除文件失败", e);
            throw new RuntimeException("删除文件失败", e);
        }
    }

    @Override
    public String getFileUrl(String objectName) {
        return getFileUrl(objectName, 7 * 24 * 60 * 60L); // 默认7天有效期
    }

    @Override
    public String getFileUrl(String objectName, long expireSeconds) {
        try {
            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry((int) expireSeconds, TimeUnit.SECONDS)
                    .build()
            );
        } catch (Exception e) {
            log.error("获取文件URL失败", e);
            throw new RuntimeException("获取文件URL失败", e);
        }
    }

    @Override
    public boolean isFileExist(String objectName) {
        try {
            minioClient.statObject(
                StatObjectArgs.builder()
                    .bucket(bucketName)
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
            // 在MinIO中，我们不需要显式地初始化分片上传
            // 直接返回对象名称作为uploadId
            return objectName;
        } catch (Exception e) {
            log.error("初始化分片上传失败", e);
            throw new RuntimeException("初始化分片上传失败", e);
        }
    }

    @Override
    public String uploadPart(String uploadId, int partNumber, MultipartFile part) {
        try {
            // 在MinIO中，我们使用putObject来上传每个分片
            String partKey = String.format("%s/part-%d", uploadId, partNumber);
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(partKey)
                    .stream(part.getInputStream(), part.getSize(), -1)
                    .build()
            );
            return partKey;
        } catch (Exception e) {
            log.error("上传分片失败", e);
            throw new RuntimeException("上传分片失败", e);
        }
    }

    @Override
    public String completeMultipartUpload(String uploadId, String objectName) {
        try {
            // 在MinIO中，我们需要手动合并所有分片
            // 这里的实现是一个简化版本，实际使用时需要根据具体需求调整
            return objectName;
        } catch (Exception e) {
            log.error("完成分片上传失败", e);
            throw new RuntimeException("完成分片上传失败", e);
        }
    }

    @Override
    public void abortMultipartUpload(String uploadId) {
        try {
            // 删除所有分片
            Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .prefix(uploadId + "/")
                    .recursive(true)
                    .build()
            );
            
            for (Result<Item> result : results) {
                minioClient.removeObject(
                    RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(result.get().objectName())
                        .build()
                );
            }
        } catch (Exception e) {
            log.error("终止分片上传失败", e);
            throw new RuntimeException("终止分片上传失败", e);
        }
    }

    @Override
    public List<FileMetadata> listByBusiness(String businessType, String businessId) {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .prefix(String.format("%s/%s/", businessType, businessId))
                    .recursive(true)
                    .build()
            );
            
            List<FileMetadata> files = new ArrayList<>();
            for (Result<Item> result : results) {
                Item item = result.get();
                FileMetadata metadata = new FileMetadata()
                    .setFilename(item.objectName())
                    .setSize(item.size())
                    .setPath(item.objectName())
                    .setContentType(item.userMetadata().get("Content-Type"))
                    .setBusinessType(businessType)
                    .setBusinessId(businessId)
                    .setCreateTime(LocalDateTime.ofInstant(item.lastModified().toInstant(), ZoneId.systemDefault()));
                files.add(metadata);
            }
            return files;
        } catch (Exception e) {
            log.error("获取业务相关文件列表失败", e);
            throw new RuntimeException("获取业务相关文件列表失败", e);
        }
    }

    private String generateObjectName(String filename) {
        LocalDateTime now = LocalDateTime.now();
        return String.format("%d/%02d/%s_%s",
            now.getYear(),
            now.getMonthValue(),
            UUID.randomUUID().toString().replace("-", ""),
            filename);
    }
} 