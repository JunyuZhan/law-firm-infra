package com.lawfirm.core.storage.strategy;

import com.lawfirm.model.base.storage.model.FileMetadata;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;

/**
 * 存储策略接口
 */
public interface StorageStrategy {
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
     * @param objectName 对象名称
     * @return 文件字节数组
     */
    byte[] downloadFile(String objectName);

    /**
     * 下载文件
     *
     * @param objectName 对象名称
     * @return 文件输入流
     */
    InputStream downloadFileAsStream(String objectName);

    /**
     * 删除文件
     *
     * @param objectName 对象名称
     */
    void deleteFile(String objectName);

    /**
     * 获取文件URL
     *
     * @param objectName 对象名称
     * @return 文件URL
     */
    String getFileUrl(String objectName);

    /**
     * 获取带过期时间的文件URL
     *
     * @param objectName 对象名称
     * @param expireSeconds 过期时间（秒）
     * @return 带过期时间的文件URL
     */
    String getFileUrl(String objectName, long expireSeconds);

    /**
     * 判断文件是否存在
     *
     * @param objectName 对象名称
     * @return 是否存在
     */
    boolean isFileExist(String objectName);

    /**
     * 初始化分片上传
     *
     * @param objectName 对象名称
     * @return 上传ID
     */
    String initMultipartUpload(String objectName);

    /**
     * 上传分片
     *
     * @param uploadId 上传ID
     * @param partNumber 分片序号
     * @param part 分片文件
     * @return 分片ETag
     */
    String uploadPart(String uploadId, int partNumber, MultipartFile part);

    /**
     * 完成分片上传
     *
     * @param uploadId 上传ID
     * @param objectName 对象名称
     * @return 文件路径
     */
    String completeMultipartUpload(String uploadId, String objectName);

    /**
     * 终止分片上传
     *
     * @param uploadId 上传ID
     */
    void abortMultipartUpload(String uploadId);
} 