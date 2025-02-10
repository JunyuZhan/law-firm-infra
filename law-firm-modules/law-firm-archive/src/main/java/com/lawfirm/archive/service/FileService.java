package com.lawfirm.archive.service;

import com.lawfirm.model.base.storage.model.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 档案文件服务接口
 */
public interface FileService {

    /**
     * 上传档案文件
     *
     * @param file 文件
     * @param archiveId 档案ID
     * @return 文件元数据
     */
    FileMetadata uploadFile(MultipartFile file, String archiveId);

    /**
     * 下载档案文件
     *
     * @param fileId 文件ID
     * @return 文件流
     */
    InputStream downloadFile(String fileId);

    /**
     * 删除档案文件
     *
     * @param fileId 文件ID
     */
    void deleteFile(String fileId);

    /**
     * 获取档案文件列表
     *
     * @param archiveId 档案ID
     * @return 文件元数据列表
     */
    List<FileMetadata> listFiles(String archiveId);
} 