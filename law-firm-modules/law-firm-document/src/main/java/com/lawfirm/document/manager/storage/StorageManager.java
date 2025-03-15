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
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 文档存储管理器，处理文档的存储相关操作
 */
@Slf4j
@Component
@RequiredArgsConstructor
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
}
