package com.lawfirm.cases.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.cases.model.entity.CaseFile;
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
     * 获取案件文件
     */
    CaseFile getFile(Long fileId);

    /**
     * 获取案件文件列表
     */
    List<CaseFile> getCaseFiles(Long caseId);

    /**
     * 获取案件机密文件列表
     */
    List<CaseFile> getConfidentialFiles(Long caseId);

    /**
     * 删除案件文件
     */
    void deleteFile(Long fileId);

    /**
     * 下载案件文件
     */
    byte[] downloadFile(Long fileId);
} 