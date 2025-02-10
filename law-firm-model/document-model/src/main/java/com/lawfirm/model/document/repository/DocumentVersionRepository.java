package com.lawfirm.model.document.repository;

import com.lawfirm.model.document.entity.DocumentVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文档版本Repository接口
 */
@Repository
public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long> {
    
    /**
     * 查询文档的所有版本
     */
    List<DocumentVersion> findByDocumentIdOrderByVersionDesc(Long documentId);
    
    /**
     * 分页查询文档版本
     */
    Page<DocumentVersion> findByDocumentIdOrderByVersionDesc(Long documentId, Pageable pageable);
    
    /**
     * 查询文档的最新版本
     */
    DocumentVersion findFirstByDocumentIdOrderByVersionDesc(Long documentId);
    
    /**
     * 查询指定版本
     */
    DocumentVersion findByDocumentIdAndVersion(Long documentId, Integer version);
    
    /**
     * 查询文档的版本数量
     */
    long countByDocumentId(Long documentId);
    
    /**
     * 查询需要清理的历史版本
     */
    @Query("SELECT v FROM DocumentVersion v WHERE v.documentId = :documentId " +
            "AND v.version NOT IN (SELECT v2.version FROM DocumentVersion v2 " +
            "WHERE v2.documentId = :documentId ORDER BY v2.version DESC LIMIT :keepVersions)")
    List<DocumentVersion> findVersionsToClean(Long documentId, Integer keepVersions);
    
    /**
     * 查询指定版本范围
     */
    @Query("SELECT v FROM DocumentVersion v WHERE v.documentId = :documentId " +
            "AND v.version BETWEEN :startVersion AND :endVersion ORDER BY v.version")
    List<DocumentVersion> findVersionRange(Long documentId, Integer startVersion, Integer endVersion);
} 