package com.lawfirm.common.data.storage;

import java.io.InputStream;

/**
 * 存储服务接口
 */
public interface StorageService {
    /**
     * 上传文件
     */
    String uploadFile(String bucketName, String objectName, InputStream stream, long size, String contentType);

    /**
     * 下载文件
     */
    InputStream downloadFile(String bucketName, String objectName);

    /**
     * 删除文件
     */
    void deleteFile(String bucketName, String objectName);

    /**
     * 获取文件URL
     */
    String getFileUrl(String bucketName, String objectName, Integer expiry);

    /**
     * 判断文件是否存在
     */
    boolean isFileExist(String bucketName, String objectName);
} 