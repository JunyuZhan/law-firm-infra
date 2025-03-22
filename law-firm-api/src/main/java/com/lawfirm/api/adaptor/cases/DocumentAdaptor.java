package com.lawfirm.api.adaptor.cases;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.cases.dto.business.CaseDocumentDTO;
import com.lawfirm.model.cases.service.business.CaseDocumentService;
import com.lawfirm.model.cases.vo.business.CaseDocumentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 案件文档适配器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentAdaptor extends BaseAdaptor {

    private final CaseDocumentService documentService;

    /**
     * 上传文档
     */
    public Long uploadDocument(CaseDocumentDTO documentDTO) {
        log.info("上传文档: {}", documentDTO);
        return documentService.uploadDocument(documentDTO);
    }

    /**
     * 批量上传文档
     */
    public boolean batchUploadDocuments(List<CaseDocumentDTO> documentDTOs) {
        log.info("批量上传文档: {}", documentDTOs);
        return documentService.batchUploadDocuments(documentDTOs);
    }

    /**
     * 更新文档信息
     */
    public boolean updateDocument(CaseDocumentDTO documentDTO) {
        log.info("更新文档信息: {}", documentDTO);
        return documentService.updateDocument(documentDTO);
    }

    /**
     * 删除文档
     */
    public boolean deleteDocument(Long documentId) {
        log.info("删除文档: {}", documentId);
        return documentService.deleteDocument(documentId);
    }

    /**
     * 批量删除文档
     */
    public boolean batchDeleteDocuments(List<Long> documentIds) {
        log.info("批量删除文档: {}", documentIds);
        return documentService.batchDeleteDocuments(documentIds);
    }

    /**
     * 获取文档详情
     */
    public CaseDocumentVO getDocumentDetail(Long documentId) {
        log.info("获取文档详情: {}", documentId);
        return documentService.getDocumentDetail(documentId);
    }

    /**
     * 获取案件的所有文档
     */
    public List<CaseDocumentVO> listCaseDocuments(Long caseId) {
        log.info("获取案件的所有文档: caseId={}", caseId);
        return documentService.listCaseDocuments(caseId);
    }

    /**
     * 分页查询文档
     */
    public IPage<CaseDocumentVO> pageDocuments(Long caseId, Integer documentType, Integer securityLevel, Integer pageNum, Integer pageSize) {
        log.info("分页查询文档: caseId={}, documentType={}, securityLevel={}, pageNum={}, pageSize={}", 
                caseId, documentType, securityLevel, pageNum, pageSize);
        return documentService.pageDocuments(caseId, documentType, securityLevel, pageNum, pageSize);
    }

    /**
     * 下载文档
     */
    public ResponseEntity<byte[]> downloadDocument(Long documentId) {
        log.info("下载文档: {}", documentId);
        byte[] fileBytes = documentService.downloadDocument(documentId);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=document-" + documentId)
                .body(fileBytes);
    }

    /**
     * 预览文档
     */
    public String previewDocument(Long documentId) {
        log.info("预览文档: {}", documentId);
        return documentService.previewDocument(documentId);
    }

    /**
     * 分享文档
     */
    public String shareDocument(Long documentId, Integer shareType, Integer expireTime) {
        log.info("分享文档: documentId={}, shareType={}, expireTime={}", documentId, shareType, expireTime);
        return documentService.shareDocument(documentId, shareType, expireTime);
    }

    /**
     * 设置文档安全级别
     */
    public boolean setSecurityLevel(Long documentId, Integer securityLevel) {
        log.info("设置文档安全级别: documentId={}, securityLevel={}", documentId, securityLevel);
        return documentService.setSecurityLevel(documentId, securityLevel);
    }

    /**
     * 添加文档标签
     */
    public boolean addDocumentTag(Long documentId, String tag) {
        log.info("添加文档标签: documentId={}, tag={}", documentId, tag);
        return documentService.addDocumentTag(documentId, tag);
    }

    /**
     * 移除文档标签
     */
    public boolean removeDocumentTag(Long documentId, String tag) {
        log.info("移除文档标签: documentId={}, tag={}", documentId, tag);
        return documentService.removeDocumentTag(documentId, tag);
    }

    /**
     * 添加文档备注
     */
    public boolean addDocumentNote(Long documentId, String note) {
        log.info("添加文档备注: documentId={}, note={}", documentId, note);
        return documentService.addDocumentNote(documentId, note);
    }

    /**
     * 移动文档
     */
    public boolean moveDocument(Long documentId, Long targetFolderId) {
        log.info("移动文档: documentId={}, targetFolderId={}", documentId, targetFolderId);
        return documentService.moveDocument(documentId, targetFolderId);
    }

    /**
     * 复制文档
     */
    public boolean copyDocument(Long documentId, Long targetFolderId) {
        log.info("复制文档: documentId={}, targetFolderId={}", documentId, targetFolderId);
        return documentService.copyDocument(documentId, targetFolderId);
    }

    /**
     * 创建文件夹
     */
    public Long createFolder(Long caseId, Long parentFolderId, String folderName) {
        log.info("创建文件夹: caseId={}, parentFolderId={}, folderName={}", caseId, parentFolderId, folderName);
        return documentService.createFolder(caseId, parentFolderId, folderName);
    }

    /**
     * 重命名文件夹
     */
    public boolean renameFolder(Long folderId, String newName) {
        log.info("重命名文件夹: folderId={}, newName={}", folderId, newName);
        return documentService.renameFolder(folderId, newName);
    }

    /**
     * 删除文件夹
     */
    public boolean deleteFolder(Long folderId) {
        log.info("删除文件夹: {}", folderId);
        return documentService.deleteFolder(folderId);
    }

    /**
     * 获取文件夹树
     */
    public List<CaseDocumentVO> getFolderTree(Long caseId) {
        log.info("获取文件夹树: caseId={}", caseId);
        return documentService.getFolderTree(caseId);
    }

    /**
     * 检查文档是否存在
     */
    public boolean checkDocumentExists(Long documentId) {
        log.info("检查文档是否存在: {}", documentId);
        return documentService.checkDocumentExists(documentId);
    }

    /**
     * 统计案件文档数量
     */
    public int countDocuments(Long caseId, Integer documentType, Integer securityLevel) {
        log.info("统计案件文档数量: caseId={}, documentType={}, securityLevel={}", 
                caseId, documentType, securityLevel);
        return documentService.countDocuments(caseId, documentType, securityLevel);
    }
} 