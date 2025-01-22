package com.lawfirm.common.data.storage.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.lawfirm.common.data.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * 阿里云OSS存储服务实现
 */
@Slf4j
@Service("aliyunOssStorageService")
@ConditionalOnProperty(prefix = "storage.aliyun", name = "enabled", havingValue = "true")
public class AliyunOssStorageService implements StorageService {

    @Autowired
    private OSS ossClient;

    @Override
    public String uploadFile(String bucketName, String objectName, InputStream stream, long size, String contentType) {
        try {
            // 创建上传Object的元信息
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(size);
            metadata.setContentType(contentType);

            // 上传文件
            ossClient.putObject(bucketName, objectName, stream, metadata);

            return getFileUrl(bucketName, objectName, 7 * 24 * 60 * 60); // 默认7天有效期
        } catch (Exception e) {
            log.error("阿里云OSS上传文件失败", e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public InputStream downloadFile(String bucketName, String objectName) {
        try {
            OSSObject ossObject = ossClient.getObject(bucketName, objectName);
            return ossObject.getObjectContent();
        } catch (Exception e) {
            log.error("阿里云OSS下载文件失败", e);
            throw new RuntimeException("下载文件失败", e);
        }
    }

    @Override
    public void deleteFile(String bucketName, String objectName) {
        try {
            ossClient.deleteObject(bucketName, objectName);
        } catch (Exception e) {
            log.error("阿里云OSS删除文件失败", e);
            throw new RuntimeException("删除文件失败", e);
        }
    }

    @Override
    public String getFileUrl(String bucketName, String objectName, Integer expiry) {
        try {
            Date expiration = new Date(System.currentTimeMillis() + expiry * 1000L);
            URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
            return url.toString();
        } catch (Exception e) {
            log.error("阿里云OSS获取文件URL失败", e);
            throw new RuntimeException("获取文件URL失败", e);
        }
    }

    @Override
    public boolean isFileExist(String bucketName, String objectName) {
        return ossClient.doesObjectExist(bucketName, objectName);
    }
} 