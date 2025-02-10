package com.lawfirm.model.document.repository;

import com.lawfirm.model.document.entity.DocumentPermission;
import com.lawfirm.model.document.enums.DocumentPermissionEnum;
import com.lawfirm.model.document.enums.DocumentPermissionTargetEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文档权限Repository接口
 */
@Repository
public interface DocumentPermissionRepository extends JpaRepository<DocumentPermission, Long> {
    
    /**
     * 查询文档的所有权限
     */
    List<DocumentPermission> findByDocumentId(Long documentId);
    
    /**
     * 查询目标对象的所有权限
     */
    List<DocumentPermission> findByTargetTypeAndTargetId(DocumentPermissionTargetEnum targetType, Long targetId);
    
    /**
     * 查询特定权限
     */
    List<DocumentPermission> findByDocumentIdAndTargetTypeAndTargetIdAndPermission(
            Long documentId, DocumentPermissionTargetEnum targetType, Long targetId, DocumentPermissionEnum permission);
    
    /**
     * 删除文档的所有权限
     */
    @Modifying
    @Query("DELETE FROM DocumentPermission p WHERE p.documentId = :documentId")
    void deleteByDocumentId(Long documentId);
    
    /**
     * 删除目标对象的所有权限
     */
    @Modifying
    @Query("DELETE FROM DocumentPermission p WHERE p.targetType = :targetType AND p.targetId = :targetId")
    void deleteByTargetTypeAndTargetId(DocumentPermissionTargetEnum targetType, Long targetId);
    
    /**
     * 检查权限是否存在
     */
    boolean existsByDocumentIdAndTargetTypeAndTargetIdAndPermission(
            Long documentId, DocumentPermissionTargetEnum targetType, Long targetId, DocumentPermissionEnum permission);
    
    /**
     * 查询已过期的权限
     */
    @Query("SELECT p FROM DocumentPermission p WHERE p.expiryTime < CURRENT_TIMESTAMP")
    List<DocumentPermission> findExpiredPermissions();
    
    /**
     * 查询即将过期的权限
     */
    @Query("SELECT p FROM DocumentPermission p WHERE p.expiryTime BETWEEN CURRENT_TIMESTAMP AND :expiryTime")
    List<DocumentPermission> findSoonExpirePermissions(java.time.LocalDateTime expiryTime);
} 