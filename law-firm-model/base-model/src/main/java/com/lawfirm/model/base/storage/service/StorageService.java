package com.lawfirm.model.base.storage.service;

import com.lawfirm.model.base.storage.model.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 统一存储服务接口
 */
public interface StorageService {
    
    /**
     * 上传文件
     *
     * @param file 文件
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 文件元数据
     */
    FileMetadata upload(MultipartFile file, String businessType, String businessId);
    
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
    FileMetadata upload(InputStream inputStream, String filename, String contentType, String businessType, String businessId);
    
    /**
     * 删除文件
     *
     * @param id 文件ID
     */
    void delete(String id);
    
    /**
     * 批量删除文件
     *
     * @param ids 文件ID列表
     */
    void deleteBatch(List<String> ids);
    
    /**
     * 获取文件元数据
     *
     * @param id 文件ID
     * @return 文件元数据
     */
    FileMetadata getMetadata(String id);
    
    /**
     * 获取文件访问URL
     *
     * @param id 文件ID
     * @return 访问URL
     */
    String getUrl(String id);
    
    /**
     * 获取文件访问URL（带过期时间）
     *
     * @param id 文件ID
     * @param expireSeconds 过期时间（秒）
     * @return 访问URL
     */
    String getUrl(String id, long expireSeconds);
    
    /**
     * 下载文件
     *
     * @param id 文件ID
     * @return 文件流
     */
    InputStream download(String id);
    
    /**
     * 根据业务类型和业务ID获取文件列表
     *
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 文件元数据列表
     */
    List<FileMetadata> listByBusiness(String businessType, String businessId);
} 