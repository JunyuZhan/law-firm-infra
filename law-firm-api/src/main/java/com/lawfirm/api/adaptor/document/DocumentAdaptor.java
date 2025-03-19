package com.lawfirm.api.adaptor.document;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.document.dto.document.DocumentCreateDTO;
import com.lawfirm.model.document.dto.document.DocumentQueryDTO;
import com.lawfirm.model.document.dto.document.DocumentUpdateDTO;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.service.DocumentService;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.model.document.dto.DocumentDTO;
import com.lawfirm.model.document.dto.DocumentUploadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;

/**
 * 文档管理适配器
 */
@Slf4j
@Component
public class DocumentAdaptor extends BaseAdaptor {

    @Autowired
    private DocumentService documentService;

    /**
     * 创建文档
     */
    public Long createDocument(DocumentCreateDTO createDTO, InputStream inputStream) {
        log.info("创建文档: {}", createDTO);
        return documentService.createDocument(createDTO, inputStream);
    }

    /**
     * 更新文档
     */
    public void updateDocument(Long id, DocumentUpdateDTO updateDTO) {
        log.info("更新文档: id={}, {}", id, updateDTO);
        documentService.updateDocument(id, updateDTO);
    }

    /**
     * 更新文档内容
     */
    public void updateDocumentContent(Long id, InputStream inputStream) {
        log.info("更新文档内容: id={}", id);
        documentService.updateDocumentContent(id, inputStream);
    }

    /**
     * 获取文档详情
     */
    public DocumentVO getDocument(Long id) {
        log.info("获取文档详情: id={}", id);
        return documentService.getDocumentById(id);
    }

    /**
     * 删除文档
     */
    public boolean deleteDocument(Long id) {
        log.info("删除文档: id={}", id);
        return documentService.deleteDocument(id);
    }

    /**
     * 批量删除文档
     */
    public void deleteDocuments(List<Long> ids) {
        log.info("批量删除文档: ids={}", ids);
        documentService.deleteDocuments(ids);
    }

    /**
     * 分页查询文档
     */
    public Page<DocumentVO> pageDocuments(Page<BaseDocument> page, DocumentQueryDTO queryDTO) {
        log.info("分页查询文档: page={}, {}", page, queryDTO);
        return documentService.pageDocuments(page, queryDTO);
    }

    /**
     * 根据业务类型和ID获取文档列表
     */
    public List<DocumentVO> listDocumentsByBusiness(Long businessId, String businessType) {
        log.info("获取业务相关文档: businessId={}, businessType={}", businessId, businessType);
        return documentService.listDocumentsByBusiness(businessId, businessType);
    }

    /**
     * 根据文档类型获取文档列表
     */
    public List<DocumentVO> listDocumentsByType(String docType) {
        log.info("根据类型获取文档列表: docType={}", docType);
        return documentService.listDocumentsByType(docType);
    }

    /**
     * 上传文档
     */
    public Long uploadDocument(MultipartFile file, DocumentUploadDTO uploadDTO) {
        log.info("上传文档: {}", uploadDTO);
        return documentService.uploadDocument(file, uploadDTO);
    }

    /**
     * 下载文档
     */
    public InputStream downloadDocument(Long id) {
        log.info("下载文档: id={}", id);
        return documentService.downloadDocument(id);
    }

    /**
     * 预览文档
     */
    public String previewDocument(Long id) {
        log.info("预览文档: id={}", id);
        return documentService.previewDocument(id);
    }

    /**
     * 更新文档状态
     */
    public void updateStatus(Long id, String status) {
        log.info("更新文档状态: id={}, status={}", id, status);
        documentService.updateStatus(id, status);
    }

    /**
     * 获取文档访问URL
     */
    public String getDocumentUrl(Long id) {
        log.info("获取文档访问URL: id={}", id);
        return documentService.getDocumentUrl(id);
    }

    /**
     * 获取文档访问URL（带有效期）
     */
    public String getDocumentUrl(Long id, Long expireTime) {
        log.info("获取文档访问URL(带有效期): id={}, expireTime={}", id, expireTime);
        return documentService.getDocumentUrl(id, expireTime);
    }

    /**
     * 获取业务相关文档列表
     */
    public List<DocumentDTO> getBusinessDocuments(String businessType, Long businessId) {
        log.info("获取业务相关文档列表: businessType={}, businessId={}", businessType, businessId);
        return documentService.getBusinessDocuments(businessType, businessId);
    }

    /**
     * 获取文档详情
     */
    public DocumentDTO getDocumentDetail(Long documentId) {
        log.info("获取文档详情: documentId={}", documentId);
        return documentService.getDocumentDetail(documentId);
    }

    /**
     * 设置文档标签
     */
    public boolean setDocumentTags(Long documentId, List<String> tags) {
        log.info("设置文档标签: documentId={}, tags={}", documentId, tags);
        return documentService.setDocumentTags(documentId, tags);
    }

    /**
     * 刷新文档缓存
     */
    public void refreshCache() {
        log.info("刷新文档缓存");
        documentService.refreshCache();
    }
} 