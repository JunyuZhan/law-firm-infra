package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.cases.dto.business.CaseDocumentDTO;
import com.lawfirm.model.cases.service.business.CaseDocumentService;
import com.lawfirm.model.cases.vo.business.CaseDocumentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 案件文档服务的空实现，用于存储功能被禁用时
 */
@Slf4j
@Service("caseDocumentServiceImpl")
@ConditionalOnProperty(name = "law-firm.storage.enabled", havingValue = "false", matchIfMissing = true)
public class NoOpCaseDocumentServiceImpl implements CaseDocumentService {

    @Override
    public Long uploadDocument(CaseDocumentDTO documentDTO) {
        log.warn("存储功能已禁用，uploadDocument操作被忽略");
        return null;
    }

    @Override
    public boolean batchUploadDocuments(List<CaseDocumentDTO> documentDTOs) {
        log.warn("存储功能已禁用，batchUploadDocuments操作被忽略");
        return false;
    }

    @Override
    public boolean updateDocument(CaseDocumentDTO documentDTO) {
        log.warn("存储功能已禁用，updateDocument操作被忽略");
        return false;
    }

    @Override
    public boolean deleteDocument(Long documentId) {
        log.warn("存储功能已禁用，deleteDocument操作被忽略");
        return false;
    }

    @Override
    public boolean batchDeleteDocuments(List<Long> documentIds) {
        log.warn("存储功能已禁用，batchDeleteDocuments操作被忽略");
        return false;
    }

    @Override
    public CaseDocumentVO getDocumentDetail(Long documentId) {
        log.warn("存储功能已禁用，getDocumentDetail操作被忽略");
        return null;
    }

    @Override
    public List<CaseDocumentVO> listCaseDocuments(Long caseId) {
        log.warn("存储功能已禁用，listCaseDocuments操作被忽略");
        return Collections.emptyList();
    }

    @Override
    public IPage<CaseDocumentVO> pageDocuments(Long caseId, Integer documentType, Integer securityLevel, Integer pageNum, Integer pageSize) {
        log.warn("存储功能已禁用，pageDocuments操作被忽略");
        return new Page<>();
    }

    @Override
    public byte[] downloadDocument(Long documentId) {
        log.warn("存储功能已禁用，downloadDocument操作被忽略");
        return new byte[0];
    }

    @Override
    public String previewDocument(Long documentId) {
        log.warn("存储功能已禁用，previewDocument操作被忽略");
        return null;
    }

    @Override
    public String shareDocument(Long documentId, Integer shareType, Integer expireTime) {
        log.warn("存储功能已禁用，shareDocument操作被忽略");
        return null;
    }

    @Override
    public boolean setSecurityLevel(Long documentId, Integer securityLevel) {
        log.warn("存储功能已禁用，setSecurityLevel操作被忽略");
        return false;
    }

    @Override
    public boolean addDocumentTag(Long documentId, String tag) {
        log.warn("存储功能已禁用，addDocumentTag操作被忽略");
        return false;
    }

    @Override
    public boolean removeDocumentTag(Long documentId, String tag) {
        log.warn("存储功能已禁用，removeDocumentTag操作被忽略");
        return false;
    }

    @Override
    public boolean addDocumentNote(Long documentId, String note) {
        log.warn("存储功能已禁用，addDocumentNote操作被忽略");
        return false;
    }

    @Override
    public boolean moveDocument(Long documentId, Long targetFolderId) {
        log.warn("存储功能已禁用，moveDocument操作被忽略");
        return false;
    }

    @Override
    public boolean copyDocument(Long documentId, Long targetFolderId) {
        log.warn("存储功能已禁用，copyDocument操作被忽略");
        return false;
    }

    @Override
    public Long createFolder(Long caseId, Long parentFolderId, String folderName) {
        log.warn("存储功能已禁用，createFolder操作被忽略");
        return null;
    }

    @Override
    public boolean renameFolder(Long folderId, String newName) {
        log.warn("存储功能已禁用，renameFolder操作被忽略");
        return false;
    }

    @Override
    public boolean deleteFolder(Long folderId) {
        log.warn("存储功能已禁用，deleteFolder操作被忽略");
        return false;
    }

    @Override
    public List<CaseDocumentVO> getFolderTree(Long caseId) {
        log.warn("存储功能已禁用，getFolderTree操作被忽略");
        return new ArrayList<>();
    }

    @Override
    public boolean checkDocumentExists(Long documentId) {
        log.warn("存储功能已禁用，checkDocumentExists操作被忽略");
        return false;
    }

    @Override
    public int countDocuments(Long caseId, Integer documentType, Integer securityLevel) {
        log.warn("存储功能已禁用，countDocuments操作被忽略");
        return 0;
    }
} 