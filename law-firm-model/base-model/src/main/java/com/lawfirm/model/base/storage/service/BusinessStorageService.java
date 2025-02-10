package com.lawfirm.model.base.storage.service;

import com.lawfirm.model.base.storage.entity.FileMetadataEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 业务存储服务接口
 */
public interface BusinessStorageService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 文件元数据
     */
    FileMetadataEntity upload(MultipartFile file, String businessType, String businessId);

    /**
     * 上传文件
     *
     * @param inputStream 输入流
     * @param filename 文件名
     * @param contentType 文件类型
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 文件元数据
     */
    FileMetadataEntity upload(InputStream inputStream, String filename, String contentType, String businessType, String businessId);

    /**
     * 下载文件
     *
     * @param id 文件ID
     * @return 文件输入流
     */
    InputStream download(String id);

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
     * 获取文件访问URL
     *
     * @param id 文件ID
     * @return 文件URL
     */
    String getUrl(String id);

    /**
     * 获取文件访问URL
     *
     * @param id 文件ID
     * @param expireSeconds URL有效期（秒）
     * @return 文件URL
     */
    String getUrl(String id, long expireSeconds);

    /**
     * 获取文件元数据
     *
     * @param id 文件ID
     * @return 文件元数据
     */
    FileMetadataEntity getMetadata(String id);

    /**
     * 根据业务类型和业务ID查询文件列表
     *
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 文件列表
     */
    List<FileMetadataEntity> listByBusiness(String businessType, String businessId);
}