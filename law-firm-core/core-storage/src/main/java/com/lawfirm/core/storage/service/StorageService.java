package com.lawfirm.core.storage.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 存储服务接口
 */
public interface StorageService {
    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件路径
     */
    String uploadFile(MultipartFile file);

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @return 文件字节数组
     */
    byte[] downloadFile(String filePath);

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    void deleteFile(String filePath);

    /**
     * 获取文件URL
     *
     * @param filePath 文件路径
     * @return 文件URL
     */
    String getFileUrl(String filePath);
} 