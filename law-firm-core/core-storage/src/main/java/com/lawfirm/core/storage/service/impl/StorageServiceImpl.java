package com.lawfirm.core.storage.service.impl;

import com.lawfirm.core.storage.strategy.StorageStrategy;
import com.lawfirm.model.base.storage.model.FileMetadata;
import com.lawfirm.model.base.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Service("defaultStorageService")
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final StorageStrategy storageStrategy;

    @Override
    public FileMetadata uploadFile(MultipartFile file) {
        try {
            log.info("开始上传文件: {}", file.getOriginalFilename());
            return storageStrategy.uploadFile(file);
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @Override
    public FileMetadata uploadFile(String fileName, InputStream inputStream, long size, String contentType) {
        try {
            log.info("开始上传文件: {}", fileName);
            return storageStrategy.uploadFile(fileName, inputStream, size, contentType);
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @Override
    public byte[] downloadFile(String filePath) {
        try {
            log.info("开始下载文件: {}", filePath);
            return storageStrategy.downloadFile(filePath);
        } catch (Exception e) {
            log.error("文件下载失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件下载失败", e);
        }
    }

    @Override
    public InputStream downloadFileAsStream(String filePath) {
        try {
            log.info("开始下载文件流: {}", filePath);
            return storageStrategy.downloadFileAsStream(filePath);
        } catch (Exception e) {
            log.error("文件流下载失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件流下载失败", e);
        }
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            log.info("开始删除文件: {}", filePath);
            storageStrategy.deleteFile(filePath);
        } catch (Exception e) {
            log.error("文件删除失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件删除失败", e);
        }
    }

    @Override
    public String getFileUrl(String filePath) {
        try {
            log.info("获取文件URL: {}", filePath);
            return storageStrategy.getFileUrl(filePath);
        } catch (Exception e) {
            log.error("获取文件URL失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取文件URL失败", e);
        }
    }

    @Override
    public String getFileUrl(String filePath, long expireSeconds) {
        try {
            log.info("获取带过期时间的文件URL: {}, expireSeconds: {}", filePath, expireSeconds);
            return storageStrategy.getFileUrl(filePath, expireSeconds);
        } catch (Exception e) {
            log.error("获取带过期时间的文件URL失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取带过期时间的文件URL失败", e);
        }
    }

    @Override
    public List<FileMetadata> listByBusiness(String businessType, String businessId) {
        try {
            log.info("查询业务文件列表: {}, {}", businessType, businessId);
            return storageStrategy.listByBusiness(businessType, businessId);
        } catch (Exception e) {
            log.error("查询业务文件列表失败: {}", e.getMessage(), e);
            throw new RuntimeException("查询业务文件列表失败", e);
        }
    }

    @Override
    public boolean isFileExist(String filePath) {
        try {
            log.info("检查文件是否存在: {}", filePath);
            return storageStrategy.isFileExist(filePath);
        } catch (Exception e) {
            log.error("检查文件是否存在失败: {}", e.getMessage(), e);
            throw new RuntimeException("检查文件是否存在失败", e);
        }
    }
} 