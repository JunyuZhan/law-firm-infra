package com.lawfirm.document.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

/**
 * 文档存储服务接口
 * 负责文件的物理存储和管理，与具体的存储实现解耦
 */
public interface DocumentStorageService {
    
    /**
     * 保存文件
     *
     * @param file 文件
     * @param documentNumber 文档编号
     * @param version 版本号
     * @return 文件存储路径
     * @throws IOException IO异常
     */
    String store(MultipartFile file, String documentNumber, String version) throws IOException;
    
    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @throws IOException IO异常
     */
    void delete(String filePath) throws IOException;
    
    /**
     * 删除文档的所有版本
     *
     * @param documentNumber 文档编号
     * @throws IOException IO异常
     */
    void deleteAllVersions(String documentNumber) throws IOException;
    
    /**
     * 获取文件路径
     *
     * @param documentNumber 文档编号
     * @param version 版本号
     * @return 文件路径
     */
    Path getFilePath(String documentNumber, String version);
    
    /**
     * 获取文件内容
     *
     * @param filePath 文件路径
     * @return 文件字节数组
     * @throws IOException IO异常
     */
    byte[] getContent(String filePath) throws IOException;
    
    /**
     * 检查文件是否存在
     *
     * @param filePath 文件路径
     * @return 是否存在
     */
    boolean exists(String filePath);
    
    /**
     * 获取文件大小
     *
     * @param filePath 文件路径
     * @return 文件大小（字节）
     * @throws IOException IO异常
     */
    long getSize(String filePath) throws IOException;
    
    /**
     * 计算文件Hash值
     *
     * @param filePath 文件路径
     * @return 文件Hash值
     * @throws IOException IO异常
     */
    String calculateHash(String filePath) throws IOException;
} 