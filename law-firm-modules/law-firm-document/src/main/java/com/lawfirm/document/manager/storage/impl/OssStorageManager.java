package com.lawfirm.document.manager.storage.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.lawfirm.document.config.properties.DocumentProperties;
import com.lawfirm.document.manager.storage.DocumentStorageManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 阿里云OSS存储管理器实现
 */
@Slf4j
public class OssStorageManager implements DocumentStorageManager {

    private final OSS ossClient;
    private final String bucketName;
    private final String endpoint;
    private final DocumentProperties properties;

    public OssStorageManager(OSS ossClient, DocumentProperties properties) {
        this.ossClient = ossClient;
        this.properties = properties;
        this.bucketName = properties.getStorage().getOssBucketName();
        this.endpoint = properties.getStorage().getOssEndpoint();
    }

    @Override
    public String uploadDocument(MultipartFile file, String path) throws IOException {
        // 1. 生成存储路径
        String storagePath = generateStoragePath(file.getOriginalFilename());
        
        // 2. 设置文件元数据
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        
        // 3. 上传文件
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, storagePath, file.getInputStream(), metadata);
        ossClient.putObject(putObjectRequest);
        
        return storagePath;
    }

    @Override
    public void deleteDocument(String path) {
        ossClient.deleteObject(bucketName, path);
    }

    @Override
    public InputStream getDocument(String path) throws IOException {
        OSSObject ossObject = ossClient.getObject(bucketName, path);
        return ossObject.getObjectContent();
    }

    @Override
    public String getDocumentUrl(String path) {
        return getDocumentUrl(path, null);
    }

    @Override
    public String getDocumentUrl(String path, Long expireTime) {
        if (expireTime == null) {
            // 返回永久访问URL
            return String.format("https://%s.%s/%s", bucketName, endpoint, path);
        } else {
            // 生成带有效期的签名URL
            Date expiration = new Date(System.currentTimeMillis() + expireTime * 1000);
            URL url = ossClient.generatePresignedUrl(bucketName, path, expiration);
            return url.toString();
        }
    }

    @Override
    public boolean exists(String path) {
        return ossClient.doesObjectExist(bucketName, path);
    }

    @Override
    public void copyDocument(String sourcePath, String targetPath) throws IOException {
        ossClient.copyObject(bucketName, sourcePath, bucketName, targetPath);
    }

    @Override
    public void moveDocument(String sourcePath, String targetPath) throws IOException {
        // 1. 复制文件
        copyDocument(sourcePath, targetPath);
        // 2. 删除源文件
        deleteDocument(sourcePath);
    }

    @Override
    public long getSize(String path) {
        ObjectMetadata metadata = ossClient.getObjectMetadata(bucketName, path);
        return metadata.getContentLength();
    }

    @Override
    public long getLastModified(String path) {
        ObjectMetadata metadata = ossClient.getObjectMetadata(bucketName, path);
        return metadata.getLastModified().getTime();
    }

    /**
     * 生成存储路径
     */
    private String generateStoragePath(String originalFilename) {
        // 生成日期路径：yyyy/MM/dd
        String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        
        // 生成文件名：时间戳 + 随机数 + 原始文件名
        String filename = System.currentTimeMillis() + "_" + ((int) (Math.random() * 10000)) 
            + "_" + originalFilename;
        
        return datePath + "/" + filename;
    }
} 