package com.lawfirm.core.storage.service.impl;

import com.lawfirm.model.base.storage.model.FileMetadata;
import com.lawfirm.model.base.storage.service.StorageService;
import com.lawfirm.core.storage.strategy.StorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service("baseStorageService")
public class BaseStorageServiceImpl implements StorageService {

    private final StorageStrategy storageStrategy;

    public BaseStorageServiceImpl(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    @Override
    public FileMetadata uploadFile(MultipartFile file) {
        try {
            return storageStrategy.uploadFile(file);
        } catch (Exception e) {
            log.error("上传文件失败: {}", e.getMessage(), e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public FileMetadata uploadFile(String fileName, InputStream inputStream, long size, String contentType) {
        try {
            return storageStrategy.uploadFile(fileName, inputStream, size, contentType);
        } catch (Exception e) {
            log.error("上传文件失败: {}", e.getMessage(), e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public byte[] downloadFile(String filePath) {
        try {
            return storageStrategy.downloadFile(filePath);
        } catch (Exception e) {
            log.error("下载文件失败: {}", e.getMessage(), e);
            throw new RuntimeException("下载文件失败", e);
        }
    }

    @Override
    public InputStream downloadFileAsStream(String filePath) {
        try {
            return storageStrategy.downloadFileAsStream(filePath);
        } catch (Exception e) {
            log.error("下载文件失败: {}", e.getMessage(), e);
            throw new RuntimeException("下载文件失败", e);
        }
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            storageStrategy.deleteFile(filePath);
        } catch (Exception e) {
            log.error("删除文件失败: {}", e.getMessage(), e);
            throw new RuntimeException("删除文件失败", e);
        }
    }

    @Override
    public String getFileUrl(String filePath) {
        try {
            return storageStrategy.getFileUrl(filePath);
        } catch (Exception e) {
            log.error("获取文件URL失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取文件URL失败", e);
        }
    }

    @Override
    public String getFileUrl(String filePath, long expireSeconds) {
        try {
            return storageStrategy.getFileUrl(filePath, expireSeconds);
        } catch (Exception e) {
            log.error("获取文件URL失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取文件URL失败", e);
        }
    }

    @Override
    public List<FileMetadata> listByBusiness(String businessType, String businessId) {
        try {
            return storageStrategy.listByBusiness(businessType, businessId);
        } catch (Exception e) {
            log.error("获取业务相关文件列表失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取业务相关文件列表失败", e);
        }
    }

    @Override
    public boolean isFileExist(String filePath) {
        try {
            return storageStrategy.isFileExist(filePath);
        } catch (Exception e) {
            log.error("检查文件是否存在失败: {}", e.getMessage(), e);
            throw new RuntimeException("检查文件是否存在失败", e);
        }
    }
}