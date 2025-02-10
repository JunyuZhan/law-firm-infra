package com.lawfirm.core.storage.impl;

import com.lawfirm.model.base.storage.model.FileMetadata;
import com.lawfirm.model.base.storage.service.StorageService;
import com.lawfirm.model.base.storage.enums.StorageTypeEnum;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * MinIO存储服务实现
 */
@Slf4j
@Service
public class MinioStorageServiceImpl implements StorageService {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.bucket}")
    private String bucket;

    private final MinioClient minioClient;

    public MinioStorageServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public FileMetadata upload(MultipartFile file, String businessType, String businessId) {
        try {
            String filename = file.getOriginalFilename();
            return upload(file.getInputStream(), filename, file.getContentType(), businessType, businessId);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public FileMetadata upload(InputStream inputStream, String filename, String contentType, String businessType, String businessId) {
        try {
            String id = UUID.randomUUID().toString();
            String path = String.format("%s/%s/%s", businessType, businessId, id);
            
            // 上传到MinIO
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(path)
                    .stream(inputStream, inputStream.available(), -1)
                    .contentType(contentType)
                    .build());

            // 构建文件元数据
            return new FileMetadata()
                    .setId(id)
                    .setFilename(filename)
                    .setOriginalFilename(filename)
                    .setContentType(contentType)
                    .setStorageType(StorageTypeEnum.MINIO.getCode())
                    .setPath(path)
                    .setUrl(getUrl(id))
                    .setBusinessType(businessType)
                    .setBusinessId(businessId)
                    .setCreateTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now());
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            FileMetadata metadata = getMetadata(id);
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(metadata.getPath())
                    .build());
        } catch (Exception e) {
            log.error("删除文件失败", e);
            throw new RuntimeException("删除文件失败", e);
        }
    }

    @Override
    public void deleteBatch(List<String> ids) {
        ids.forEach(this::delete);
    }

    @Override
    public FileMetadata getMetadata(String id) {
        // 实际项目中应该从数据库获取
        throw new UnsupportedOperationException("未实现");
    }

    @Override
    public String getUrl(String id) {
        try {
            FileMetadata metadata = getMetadata(id);
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .object(metadata.getPath())
                    .method(Method.GET)
                    .build());
        } catch (Exception e) {
            log.error("获取文件访问URL失败", e);
            throw new RuntimeException("获取文件访问URL失败", e);
        }
    }

    @Override
    public String getUrl(String id, long expireSeconds) {
        try {
            FileMetadata metadata = getMetadata(id);
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .object(metadata.getPath())
                    .method(Method.GET)
                    .expiry((int) expireSeconds)
                    .build());
        } catch (Exception e) {
            log.error("获取文件访问URL失败", e);
            throw new RuntimeException("获取文件访问URL失败", e);
        }
    }

    @Override
    public InputStream download(String id) {
        try {
            FileMetadata metadata = getMetadata(id);
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(metadata.getPath())
                    .build());
        } catch (Exception e) {
            log.error("下载文件失败", e);
            throw new RuntimeException("下载文件失败", e);
        }
    }

    @Override
    public List<FileMetadata> listByBusiness(String businessType, String businessId) {
        // 实际项目中应该从数据库获取
        throw new UnsupportedOperationException("未实现");
    }
} 