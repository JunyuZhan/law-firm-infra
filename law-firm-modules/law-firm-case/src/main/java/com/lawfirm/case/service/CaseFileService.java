package com.lawfirm.case.service;

import com.lawfirm.model.base.storage.model.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 案件文件服务接口
 */
public interface CaseFileService {
    
    /**
     * 上传案件文件
     *
     * @param file 文件
     * @param caseId 案件ID
     * @param fileType 文件类型
     * @return 文件元数据
     */
    FileMetadata uploadCaseFile(MultipartFile file, String caseId, String fileType);
    
    /**
     * 下载案件文件
     *
     * @param fileId 文件ID
     * @return 文件流
     */
    InputStream downloadCaseFile(String fileId);
    
    /**
     * 删除案件文件
     *
     * @param fileId 文件ID
     */
    void deleteCaseFile(String fileId);
    
    /**
     * 获取案件文件列表
     *
     * @param caseId 案件ID
     * @param fileType 文件类型
     * @return 文件元数据列表
     */
    List<FileMetadata> listCaseFiles(String caseId, String fileType);
} 