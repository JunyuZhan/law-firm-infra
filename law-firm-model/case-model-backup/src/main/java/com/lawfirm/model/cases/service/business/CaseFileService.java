package com.lawfirm.model.cases.service.business;

import com.lawfirm.model.base.dto.PageDTO;
import com.lawfirm.model.cases.entity.business.CaseFile;
import com.lawfirm.model.cases.enums.doc.DocumentSecurityLevelEnum;
import com.lawfirm.model.cases.enums.doc.DocumentTypeEnum;
import com.lawfirm.model.cases.vo.business.CaseDocumentVO;

import java.util.List;

/**
 * 案件文件服务接口
 */
public interface CaseFileService {

    /**
     * 上传文件
     *
     * @param caseId 案件ID
     * @param fileType 文件类型
     * @param securityLevel 安全级别
     * @param files 文件列表
     * @return 文件ID列表
     */
    List<Long> uploadFiles(Long caseId, DocumentTypeEnum fileType, 
            DocumentSecurityLevelEnum securityLevel, List<CaseFile> files);

    /**
     * 更新文件信息
     *
     * @param fileId 文件ID
     * @param fileName 文件名
     * @param description 文件描述
     * @param securityLevel 安全级别
     * @return 是否成功
     */
    Boolean updateFileInfo(Long fileId, String fileName, String description, DocumentSecurityLevelEnum securityLevel);

    /**
     * 删除文件
     *
     * @param fileId 文件ID
     * @return 是否成功
     */
    Boolean deleteFile(Long fileId);

    /**
     * 批量删除文件
     *
     * @param fileIds 文件ID列表
     * @return 是否成功
     */
    Boolean batchDeleteFiles(List<Long> fileIds);

    /**
     * 获取文件详情
     *
     * @param fileId 文件ID
     * @return 文件详情
     */
    CaseDocumentVO getFileDetail(Long fileId);

    /**
     * 获取案件的所有文件
     *
     * @param caseId 案件ID
     * @return 文件列表
     */
    List<CaseDocumentVO> listCaseFiles(Long caseId);

    /**
     * 分页查询文件
     *
     * @param caseId 案件ID
     * @param fileType 文件类型
     * @param securityLevel 安全级别
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageDTO<CaseDocumentVO> pageFiles(Long caseId, DocumentTypeEnum fileType,
            DocumentSecurityLevelEnum securityLevel, Integer pageNum, Integer pageSize);

    /**
     * 下载文件
     *
     * @param fileId 文件ID
     * @return 文件字节数组
     */
    byte[] downloadFile(Long fileId);

    /**
     * 批量下载文件
     *
     * @param fileIds 文件ID列表
     * @return 压缩包字节数组
     */
    byte[] batchDownloadFiles(List<Long> fileIds);

    /**
     * 检查文件访问权限
     *
     * @param fileId 文件ID
     * @param userId 用户ID
     * @return 是否有权限
     */
    Boolean checkFilePermission(Long fileId, Long userId);

    /**
     * 更新文件版本
     *
     * @param fileId 文件ID
     * @param newFile 新文件
     * @return 是否成功
     */
    Boolean updateFileVersion(Long fileId, CaseFile newFile);

    /**
     * 获取文件版本历史
     *
     * @param fileId 文件ID
     * @return 版本历史列表
     */
    List<CaseDocumentVO> getFileVersionHistory(Long fileId);

    /**
     * 恢复文件历史版本
     *
     * @param fileId 文件ID
     * @param versionId 版本ID
     * @return 是否成功
     */
    Boolean restoreFileVersion(Long fileId, Long versionId);

    /**
     * 文件加密
     *
     * @param fileId 文件ID
     * @param password 密码
     * @return 是否成功
     */
    Boolean encryptFile(Long fileId, String password);

    /**
     * 文件解密
     *
     * @param fileId 文件ID
     * @param password 密码
     * @return 是否成功
     */
    Boolean decryptFile(Long fileId, String password);
} 