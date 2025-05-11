package com.lawfirm.core.storage.strategy.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.strategy.AbstractStorageStrategy;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.enums.StorageTypeEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 本地存储策略实现
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "law-firm.core.storage.enabled", havingValue = "true", matchIfMissing = false)
public class LocalStorageStrategy extends AbstractStorageStrategy {
    
    private final StorageProperties storageProperties;
    
    /**
     * 本地存储的根目录
     */
    private String basePath;
    
    /**
     * URL前缀
     */
    private String urlPrefix;
    
    /**
     * 构造函数
     */
    public LocalStorageStrategy(StorageProperties storageProperties) {
        super(StorageTypeEnum.LOCAL);
        this.storageProperties = storageProperties;
    }
    
    @Override
    public void initialize() {
        log.info("初始化本地存储策略");
        
        this.basePath = storageProperties.getLocal().getBasePath();
        this.urlPrefix = storageProperties.getLocal().getUrlPrefix();
        
        // 确保存储目录存在
        try {
            Files.createDirectories(Paths.get(basePath));
            log.info("本地存储目录已创建: {}", basePath);
        } catch (IOException e) {
            log.error("创建本地存储目录失败: {}", basePath, e);
            throw new RuntimeException("无法创建本地存储目录", e);
        }
    }
    
    @Override
    protected boolean isEnabled() {
        return storageProperties.getLocal().isEnabled();
    }
    
    @Override
    public boolean createBucket(String bucketName, boolean isPublic) {
        ensureInitialized();
        
        try {
            // 在本地存储中，桶就是一个目录
            Path bucketPath = Paths.get(basePath, bucketName);
            Files.createDirectories(bucketPath);
            log.info("本地存储桶创建成功: {}", bucketName);
            return true;
        } catch (IOException e) {
            log.error("创建本地存储桶失败: {}", bucketName, e);
            return false;
        }
    }
    
    @Override
    public boolean bucketExists(String bucketName) {
        ensureInitialized();
        
        Path bucketPath = Paths.get(basePath, bucketName);
        return Files.exists(bucketPath) && Files.isDirectory(bucketPath);
    }
    
    @Override
    public boolean removeBucket(String bucketName) {
        ensureInitialized();
        
        try {
            Path bucketPath = Paths.get(basePath, bucketName);
            if (Files.exists(bucketPath)) {
                // 目录不为空时无法删除
                if (Files.list(bucketPath).findAny().isPresent()) {
                    log.warn("无法删除非空的存储桶: {}", bucketName);
                    return false;
                }
                
                Files.delete(bucketPath);
                log.info("本地存储桶删除成功: {}", bucketName);
                return true;
            }
            return false;
        } catch (IOException e) {
            log.error("删除本地存储桶失败: {}", bucketName, e);
            return false;
        }
    }
    
    @Override
    public boolean uploadFile(StorageBucket bucket, FileObject fileObject, InputStream inputStream) {
        ensureInitialized();
        
        try {
            // 构建文件存储路径：basePath/bucketName/yyyy/MM/dd/fileName_uuid
            String objectName = buildObjectName(fileObject);
            Path targetPath = getFullPath(bucket.getBucketName(), objectName);
            
            // 确保目标目录存在
            Files.createDirectories(targetPath.getParent());
            
            // 写入文件
            try (OutputStream outputStream = new FileOutputStream(targetPath.toFile())) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
            
            // 设置存储路径到文件对象
            fileObject.setStoragePath(objectName);
            
            log.info("文件上传成功: {}", objectName);
            return true;
        } catch (IOException e) {
            log.error("文件上传失败", e);
            return false;
        }
    }
    
    @Override
    public Object getObjectMetadata(StorageBucket bucket, String objectName) {
        ensureInitialized();
        
        try {
            Path filePath = getFullPath(bucket.getBucketName(), objectName);
            if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
                return Files.readAttributes(filePath, "lastModifiedTime,size");
            }
            return null;
        } catch (IOException e) {
            log.error("获取文件元数据失败: {}", objectName, e);
            return null;
        }
    }
    
    @Override
    public InputStream getObject(StorageBucket bucket, String objectName) {
        ensureInitialized();
        
        try {
            Path filePath = getFullPath(bucket.getBucketName(), objectName);
            if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
                return Files.newInputStream(filePath);
            }
            return null;
        } catch (IOException e) {
            log.error("获取文件流失败: {}", objectName, e);
            return null;
        }
    }
    
    @Override
    public boolean removeObject(StorageBucket bucket, String objectName) {
        ensureInitialized();
        
        try {
            Path filePath = getFullPath(bucket.getBucketName(), objectName);
            if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
                Files.delete(filePath);
                log.info("文件删除成功: {}", objectName);
                return true;
            }
            return false;
        } catch (IOException e) {
            log.error("文件删除失败: {}", objectName, e);
            return false;
        }
    }
    
    @Override
    public String generatePresignedUrl(StorageBucket bucket, String objectName, Integer expireSeconds) {
        ensureInitialized();
        
        try {
            objectName = formatObjectName(objectName);
            
            // 本地存储的URL实际上是一个静态文件路径，不支持真正的预签名
            // 我们直接构造一个可访问的URL
            String urlPrefix = storageProperties.getLocal().getUrlPrefix();
            if (!urlPrefix.endsWith("/")) {
                urlPrefix += "/";
            }
            
            // 如果外部传入了桶名，我们在URL中包含桶名
            String bucketName = bucket.getBucketName();
            if (StringUtils.hasText(bucketName)) {
                return urlPrefix + bucketName + "/" + objectName;
            } else {
                return urlPrefix + objectName;
            }
        } catch (Exception e) {
            log.error("生成本地文件URL失败: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 根据文件对象构建对象名称
     */
    private String buildObjectName(FileObject fileObject) {
        LocalDateTime now = LocalDateTime.now();
        String datePath = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        
        // 文件名 + UUID 防止重复
        String fileName = fileObject.getFileName();
        String fileExtension = "";
        
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            fileExtension = fileName.substring(dotIndex);
            fileName = fileName.substring(0, dotIndex);
        }
        
        return datePath + "/" + fileName + "_" + UUID.randomUUID().toString().replace("-", "") + fileExtension;
    }
    
    /**
     * 获取完整的文件路径
     */
    private Path getFullPath(String bucketName, String objectName) {
        return Paths.get(basePath, bucketName, formatObjectName(objectName));
    }
} 