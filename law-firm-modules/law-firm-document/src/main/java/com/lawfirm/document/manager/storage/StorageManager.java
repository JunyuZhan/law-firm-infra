package com.lawfirm.document.manager.storage;

import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.enums.StorageTypeEnum;
import com.lawfirm.core.storage.strategy.StorageContext;
import com.lawfirm.core.storage.strategy.StorageStrategy;
import com.lawfirm.core.storage.config.StorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 文档存储管理器，处理文档的存储相关操作
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "true", matchIfMissing = false)
public class StorageManager {

    private final FileService fileService;
    private final BucketService bucketService;
    private final StorageContext storageContext;
    private final StorageProperties storageProperties;

    /**
     * 上传文档
     *
     * @param file 文件
     * @param bucket 存储桶
     * @return 文件对象
     */
    public FileObject uploadDocument(MultipartFile file, StorageBucket bucket) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }

        // 获取存储策略
        StorageStrategy strategy = storageContext.getStrategy(bucket);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的存储类型: " + bucket.getStorageType());
        }

        // 生成文件对象
        FileObject fileObject = new FileObject();
        fileObject.setBucketId(bucket.getId());
        fileObject.setFileName(file.getOriginalFilename());
        fileObject.setFileSize(file.getSize());
        fileObject.setContentType(file.getContentType());
        fileObject.setStoragePath(generateStoragePath(file.getOriginalFilename()));

        // 上传文件
        try (InputStream inputStream = file.getInputStream()) {
            boolean success = strategy.uploadFile(bucket, fileObject, inputStream);
            if (!success) {
                throw new IOException("文件上传失败");
            }
        }

        return fileObject;
    }

    /**
     * 下载文档
     *
     * @param fileObject 文件对象
     * @param bucket 存储桶
     * @return 文件输入流
     */
    public InputStream downloadDocument(FileObject fileObject, StorageBucket bucket) {
        if (fileObject == null || bucket == null) {
            throw new IllegalArgumentException("文件对象和存储桶不能为空");
        }

        // 获取存储策略
        StorageStrategy strategy = storageContext.getStrategy(bucket);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的存储类型: " + bucket.getStorageType());
        }

        return strategy.getObject(bucket, fileObject.getStoragePath());
    }

    /**
     * 删除文档
     *
     * @param fileObject 文件对象
     * @param bucket 存储桶
     * @return 是否删除成功
     */
    public boolean deleteDocument(FileObject fileObject, StorageBucket bucket) {
        if (fileObject == null || bucket == null) {
            return false;
        }

        // 获取存储策略
        StorageStrategy strategy = storageContext.getStrategy(bucket);
        if (strategy == null) {
            return false;
        }

        return strategy.removeObject(bucket, fileObject.getStoragePath());
    }

    /**
     * 生成存储路径
     *
     * @param originalFilename 原始文件名
     * @return 存储路径
     */
    private String generateStoragePath(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return storageProperties.getPath().getBaseDir() + "/" + uuid + extension;
    }

    /**
     * 存储模板内容
     *
     * @param templateCode 模板编码
     * @param content 模板内容
     * @param templateType 模板类型
     * @return 存储路径
     */
    public String storeTemplateContent(String templateCode, String content, String templateType) throws IOException {
        // 获取默认存储桶
        StorageBucket bucket = getDefaultBucket();
        
        // 获取存储策略
        StorageStrategy strategy = storageContext.getStrategy(bucket);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的存储类型: " + bucket.getStorageType());
        }
        
        // 生成存储路径
        String extension = getExtensionByTemplateType(templateType);
        String storagePath = storageProperties.getPath().getBaseDir() + "/templates/" + templateCode + extension;
        
        // 存储内容
        boolean success = strategy.uploadText(bucket, storagePath, content);
        if (!success) {
            throw new IOException("模板内容存储失败");
        }
        
        return storagePath;
    }
    
    /**
     * 获取存储类型
     * 
     * @return 存储类型字符串
     */
    public String getStorageType() {
        return storageProperties.getType().name();
    }
    
    /**
     * 获取默认存储桶
     * 
     * @return 默认存储桶
     */
    private StorageBucket getDefaultBucket() {
        return bucketService.getDefaultBucket();
    }
    
    /**
     * 根据模板类型获取文件扩展名
     * 
     * @param templateType 模板类型
     * @return 文件扩展名
     */
    private String getExtensionByTemplateType(String templateType) {
        switch (templateType.toUpperCase()) {
            case "WORD":
                return ".docx";
            case "PDF":
                return ".pdf";
            case "HTML":
                return ".html";
            case "TEXT":
                return ".txt";
            default:
                return ".template";
        }
    }
    
    /**
     * 获取存储总大小
     * 
     * @return 存储总大小（字节）
     */
    public long getTotalStorageSize() {
        try {
            // 获取本地存储路径
            String basePath = storageProperties.getLocal().getBasePath();
            Path storagePath = Paths.get(basePath);
            if (Files.exists(storagePath)) {
                return Files.walk(storagePath)
                        .filter(Files::isRegularFile)
                        .mapToLong(p -> {
                            try { return Files.size(p); } catch (Exception e) { return 0L; }
                        }).sum();
            }
        } catch (Exception e) {
            log.error("获取存储总大小失败", e);
        }
        return 0L;
    }
    
    /**
     * 获取存储文件总数
     * 
     * @return 存储文件总数
     */
    public long getTotalFileCount() {
        try {
            // 获取本地存储路径
            String basePath = storageProperties.getLocal().getBasePath();
            Path storagePath = Paths.get(basePath);
            if (Files.exists(storagePath)) {
                return Files.walk(storagePath)
                        .filter(Files::isRegularFile)
                        .count();
            }
        } catch (Exception e) {
            log.error("获取存储文件总数失败", e);
        }
        return 0L;
    }
}
