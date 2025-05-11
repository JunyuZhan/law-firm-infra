package com.lawfirm.core.storage.service.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.strategy.StorageContext;
import com.lawfirm.core.storage.strategy.StorageStrategy;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.mapper.StorageBucketMapper;
import com.lawfirm.model.storage.mapper.FileObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件下载处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "law-firm.core.storage.enabled", havingValue = "true", matchIfMissing = false)
public class FileDownloader {

    private final StorageContext storageContext;
    private final FileObjectMapper fileObjectMapper;
    private final StorageBucketMapper bucketMapper;
    private final StorageProperties storageProperties;
    
    /**
     * 下载文件
     *
     * @param fileId 文件ID
     * @param outputStream 输出流
     * @return 是否成功
     */
    public boolean download(Long fileId, OutputStream outputStream) {
        if (fileId == null || outputStream == null) {
            return false;
        }
        
        // 获取文件对象
        FileObject fileObject = fileObjectMapper.selectById(fileId);
        if (fileObject == null) {
            log.error("文件不存在: {}", fileId);
            return false;
        }
        
        // 获取存储桶
        StorageBucket bucket = bucketMapper.selectById(fileObject.getBucketId());
        if (bucket == null) {
            log.error("存储桶不存在: {}", fileObject.getBucketId());
            return false;
        }
        
        // 获取存储策略
        StorageStrategy strategy = storageContext.getStrategy(bucket.getStorageType());
        if (strategy == null) {
            log.error("不支持的存储类型: {}", bucket.getStorageType());
            return false;
        }
        
        try {
            // 获取文件输入流
            InputStream inputStream = strategy.getObject(bucket, fileObject.getStoragePath());
            if (inputStream == null) {
                log.error("获取文件输入流失败: {}", fileObject.getStoragePath());
                return false;
            }
            
            // 复制文件内容
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            // 更新下载次数
            fileObject.setDownloadCount(fileObject.getDownloadCount() + 1);
            fileObject.setLastDownloadTime(System.currentTimeMillis());
            fileObjectMapper.updateById(fileObject);
            
            return true;
        } catch (Exception e) {
            log.error("下载文件失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 获取文件下载URL
     *
     * @param fileId 文件ID
     * @param expireSeconds 过期时间（秒）
     * @return 下载URL
     */
    public String getDownloadUrl(Long fileId, Integer expireSeconds) {
        if (fileId == null) {
            return null;
        }
        
        // 获取文件对象
        FileObject fileObject = fileObjectMapper.selectById(fileId);
        if (fileObject == null) {
            log.error("文件不存在: {}", fileId);
            return null;
        }
        
        // 获取存储桶
        StorageBucket bucket = bucketMapper.selectById(fileObject.getBucketId());
        if (bucket == null) {
            log.error("存储桶不存在: {}", fileObject.getBucketId());
            return null;
        }
        
        // 获取存储策略
        StorageStrategy strategy = storageContext.getStrategy(bucket.getStorageType());
        if (strategy == null) {
            log.error("不支持的存储类型: {}", bucket.getStorageType());
            return null;
        }
        
        try {
            // 生成下载URL
            String url = strategy.generatePresignedUrl(bucket, fileObject.getStoragePath(), expireSeconds);
            
            // 更新访问次数
            fileObject.setAccessCount(fileObject.getAccessCount() + 1);
            fileObject.setLastAccessTime(System.currentTimeMillis());
            fileObjectMapper.updateById(fileObject);
            
            return url;
        } catch (Exception e) {
            log.error("生成下载URL失败: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 将文件下载为字节数组
     *
     * @param fileId 文件ID
     * @return 文件字节数组
     * @throws IOException IO异常
     */
    public byte[] downloadAsBytes(Long fileId) throws IOException {
        // 获取文件对象
        FileObject fileObject = fileObjectMapper.selectById(fileId);
        if (fileObject == null) {
            throw new IOException("文件不存在");
        }
        
        // 获取存储桶
        StorageBucket bucket = bucketMapper.selectById(fileObject.getBucketId());
        if (bucket == null) {
            throw new IOException("存储桶不存在");
        }
        
        // 获取存储策略
        StorageStrategy strategy = storageContext.getStrategy(bucket.getStorageType());
        if (strategy == null) {
            throw new IOException("不支持的存储类型");
        }
        
        // 获取文件输入流
        try (InputStream inputStream = strategy.getObject(bucket, fileObject.getStoragePath())) {
            if (inputStream == null) {
                throw new IOException("获取文件输入流失败");
            }
            
            // 读取文件内容
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            // 更新下载次数
            fileObject.setDownloadCount(fileObject.getDownloadCount() + 1);
            fileObject.setLastDownloadTime(System.currentTimeMillis());
            fileObjectMapper.updateById(fileObject);
            
            return outputStream.toByteArray();
        }
    }
    
    /**
     * 将文件下载为ResponseEntity
     *
     * @param fileId 文件ID
     * @return ResponseEntity对象
     * @throws IOException IO异常
     */
    public ResponseEntity<byte[]> downloadAsResponse(Long fileId) throws IOException {
        // 获取文件对象
        FileObject fileObject = fileObjectMapper.selectById(fileId);
        if (fileObject == null) {
            throw new IOException("文件不存在");
        }
        
        // 下载文件内容
        byte[] content = downloadAsBytes(fileId);
        
        // 构建响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(content.length);
        headers.setContentDispositionFormData("attachment", 
                URLEncoder.encode(fileObject.getFileName(), StandardCharsets.UTF_8));
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(content);
    }
    
    /**
     * 将文件下载为输入流
     *
     * @param fileId 文件ID
     * @return 文件输入流
     */
    public InputStream downloadAsStream(Long fileId) {
        // 获取文件对象
        FileObject fileObject = fileObjectMapper.selectById(fileId);
        if (fileObject == null) {
            return null;
        }
        
        // 获取存储桶
        StorageBucket bucket = bucketMapper.selectById(fileObject.getBucketId());
        if (bucket == null) {
            return null;
        }
        
        // 获取存储策略
        StorageStrategy strategy = storageContext.getStrategy(bucket.getStorageType());
        if (strategy == null) {
            return null;
        }
        
        // 获取文件输入流
        InputStream inputStream = strategy.getObject(bucket, fileObject.getStoragePath());
        if (inputStream != null) {
            // 更新下载次数
            fileObject.setDownloadCount(fileObject.getDownloadCount() + 1);
            fileObject.setLastDownloadTime(System.currentTimeMillis());
            fileObjectMapper.updateById(fileObject);
        }
        
        return inputStream;
    }
} 