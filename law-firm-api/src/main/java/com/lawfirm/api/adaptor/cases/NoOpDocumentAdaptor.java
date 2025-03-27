package com.lawfirm.api.adaptor.cases;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.cases.dto.business.CaseDocumentDTO;
import com.lawfirm.model.cases.vo.business.CaseDocumentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 案件文档适配器的空实现，用于存储功能被禁用时
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "false", matchIfMissing = true)
public class NoOpDocumentAdaptor extends BaseAdaptor {

    /**
     * 上传文档
     */
    public Long uploadDocument(CaseDocumentDTO documentDTO) {
        log.warn("存储功能已禁用，uploadDocument操作被忽略");
        return null;
    }

    /**
     * 批量上传文档
     */
    public boolean batchUploadDocuments(List<CaseDocumentDTO> documentDTOs) {
        log.warn("存储功能已禁用，batchUploadDocuments操作被忽略");
        return false;
    }

    /**
     * 更新文档信息
     */
    public boolean updateDocument(CaseDocumentDTO documentDTO) {
        log.warn("存储功能已禁用，updateDocument操作被忽略");
        return false;
    }

    /**
     * 删除文档
     */
    public boolean deleteDocument(Long documentId) {
        log.warn("存储功能已禁用，deleteDocument操作被忽略");
        return false;
    }

    /**
     * 批量删除文档
     */
    public boolean batchDeleteDocuments(List<Long> documentIds) {
        log.warn("存储功能已禁用，batchDeleteDocuments操作被忽略");
        return false;
    }

    /**
     * 获取文档详情
     */
    public CaseDocumentVO getDocumentDetail(Long documentId) {
        log.warn("存储功能已禁用，getDocumentDetail操作被忽略");
        return null;
    }

    /**
     * 获取案件的所有文档
     */
    public List<CaseDocumentVO> listCaseDocuments(Long caseId) {
        log.warn("存储功能已禁用，listCaseDocuments操作被忽略");
        return Collections.emptyList();
    }

    /**
     * 分页查询文档
     */
    public IPage<CaseDocumentVO> pageDocuments(Long caseId, Integer documentType, Integer securityLevel, Integer pageNum, Integer pageSize) {
        log.warn("存储功能已禁用，pageDocuments操作被忽略");
        return new Page<>();
    }

    /**
     * 下载文档
     */
    public ResponseEntity<byte[]> downloadDocument(Long documentId) {
        log.warn("存储功能已禁用，downloadDocument操作被忽略");
        return ResponseEntity.ok().body(new byte[0]);
    }

    /**
     * 预览文档
     */
    public String previewDocument(Long documentId) {
        log.warn("存储功能已禁用，previewDocument操作被忽略");
        return null;
    }

    /**
     * 分享文档
     */
    public String shareDocument(Long documentId, Integer shareType, Integer expireTime) {
        log.warn("存储功能已禁用，shareDocument操作被忽略");
        return null;
    }

    /**
     * 设置文档安全级别
     */
    public boolean setSecurityLevel(Long documentId, Integer securityLevel) {
        log.warn("存储功能已禁用，setSecurityLevel操作被忽略");
        return false;
    }

    /**
     * 添加文档标签
     */
    public boolean addDocumentTag(Long documentId, String tag) {
        log.warn("存储功能已禁用，addDocumentTag操作被忽略");
        return false;
    }

    /**
     * 移除文档标签
     */
    public boolean removeDocumentTag(Long documentId, String tag) {
        log.warn("存储功能已禁用，removeDocumentTag操作被忽略");
        return false;
    }

    /**
     * 添加文档备注
     */
    public boolean addDocumentNote(Long documentId, String note) {
        log.warn("存储功能已禁用，addDocumentNote操作被忽略");
        return false;
    }

    /**
     * 移动文档
     */
    public boolean moveDocument(Long documentId, Long targetFolderId) {
        log.warn("存储功能已禁用，moveDocument操作被忽略");
        return false;
    }

    /**
     * 复制文档
     */
    public boolean copyDocument(Long documentId, Long targetFolderId) {
        log.warn("存储功能已禁用，copyDocument操作被忽略");
        return false;
    }

    /**
     * 创建文件夹
     */
    public Long createFolder(Long caseId, Long parentFolderId, String folderName) {
        log.warn("存储功能已禁用，createFolder操作被忽略");
        return null;
    }

    /**
     * 重命名文件夹
     */
    public boolean renameFolder(Long folderId, String newName) {
        log.warn("存储功能已禁用，renameFolder操作被忽略");
        return false;
    }

    /**
     * 删除文件夹
     */
    public boolean deleteFolder(Long folderId) {
        log.warn("存储功能已禁用，deleteFolder操作被忽略");
        return false;
    }

    /**
     * 获取文件夹树
     */
    public List<CaseDocumentVO> getFolderTree(Long caseId) {
        log.warn("存储功能已禁用，getFolderTree操作被忽略");
        return new ArrayList<>();
    }

    /**
     * 检查文档是否存在
     */
    public boolean checkDocumentExists(Long documentId) {
        log.warn("存储功能已禁用，checkDocumentExists操作被忽略");
        return false;
    }

    /**
     * 统计文档数量
     */
    public int countDocuments(Long caseId, Integer documentType, Integer securityLevel) {
        log.warn("存储功能已禁用，countDocuments操作被忽略");
        return 0;
    }
} 