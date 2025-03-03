package com.lawfirm.model.cases.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseDocumentDTO;
import com.lawfirm.model.cases.vo.business.CaseDocumentVO;

import java.util.List;

/**
 * 案件文档服务接口
 */
public interface CaseDocumentService {

    /**
     * 上传文档
     *
     * @param documentDTO 文档信息
     * @return 文档ID
     */
    Long uploadDocument(CaseDocumentDTO documentDTO);

    /**
     * 批量上传文档
     *
     * @param documentDTOs 文档信息列表
     * @return 是否成功
     */
    boolean batchUploadDocuments(List<CaseDocumentDTO> documentDTOs);

    /**
     * 更新文档信息
     *
     * @param documentDTO 文档信息
     * @return 是否成功
     */
    boolean updateDocument(CaseDocumentDTO documentDTO);

    /**
     * 删除文档
     *
     * @param documentId 文档ID
     * @return 是否成功
     */
    boolean deleteDocument(Long documentId);

    /**
     * 批量删除文档
     *
     * @param documentIds 文档ID列表
     * @return 是否成功
     */
    boolean batchDeleteDocuments(List<Long> documentIds);

    /**
     * 获取文档详情
     *
     * @param documentId 文档ID
     * @return 文档详情
     */
    CaseDocumentVO getDocumentDetail(Long documentId);

    /**
     * 获取案件的所有文档
     *
     * @param caseId 案件ID
     * @return 文档列表
     */
    List<CaseDocumentVO> listCaseDocuments(Long caseId);

    /**
     * 分页查询文档
     *
     * @param caseId 案件ID
     * @param documentType 文档类型
     * @param securityLevel 安全级别
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    IPage<CaseDocumentVO> pageDocuments(Long caseId, Integer documentType, Integer securityLevel, Integer pageNum, Integer pageSize);

    /**
     * 下载文档
     *
     * @param documentId 文档ID
     * @return 文档字节数组
     */
    byte[] downloadDocument(Long documentId);

    /**
     * 预览文档
     *
     * @param documentId 文档ID
     * @return 预览URL
     */
    String previewDocument(Long documentId);

    /**
     * 分享文档
     *
     * @param documentId 文档ID
     * @param shareType 分享类型
     * @param expireTime 过期时间（分钟）
     * @return 分享链接
     */
    String shareDocument(Long documentId, Integer shareType, Integer expireTime);

    /**
     * 设置文档安全级别
     *
     * @param documentId 文档ID
     * @param securityLevel 安全级别
     * @return 是否成功
     */
    boolean setSecurityLevel(Long documentId, Integer securityLevel);

    /**
     * 添加文档标签
     *
     * @param documentId 文档ID
     * @param tag 标签
     * @return 是否成功
     */
    boolean addDocumentTag(Long documentId, String tag);

    /**
     * 移除文档标签
     *
     * @param documentId 文档ID
     * @param tag 标签
     * @return 是否成功
     */
    boolean removeDocumentTag(Long documentId, String tag);

    /**
     * 添加文档备注
     *
     * @param documentId 文档ID
     * @param note 备注
     * @return 是否成功
     */
    boolean addDocumentNote(Long documentId, String note);

    /**
     * 移动文档
     *
     * @param documentId 文档ID
     * @param targetFolderId 目标文件夹ID
     * @return 是否成功
     */
    boolean moveDocument(Long documentId, Long targetFolderId);

    /**
     * 复制文档
     *
     * @param documentId 文档ID
     * @param targetFolderId 目标文件夹ID
     * @return 是否成功
     */
    boolean copyDocument(Long documentId, Long targetFolderId);

    /**
     * 创建文件夹
     *
     * @param caseId 案件ID
     * @param parentFolderId 父文件夹ID
     * @param folderName 文件夹名称
     * @return 文件夹ID
     */
    Long createFolder(Long caseId, Long parentFolderId, String folderName);

    /**
     * 重命名文件夹
     *
     * @param folderId 文件夹ID
     * @param newName 新名称
     * @return 是否成功
     */
    boolean renameFolder(Long folderId, String newName);

    /**
     * 删除文件夹
     *
     * @param folderId 文件夹ID
     * @return 是否成功
     */
    boolean deleteFolder(Long folderId);

    /**
     * 获取文件夹树
     *
     * @param caseId 案件ID
     * @return 文件夹树
     */
    List<CaseDocumentVO> getFolderTree(Long caseId);

    /**
     * 检查文档是否存在
     *
     * @param documentId 文档ID
     * @return 是否存在
     */
    boolean checkDocumentExists(Long documentId);

    /**
     * 统计案件文档数量
     *
     * @param caseId 案件ID
     * @param documentType 文档类型
     * @param securityLevel 安全级别
     * @return 数量
     */
    int countDocuments(Long caseId, Integer documentType, Integer securityLevel);
} 