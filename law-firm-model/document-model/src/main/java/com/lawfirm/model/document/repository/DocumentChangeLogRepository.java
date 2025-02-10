package com.lawfirm.model.document.repository;

import com.lawfirm.model.document.entity.DocumentChangeLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文档变更记录Repository接口
 */
@Repository
public interface DocumentChangeLogRepository extends JpaRepository<DocumentChangeLog, Long> {
    
    /**
     * 查询文档的变更记录
     */
    List<DocumentChangeLog> findByDocumentIdOrderByChangeTimeDesc(Long documentId);
    
    /**
     * 分页查询变更记录
     */
    Page<DocumentChangeLog> findByDocumentIdOrderByChangeTimeDesc(Long documentId, Pageable pageable);
    
    /**
     * 查询指定版本的变更记录
     */
    DocumentChangeLog findByDocumentIdAndVersion(Long documentId, Integer version);
    
    /**
     * 查询指定时间范围内的变更记录
     */
    List<DocumentChangeLog> findByDocumentIdAndChangeTimeBetweenOrderByChangeTimeDesc(
            Long documentId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 查询指定操作人的变更记录
     */
    List<DocumentChangeLog> findByOperatorOrderByChangeTimeDesc(String operator);
    
    /**
     * 查询最近的变更记录
     */
    @Query("SELECT c FROM DocumentChangeLog c WHERE c.documentId = :documentId " +
            "ORDER BY c.changeTime DESC LIMIT 1")
    DocumentChangeLog findLatestChange(Long documentId);
    
    /**
     * 统计文档的变更次数
     */
    long countByDocumentId(Long documentId);
    
    /**
     * 删除文档的变更记录
     */
    void deleteByDocumentId(Long documentId);
} 