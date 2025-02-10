package com.lawfirm.model.document.service;

import com.lawfirm.model.document.entity.Document;
import com.lawfirm.model.document.vo.DocumentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 文档服务接口
 */
public interface DocumentService {
    
    /**
     * 创建文档
     */
    Document createDocument(Document document);
    
    /**
     * 更新文档
     */
    Document updateDocument(Document document);
    
    /**
     * 删除文档
     */
    void deleteDocument(Long id);
    
    /**
     * 获取文档详情
     */
    Document getDocument(Long id);
    
    /**
     * 分页查询文档
     */
    Page<Document> listDocuments(Pageable pageable);
    
    /**
     * 根据分类ID查询文档
     */
    List<Document> listByCategory(Long categoryId);
    
    /**
     * 根据案件ID查询文档
     */
    List<Document> listByCase(Long caseId);
    
    /**
     * 根据合同ID查询文档
     */
    List<Document> listByContract(Long contractId);
    
    /**
     * 根据客户ID查询文档
     */
    List<Document> listByClient(Long clientId);
    
    /**
     * 移动文档到指定分类
     */
    void moveToCategory(Long documentId, Long categoryId);
    
    /**
     * 复制文档
     */
    Document copyDocument(Long sourceId, String newName);
    
    /**
     * 归档文档
     */
    void archiveDocument(Long id);
    
    /**
     * 取消归档
     */
    void unarchiveDocument(Long id);
    
    /**
     * 检查文档权限
     */
    boolean hasPermission(Long documentId, Long userId, String permission);
    
    /**
     * 获取文档视图对象
     */
    DocumentVO getDocumentVO(Long id);
    
    /**
     * 分页查询文档视图对象
     */
    Page<DocumentVO> listDocumentVOs(Pageable pageable);
} 