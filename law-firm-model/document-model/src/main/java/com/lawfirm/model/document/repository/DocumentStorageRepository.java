package com.lawfirm.model.document.repository;

import com.lawfirm.model.document.entity.DocumentStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文档存储Repository接口
 */
@Repository
public interface DocumentStorageRepository extends JpaRepository<DocumentStorage, Long> {

    /**
     * 查询文档的所有存储记录
     */
    List<DocumentStorage> findByDocumentIdOrderByCreateTimeDesc(Long documentId);
    
    /**
     * 分页查询文档存储记录
     */
    Page<DocumentStorage> findByDocumentIdOrderByCreateTimeDesc(Long documentId, Pageable pageable);
    
    /**
     * 查询文档的最新存储记录
     */
    DocumentStorage findFirstByDocumentIdOrderByCreateTimeDesc(Long documentId);
    
    /**
     * 根据存储路径查询
     */
    DocumentStorage findByStoragePath(String storagePath);
    
    /**
     * 查询指定时间范围内的存储记录
     */
    List<DocumentStorage> findByCreateTimeBetweenOrderByCreateTimeDesc(
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 查询需要清理的存储记录
     */
    @Query("SELECT s FROM DocumentStorage s WHERE s.status = 'DELETED' " +
            "AND s.deleteTime < :beforeTime")
    List<DocumentStorage> findStoragesToClean(LocalDateTime beforeTime);
    
    /**
     * 查询存储使用统计
     */
    @Query("SELECT SUM(s.fileSize) FROM DocumentStorage s WHERE s.status = 'ACTIVE'")
    Long calculateTotalStorageUsage();
    
    /**
     * 查询指定状态的存储记录
     */
    List<DocumentStorage> findByStatus(String status);
    
    /**
     * 删除文档的所有存储记录
     */
    void deleteByDocumentId(Long documentId);
} 