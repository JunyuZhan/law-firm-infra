package com.lawfirm.document.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;

/**
 * 文档存储服务接口
 */
public interface IDocumentStorageService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 存储路径
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file, String path);

    /**
     * 下载文件
     *
     * @param path 存储路径
     * @return 文件输入流
     */
    InputStream downloadFile(String path);

    /**
     * 删除文件
     *
     * @param path 存储路径
     */
    void deleteFile(String path);

    /**
     * 转换文件格式
     *
     * @param sourcePath 源文件路径
     * @param targetFormat 目标格式
     * @return 转换后的文件路径
     */
    String convertFormat(String sourcePath, String targetFormat);
} 