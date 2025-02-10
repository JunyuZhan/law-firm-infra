package com.lawfirm.core.storage.service.impl;

import com.lawfirm.model.base.storage.service.StorageService;
import com.lawfirm.core.storage.strategy.StorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 基础存储服务实现类
 */
@Slf4j
@Service
public class BaseStorageServiceImpl implements StorageService {

    private final StorageStrategy storageStrategy;

    public BaseStorageServiceImpl(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String objectName = generateObjectName(file.getOriginalFilename());
        return storageStrategy.uploadFile(objectName, file);
    }

    @Override
    public byte[] downloadFile(String filePath) {
        return storageStrategy.downloadFile(filePath);
    }

    @Override
    public void deleteFile(String filePath) {
        storageStrategy.deleteFile(filePath);
    }

    @Override
    public String getFileUrl(String filePath) {
        return storageStrategy.getFileUrl(filePath);
    }

    /**
     * 生成存储对象名称
     * 格式：年/月/UUID_文件名
     */
    private String generateObjectName(String filename) {
        LocalDateTime now = LocalDateTime.now();
        return String.format("%d/%02d/%s_%s",
                now.getYear(),
                now.getMonthValue(),
                UUID.randomUUID().toString().replace("-", ""),
                filename);
    }
} 