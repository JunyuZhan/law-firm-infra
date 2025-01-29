package com.lawfirm.cases.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.cases.entity.CaseFile;
import org.springframework.web.multipart.MultipartFile;

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
} 