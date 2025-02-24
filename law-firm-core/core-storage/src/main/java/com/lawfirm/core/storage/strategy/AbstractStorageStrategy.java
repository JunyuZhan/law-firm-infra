package com.lawfirm.core.storage.strategy;

import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.model.base.storage.enums.StorageTypeEnum;
import com.lawfirm.model.base.storage.model.FileMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractStorageStrategy implements StorageStrategy {

    protected final StorageProperties storageProperties;

    @Override
    public FileMetadata uploadFile(MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            String contentType = file.getContentType();
            long size = file.getSize();
            
            return uploadFile(filename, file.getInputStream(), size, contentType);
        } catch (IOException e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @Override
    public FileMetadata uploadFile(String fileName, InputStream inputStream, long size, String contentType) {
        try {
            String path = doUpload(inputStream, fileName);
            FileMetadata metadata = new FileMetadata();
            metadata.setFilename(fileName);
            metadata.setSize(size);
            metadata.setContentType(contentType);
            metadata.setStorageType(getStorageType().getCode());
            metadata.setPath(path);
            return metadata;
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            doDelete(filePath);
        } catch (Exception e) {
            log.error("文件删除失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件删除失败", e);
        }
    }

    @Override
    public String getFileUrl(String filePath) {
        try {
            return doGetUrl(filePath);
        } catch (Exception e) {
            log.error("获取文件URL失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取文件URL失败", e);
        }
    }

    @Override
    public String getFileUrl(String filePath, long expireSeconds) {
        try {
            return doGetUrl(filePath, expireSeconds);
        } catch (Exception e) {
            log.error("获取文件URL失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取文件URL失败", e);
        }
    }

    @Override
    public byte[] downloadFile(String filePath) {
        try {
            return doDownload(filePath);
        } catch (Exception e) {
            log.error("文件下载失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件下载失败", e);
        }
    }

    @Override
    public List<FileMetadata> listByBusiness(String businessType, String businessId) {
        throw new UnsupportedOperationException("当前存储策略不支持按业务查询文件列表");
    }

    @Override
    public boolean isFileExist(String filePath) {
        try {
            doGetUrl(filePath);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取存储类型
     */
    protected abstract StorageTypeEnum getStorageType();

    /**
     * 执行上传
     */
    protected abstract String doUpload(InputStream inputStream, String fileName) throws Exception;

    /**
     * 执行删除
     */
    protected abstract void doDelete(String filePath) throws Exception;

    /**
     * 获取文件URL
     */
    protected abstract String doGetUrl(String filePath) throws Exception;

    /**
     * 获取带过期时间的文件URL
     */
    protected abstract String doGetUrl(String filePath, long expireSeconds) throws Exception;

    /**
     * 执行下载
     */
    protected abstract byte[] doDownload(String filePath) throws Exception;
}