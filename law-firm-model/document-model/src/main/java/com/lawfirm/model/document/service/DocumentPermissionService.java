package com.lawfirm.model.document.service;

import com.lawfirm.model.document.entity.DocumentPermission;
import com.lawfirm.model.document.enums.DocumentPermissionEnum;
import com.lawfirm.model.document.enums.DocumentPermissionTargetEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 文档权限服务接口
 */
public interface DocumentPermissionService {
    
    /**
     * 授予权限
     */
    DocumentPermission grantPermission(Long documentId, DocumentPermissionTargetEnum targetType, 
            Long targetId, DocumentPermissionEnum permission);
    
    /**
     * 撤销权限
     */
    void revokePermission(Long permissionId);
    
    /**
     * 批量授予权限
     */
    List<DocumentPermission> grantPermissions(Long documentId, DocumentPermissionTargetEnum targetType, 
            List<Long> targetIds, DocumentPermissionEnum permission);
    
    /**
     * 批量撤销权限
     */
    void revokePermissions(List<Long> permissionIds);
    
    /**
     * 获取文档的所有权限
     */
    List<DocumentPermission> getDocumentPermissions(Long documentId);
    
    /**
     * 获取目标对象的所有权限
     */
    List<DocumentPermission> getTargetPermissions(DocumentPermissionTargetEnum targetType, Long targetId);
    
    /**
     * 检查权限
     */
    boolean hasPermission(Long documentId, DocumentPermissionTargetEnum targetType, 
            Long targetId, DocumentPermissionEnum permission);
    
    /**
     * 获取权限详情
     */
    DocumentPermission getPermission(Long permissionId);
    
    /**
     * 分页查询权限
     */
    Page<DocumentPermission> listPermissions(Pageable pageable);
    
    /**
     * 更新权限
     */
    DocumentPermission updatePermission(DocumentPermission permission);
    
    /**
     * 清除文档的所有权限
     */
    void clearDocumentPermissions(Long documentId);
    
    /**
     * 清除目标对象的所有权限
     */
    void clearTargetPermissions(DocumentPermissionTargetEnum targetType, Long targetId);
} 