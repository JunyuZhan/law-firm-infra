package com.lawfirm.api.adaptor.document;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.document.dto.document.DocumentCreateDTO;
import com.lawfirm.model.document.dto.document.DocumentUpdateDTO;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.service.DocumentService;
import com.lawfirm.model.document.vo.document.DocumentVO;
import com.lawfirm.model.document.enums.DocumentTypeEnum;
import com.lawfirm.model.document.enums.DocumentStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档管理适配器
 */
@Component
public class DocumentAdaptor extends BaseAdaptor {

    @Autowired
    private DocumentService documentService;

    /**
     * 创建文档
     */
    public DocumentVO createDocument(DocumentCreateDTO dto) {
        BaseDocument document = documentService.createDocument(dto);
        return convert(document, DocumentVO.class);
    }

    /**
     * 更新文档
     */
    public DocumentVO updateDocument(Long id, DocumentUpdateDTO dto) {
        BaseDocument document = documentService.updateDocument(id, dto);
        return convert(document, DocumentVO.class);
    }

    /**
     * 获取文档详情
     */
    public DocumentVO getDocument(Long id) {
        BaseDocument document = documentService.getDocument(id);
        return convert(document, DocumentVO.class);
    }

    /**
     * 删除文档
     */
    public void deleteDocument(Long id) {
        documentService.deleteDocument(id);
    }

    /**
     * 获取所有文档
     */
    public List<DocumentVO> listDocuments() {
        List<BaseDocument> documents = documentService.listDocuments();
        return documents.stream()
                .map(document -> convert(document, DocumentVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据文档类型查询文档
     */
    public List<DocumentVO> getDocumentsByType(DocumentTypeEnum type) {
        List<BaseDocument> documents = documentService.getDocumentsByType(type);
        return documents.stream()
                .map(document -> convert(document, DocumentVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据文档状态查询文档
     */
    public List<DocumentVO> getDocumentsByStatus(DocumentStatusEnum status) {
        List<BaseDocument> documents = documentService.getDocumentsByStatus(status);
        return documents.stream()
                .map(document -> convert(document, DocumentVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询文档
     */
    public List<DocumentVO> getDocumentsByDepartmentId(Long departmentId) {
        List<BaseDocument> documents = documentService.getDocumentsByDepartmentId(departmentId);
        return documents.stream()
                .map(document -> convert(document, DocumentVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据创建者ID查询文档
     */
    public List<DocumentVO> getDocumentsByCreatorId(Long creatorId) {
        List<BaseDocument> documents = documentService.getDocumentsByCreatorId(creatorId);
        return documents.stream()
                .map(document -> convert(document, DocumentVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 上传文档
     */
    public DocumentVO uploadDocument(MultipartFile file, DocumentCreateDTO dto) {
        BaseDocument document = documentService.uploadDocument(file, dto);
        return convert(document, DocumentVO.class);
    }

    /**
     * 下载文档
     */
    public byte[] downloadDocument(Long id) {
        return documentService.downloadDocument(id);
    }

    /**
     * 预览文档
     */
    public String previewDocument(Long id) {
        return documentService.previewDocument(id);
    }

    /**
     * 更新文档状态
     */
    public DocumentVO updateDocumentStatus(Long id, DocumentStatusEnum status) {
        BaseDocument document = documentService.updateDocumentStatus(id, status);
        return convert(document, DocumentVO.class);
    }

    /**
     * 检查文档是否存在
     */
    public boolean existsDocument(Long id) {
        return documentService.existsDocument(id);
    }

    /**
     * 获取文档数量
     */
    public long countDocuments() {
        return documentService.countDocuments();
    }
} 