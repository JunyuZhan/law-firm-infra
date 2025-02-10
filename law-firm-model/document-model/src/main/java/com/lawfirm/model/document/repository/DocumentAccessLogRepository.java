package com.lawfirm.model.document.repository;

import com.lawfirm.model.document.entity.DocumentAccessLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocumentAccessLogRepository extends JpaRepository<DocumentAccessLog, Long> {
    
    List<DocumentAccessLog> findByDocumentIdOrderByAccessTimeDesc(Long documentId);
    
    Page<DocumentAccessLog> findByDocumentIdOrderByAccessTimeDesc(Long documentId, Pageable pageable);
    
    List<DocumentAccessLog> findByDocumentIdAndAccessTimeBetweenOrderByAccessTimeDesc(
            Long documentId, LocalDateTime startTime, LocalDateTime endTime);
            
    List<DocumentAccessLog> findByAccessUserOrderByAccessTimeDesc(String accessUser);
    
    List<DocumentAccessLog> findByIsSuccessFalseOrderByAccessTimeDesc();
    
    long countByDocumentId(Long documentId);
    
    List<DocumentAccessLog> findByDocumentId(Long documentId);
    
    @Modifying
    @Query("DELETE FROM DocumentAccessLog log WHERE log.accessTime < ?1")
    int deleteByAccessTimeBefore(LocalDateTime cutoffDate);

    /**
     * 统计访问用户数
     */
    @Query("SELECT COUNT(DISTINCT a.accessUser) FROM DocumentAccessLog a WHERE a.documentId = :documentId")
    long countDistinctAccessUserByDocumentId(Long documentId);
    
    /**
     * 统计平均响应时间
     */
    @Query("SELECT AVG(a.responseTime) FROM DocumentAccessLog a WHERE a.documentId = :documentId")
    Double avgResponseTimeByDocumentId(Long documentId);
    
    /**
     * 查询高频访问IP
     */
    @Query("SELECT a.ipAddress, COUNT(a) as count FROM DocumentAccessLog a " +
            "GROUP BY a.ipAddress ORDER BY count DESC")
    List<Object[]> findHighFrequencyIps();
} 