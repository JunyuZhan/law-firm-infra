package com.lawfirm.core.storage.service.impl;

import com.lawfirm.model.base.storage.model.FileMetadata;
import com.lawfirm.model.base.storage.service.StorageService;
import com.lawfirm.core.storage.strategy.StorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageStrategy storageStrategy;

    @Override
    public FileMetadata uploadFile(MultipartFile file) {
        try {
            return storageStrategy.uploadFile(file);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public FileMetadata uploadFile(String filename, InputStream inputStream, long size, String contentType) {
        try {
            return storageStrategy.uploadFile(filename, inputStream, size, contentType);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public void deleteFile(String path) {
        try {
            storageStrategy.deleteFile(path);
        } catch (Exception e) {
            log.error("删除文件失败", e);
            throw new RuntimeException("删除文件失败", e);
        }
    }

    @Override
    public void deleteBatch(List<String> paths) {
        for (String path : paths) {
            deleteFile(path);
        }
    }

    @Override
    public String getFileUrl(String path) {
        try {
            return storageStrategy.getFileUrl(path);
        } catch (Exception e) {
            log.error("获取文件URL失败", e);
            throw new RuntimeException("获取文件URL失败", e);
        }
    }

    @Override
    public String getFileUrl(String path, long expireSeconds) {
        try {
            return storageStrategy.getFileUrl(path, expireSeconds);
        } catch (Exception e) {
            log.error("获取文件URL失败", e);
            throw new RuntimeException("获取文件URL失败", e);
        }
    }

    @Override
    public InputStream downloadFile(String path) {
        try {
            return storageStrategy.downloadFile(path);
        } catch (Exception e) {
            log.error("下载文件失败", e);
            throw new RuntimeException("下载文件失败", e);
        }
    }

    @Override
    public boolean isFileExist(String path) {
        try {
            return storageStrategy.isFileExist(path);
        } catch (Exception e) {
            log.error("检查文件是否存在失败", e);
            throw new RuntimeException("检查文件是否存在失败", e);
        }
    }

    @Override
    public List<FileMetadata> listByBusiness(String businessType, String businessId) {
        // TODO: 实现按业务类型和业务ID查询文件列表
        return new ArrayList<>();
    }
} 