package com.lawfirm.model.document.repository;

import com.lawfirm.model.document.entity.DocumentPreview;
import com.lawfirm.model.document.enums.DocumentPreviewStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 文档预览Repository接口
 */
@Repository
public interface DocumentPreviewRepository extends JpaRepository<DocumentPreview, Long> {
    
    /**
     * 查询文档的预览记录
     */
    Optional<DocumentPreview> findByDocumentId(Long documentId);
    
    /**
     * 分页查询预览记录
     */
    Page<DocumentPreview> findByDocumentIdOrderByCreateTimeDesc(Long documentId, Pageable pageable);
    
    /**
     * 查询指定状态的预览记录
     */
    List<DocumentPreview> findByPreviewStatus(DocumentPreviewStatusEnum status);
    
    /**
     * 查询需要清理的预览记录
     */
    @Query("SELECT p FROM DocumentPreview p WHERE p.previewStatus = 'GENERATED' " +
            "AND p.previewGenerateTime < :beforeTime")
    List<DocumentPreview> findPreviewsToClean(LocalDateTime beforeTime);
    
    /**
     * 查询生成失败的预览记录
     */
    List<DocumentPreview> findByPreviewStatusAndCreateTimeBefore(
            DocumentPreviewStatusEnum status, LocalDateTime beforeTime);
    
    /**
     * 查询正在生成的预览记录
     */
    List<DocumentPreview> findByPreviewStatusAndCreateTimeBeforeAndRetryCountLessThan(
            DocumentPreviewStatusEnum status, LocalDateTime beforeTime, Integer maxRetries);
    
    /**
     * 删除文档的预览记录
     */
    void deleteByDocumentId(Long documentId);
    
    /**
     * 更新预览状态
     */
    @Query("UPDATE DocumentPreview p SET p.previewStatus = :status, " +
            "p.previewGenerateTime = CURRENT_TIMESTAMP WHERE p.documentId = :documentId")
    void updatePreviewStatus(Long documentId, DocumentPreviewStatusEnum status);
    
    /**
     * 更新重试次数
     */
    @Query("UPDATE DocumentPreview p SET p.retryCount = p.retryCount + 1 " +
            "WHERE p.documentId = :documentId")
    void incrementRetryCount(Long documentId);
} 