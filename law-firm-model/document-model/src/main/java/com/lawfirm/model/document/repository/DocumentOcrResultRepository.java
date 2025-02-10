package com.lawfirm.model.document.repository;

import com.lawfirm.model.document.entity.DocumentOcrResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * OCR结果仓库接口
 */
@Repository
public interface DocumentOcrResultRepository extends JpaRepository<DocumentOcrResult, Long> {
    
    /**
     * 根据文档ID查询OCR结果（按页码升序）
     */
    List<DocumentOcrResult> findByDocumentIdOrderByPageNoAsc(Long documentId);
    
    /**
     * 根据文档ID删除OCR结果
     */
    void deleteByDocumentId(Long documentId);
    
    /**
     * 根据文档ID和页码查询OCR结果
     */
    DocumentOcrResult findByDocumentIdAndPageNo(Long documentId, Integer pageNo);
} 