package com.lawfirm.cases.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.cases.entity.CaseFile;
import com.lawfirm.model.base.storage.model.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 案件文件服务接口
 */
public interface CaseFileService extends IService<CaseFile> {

    /**
     * 上传案件文件
     */
    CaseFile uploadFile(Long caseId, MultipartFile file, String description, Boolean isConfidential);

    /**
     * 获取文件信息
     */
    CaseFile getFile(Long fileId);

    /**
     * 获取案件的所有文件
     */
    List<CaseFile> getCaseFiles(Long caseId);

    /**
     * 获取案件的机密文件
     */
    List<CaseFile> getConfidentialFiles(Long caseId);

    /**
     * 删除文件
     */
    void deleteFile(Long fileId);

    /**
     * 下载文件
     */
    byte[] downloadFile(Long fileId);

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