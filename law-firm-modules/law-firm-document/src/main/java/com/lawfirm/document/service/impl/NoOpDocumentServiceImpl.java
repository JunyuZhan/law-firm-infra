package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.document.dto.DocumentDTO;
import com.lawfirm.model.document.dto.DocumentUploadDTO;
import com.lawfirm.model.document.dto.document.DocumentCreateDTO;
import com.lawfirm.model.document.dto.document.DocumentQueryDTO;
import com.lawfirm.model.document.dto.document.DocumentUpdateDTO;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.mapper.DocumentMapper;
import com.lawfirm.model.document.service.DocumentService;
import com.lawfirm.model.document.vo.DocumentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文档服务的空实现，用于存储功能被禁用时
 */
@Slf4j
@Service("documentServiceImpl")
@ConditionalOnProperty(name = "law-firm.storage.enabled", havingValue = "false", matchIfMissing = true)
public class NoOpDocumentServiceImpl extends BaseServiceImpl<DocumentMapper, BaseDocument> implements DocumentService {

    @Override
    public Long createDocument(DocumentCreateDTO createDTO, InputStream inputStream) {
        log.warn("存储功能已禁用，createDocument操作被忽略");
        return null;
    }

    @Override
    public void updateDocument(Long id, DocumentUpdateDTO updateDTO) {
        log.warn("存储功能已禁用，updateDocument操作被忽略");
    }

    @Override
    public void updateDocumentContent(Long id, InputStream inputStream) {
        log.warn("存储功能已禁用，updateDocumentContent操作被忽略");
    }

    @Override
    public boolean deleteDocument(Long id) {
        log.warn("存储功能已禁用，deleteDocument操作被忽略");
        return false;
    }

    @Override
    public void deleteDocuments(List<Long> ids) {
        log.warn("存储功能已禁用，deleteDocuments操作被忽略");
    }

    @Override
    public DocumentVO getDocumentById(Long id) {
        log.warn("存储功能已禁用，getDocumentById操作被忽略");
        return null;
    }

    @Override
    public Page<DocumentVO> pageDocuments(Page<BaseDocument> page, DocumentQueryDTO queryDTO) {
        log.warn("存储功能已禁用，pageDocuments操作被忽略");
        return new Page<>();
    }

    @Override
    public InputStream downloadDocument(Long id) {
        log.warn("存储功能已禁用，downloadDocument操作被忽略");
        return null;
    }

    @Override
    public String previewDocument(Long id) {
        log.warn("存储功能已禁用，previewDocument操作被忽略");
        return null;
    }

    @Override
    public String getDocumentUrl(Long id) {
        log.warn("存储功能已禁用，getDocumentUrl操作被忽略");
        return null;
    }

    @Override
    public String getDocumentUrl(Long id, Long expireTime) {
        log.warn("存储功能已禁用，getDocumentUrl操作被忽略");
        return null;
    }

    @Override
    public void updateStatus(Long id, String status) {
        log.warn("存储功能已禁用，updateStatus操作被忽略");
    }

    @Override
    public List<DocumentVO> listDocumentsByBusiness(Long businessId, String businessType) {
        log.warn("存储功能已禁用，listDocumentsByBusiness操作被忽略");
        return new ArrayList<>();
    }

    @Override
    public List<DocumentVO> listDocumentsByType(String docType) {
        log.warn("存储功能已禁用，listDocumentsByType操作被忽略");
        return new ArrayList<>();
    }

    @Override
    public void refreshCache() {
        log.warn("存储功能已禁用，refreshCache操作被忽略");
    }

    @Override
    public Long uploadDocument(MultipartFile file, DocumentUploadDTO uploadDTO) {
        log.warn("存储功能已禁用，uploadDocument操作被忽略");
        return null;
    }

    @Override
    public List<DocumentDTO> getBusinessDocuments(String businessType, Long businessId) {
        log.warn("存储功能已禁用，getBusinessDocuments操作被忽略");
        return new ArrayList<>();
    }

    @Override
    public DocumentDTO getDocumentDetail(Long documentId) {
        log.warn("存储功能已禁用，getDocumentDetail操作被忽略");
        return null;
    }

    @Override
    public boolean setDocumentTags(Long documentId, List<String> tags) {
        log.warn("存储功能已禁用，setDocumentTags操作被忽略");
        return false;
    }
} 