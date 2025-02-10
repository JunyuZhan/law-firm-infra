package com.lawfirm.model.base.storage.service;

import com.lawfirm.model.base.storage.model.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 基础存储服务接口
 */
public interface StorageService {
    
    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件元数据
     */
    FileMetadata uploadFile(MultipartFile file);

    /**
     * 上传文件
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @param size       文件大小
     * @param contentType 内容类型
     * @return 文件元数据
     */
    FileMetadata uploadFile(String fileName, InputStream inputStream, long size, String contentType);

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @return 文件字节数组
     */
    byte[] downloadFile(String filePath);

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @return 文件输入流
     */
    InputStream downloadFileAsStream(String filePath);

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

    /**
     * 根据业务类型和业务ID查询文件列表
     *
     * @param businessType 业务类型
     * @param businessId   业务ID
     * @return 文件元数据列表
     */
    List<FileMetadata> listByBusiness(String businessType, String businessId);

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return 是否存在
     */
    boolean isFileExist(String filePath);
} 