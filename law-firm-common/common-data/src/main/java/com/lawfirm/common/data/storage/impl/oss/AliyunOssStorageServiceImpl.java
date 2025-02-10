package com.lawfirm.common.data.storage.impl.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.lawfirm.common.data.storage.BaseStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * 阿里云OSS存储服务实现
 */
@Slf4j
@Service("aliyunOssStorageService")
@ConditionalOnProperty(prefix = "storage.aliyun", name = "enabled", havingValue = "true")
public class AliyunOssStorageServiceImpl implements BaseStorageService {
    @Autowired
    private OSS ossClient;

    @Autowired
    private AliyunOssProperties properties;

    @Override
    public String uploadFile(String bucketName, String objectName, InputStream stream, long size, String contentType) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(size);
            metadata.setContentType(contentType);
            ossClient.putObject(bucketName, objectName, stream, metadata);
            return getFileUrl(bucketName, objectName, properties.getDefaultExpiry());
        } catch (Exception e) {
            log.error("上传文件失败: bucketName={}, objectName={}", bucketName, objectName, e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public InputStream downloadFile(String bucketName, String objectName) {
        try {
            OSSObject object = ossClient.getObject(bucketName, objectName);
            return object.getObjectContent();
        } catch (Exception e) {
            log.error("下载文件失败: bucketName={}, objectName={}", bucketName, objectName, e);
            throw new RuntimeException("下载文件失败", e);
        }
    }

    @Override
    public void deleteFile(String bucketName, String objectName) {
        try {
            ossClient.deleteObject(bucketName, objectName);
        } catch (Exception e) {
            log.error("删除文件失败: bucketName={}, objectName={}", bucketName, objectName, e);
            throw new RuntimeException("删除文件失败", e);
        }
    }

    @Override
    public String getFileUrl(String bucketName, String objectName, Integer expiry) {
        try {
            Date expiration = new Date(System.currentTimeMillis() + (expiry * 1000L));
            URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
            return url.toString();
        } catch (Exception e) {
            log.error("获取文件URL失败: bucketName={}, objectName={}", bucketName, objectName, e);
            throw new RuntimeException("获取文件URL失败", e);
        }
    }

    @Override
    public boolean isFileExist(String bucketName, String objectName) {
        try {
            return ossClient.doesObjectExist(bucketName, objectName);
        } catch (Exception e) {
            log.error("检查文件是否存在时发生错误: bucketName={}, objectName={}", bucketName, objectName, e);
            return false;
        }
    }
} 