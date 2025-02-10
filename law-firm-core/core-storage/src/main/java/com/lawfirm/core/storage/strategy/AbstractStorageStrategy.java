package com.lawfirm.core.storage.strategy;

import com.lawfirm.model.base.storage.model.FileMetadata;
import com.lawfirm.core.storage.config.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 存储策略抽象类
 */
public abstract class AbstractStorageStrategy implements StorageStrategy {
    
    @Autowired
    protected StorageProperties storageProperties;
    
    /**
     * 上传文件
     *
     * @param file 文件
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 文件元数据
     */
    public FileMetadata upload(MultipartFile file, String businessType, String businessId) {
        try {
            return upload(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), businessType, businessId);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }
    
    /**
     * 上传文件流
     *
     * @param inputStream 输入流
     * @param filename 文件名
     * @param contentType 文件类型
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 文件元数据
     */
    public FileMetadata upload(InputStream inputStream, String filename, String contentType, String businessType, String businessId) {
        // 生成文件ID
        String id = generateFileId();
        // 生成存储路径
        String path = generatePath(filename);
        
        // 执行上传
        doUpload(inputStream, path);
        
        // 构建并返回文件元数据
        return new FileMetadata()
                .setId(id)
                .setFilename(filename)
                .setContentType(contentType)
                .setStorageType(getStorageType())
                .setPath(path)
                .setBusinessType(businessType)
                .setBusinessId(businessId)
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
    }
    
    /**
     * 生成文件ID
     *
     * @return 文件ID
     */
    protected String generateFileId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 生成存储路径
     *
     * @param filename 文件名
     * @return 存储路径
     */
    protected String generatePath(String filename) {
        return String.format("%s/%s/%s", 
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue(),
                generateFileId() + "_" + filename);
    }
    
    /**
     * 获取存储类型
     *
     * @return 存储类型
     */
    protected abstract String getStorageType();
    
    /**
     * 执行上传
     *
     * @param inputStream 输入流
     * @param path 存储路径
     */
    protected abstract void doUpload(InputStream inputStream, String path);
    
    /**
     * 执行删除
     *
     * @param path 存储路径
     */
    public abstract void doDelete(String path);
    
    /**
     * 获取文件访问URL
     *
     * @param path 存储路径
     * @return 访问URL
     */
    public abstract String doGetUrl(String path);
    
    /**
     * 获取文件访问URL（带过期时间）
     *
     * @param path 存储路径
     * @param expireSeconds 过期时间（秒）
     * @return 访问URL
     */
    public abstract String doGetUrl(String path, long expireSeconds);
    
    /**
     * 下载文件
     *
     * @param path 存储路径
     * @return 文件流
     */
    public abstract InputStream doDownload(String path);

    @Override
    public FileMetadata uploadFile(MultipartFile file) {
        try {
            String objectName = generateObjectName(file.getOriginalFilename());
            doUpload(file.getInputStream(), objectName);
            
            FileMetadata metadata = new FileMetadata();
            metadata.setFilename(objectName);
            metadata.setOriginalFilename(file.getOriginalFilename());
            metadata.setSize(file.getSize());
            metadata.setContentType(file.getContentType());
            metadata.setStorageType(getStorageType());
            metadata.setPath(objectName);
            metadata.setCreateTime(LocalDateTime.now());
            metadata.setUpdateTime(LocalDateTime.now());
            
            return metadata;
        } catch (IOException e) {
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public FileMetadata uploadFile(String filename, InputStream inputStream, long size, String contentType) {
        String objectName = generateObjectName(filename);
        doUpload(inputStream, objectName);
        
        FileMetadata metadata = new FileMetadata();
        metadata.setFilename(objectName);
        metadata.setOriginalFilename(filename);
        metadata.setSize(size);
        metadata.setContentType(contentType);
        metadata.setStorageType(getStorageType());
        metadata.setPath(objectName);
        metadata.setCreateTime(LocalDateTime.now());
        metadata.setUpdateTime(LocalDateTime.now());
        
        return metadata;
    }

    @Override
    public void deleteFile(String path) {
        doDelete(path);
    }

    @Override
    public String getFileUrl(String path) {
        return doGetUrl(path);
    }

    @Override
    public String getFileUrl(String path, long expireSeconds) {
        return doGetUrl(path, expireSeconds);
    }

    @Override
    public InputStream downloadFile(String path) {
        return doDownload(path);
    }

    /**
     * 生成对象名称
     */
    protected String generateObjectName(String filename) {
        return UUID.randomUUID().toString().replace("-", "") + getFileExtension(filename);
    }

    /**
     * 获取文件扩展名
     */
    protected String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    /**
     * 获取存储类型
     */
    protected abstract String getStorageType();

    /**
     * 执行上传
     */
    protected abstract void doUpload(InputStream inputStream, String path);

    /**
     * 执行删除
     */
    protected abstract void doDelete(String path);

    /**
     * 获取文件访问URL
     */
    protected abstract String doGetUrl(String path);

    /**
     * 获取文件访问URL（带过期时间）
     */
    protected abstract String doGetUrl(String path, long expireSeconds);

    /**
     * 下载文件
     */
    protected abstract InputStream doDownload(String path);
} 