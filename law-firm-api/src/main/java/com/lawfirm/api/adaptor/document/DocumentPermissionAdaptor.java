package com.lawfirm.api.adaptor.document;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.document.dto.permission.PermissionCreateDTO;
import com.lawfirm.model.document.dto.permission.PermissionUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentPermission;
import com.lawfirm.model.document.service.DocumentPermissionService;
import com.lawfirm.model.document.vo.permission.PermissionVO;
import com.lawfirm.model.document.enums.DocumentOperationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档权限管理适配器
 */
@Component
public class DocumentPermissionAdaptor extends BaseAdaptor {

    private final DocumentPermissionService documentPermissionService;

    @Autowired
    public DocumentPermissionAdaptor(@Qualifier("documentPermissionServiceImpl") DocumentPermissionService documentPermissionService) {
        this.documentPermissionService = documentPermissionService;
    }

    /**
     * 创建文档权限
     */
    public PermissionVO createPermission(PermissionCreateDTO dto) {
        DocumentPermission permission = documentPermissionService.createPermission(dto);
        return convert(permission, PermissionVO.class);
    }

    /**
     * 更新文档权限
     */
    public PermissionVO updatePermission(Long id, PermissionUpdateDTO dto) {
        DocumentPermission permission = documentPermissionService.updatePermission(id, dto);
        return convert(permission, PermissionVO.class);
    }

    /**
     * 删除文档权限
     */
    public void deletePermission(Long id) {
        documentPermissionService.deletePermission(id);
    }

    /**
     * 获取文档权限详情
     */
    public PermissionVO getPermission(Long id) {
        DocumentPermission permission = documentPermissionService.getPermission(id);
        return convert(permission, PermissionVO.class);
    }

    /**
     * 获取所有文档权限
     */
    public List<PermissionVO> listPermissions() {
        List<DocumentPermission> permissions = documentPermissionService.listPermissions();
        return permissions.stream()
                .map(permission -> convert(permission, PermissionVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据文档ID查询权限
     */
    public List<PermissionVO> getPermissionsByDocumentId(Long documentId) {
        List<DocumentPermission> permissions = documentPermissionService.getPermissionsByDocumentId(documentId);
        return permissions.stream()
                .map(permission -> convert(permission, PermissionVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据用户ID查询权限
     */
    public List<PermissionVO> getPermissionsByUserId(Long userId) {
        List<DocumentPermission> permissions = documentPermissionService.getPermissionsByUserId(userId);
        return permissions.stream()
                .map(permission -> convert(permission, PermissionVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查用户是否有文档操作权限
     */
    public boolean checkPermission(Long documentId, Long userId, DocumentOperationEnum operation) {
        return documentPermissionService.checkPermission(documentId, userId, operation);
    }

    /**
     * 为用户分配文档权限
     */
    public void grantPermission(Long documentId, Long userId, DocumentOperationEnum operation) {
        documentPermissionService.grantPermission(documentId, userId, operation);
    }

    /**
     * 撤销用户文档权限
     */
    public void revokePermission(Long documentId, Long userId, DocumentOperationEnum operation) {
        documentPermissionService.revokePermission(documentId, userId, operation);
    }

    /**
     * 检查权限是否存在
     */
    public boolean existsPermission(Long id) {
        return documentPermissionService.existsPermission(id);
    }

    /**
     * 获取权限数量
     */
    public long countPermissions() {
        return documentPermissionService.countPermissions();
    }

    /**
     * 同步业务系统文档权限
     */
    public void syncBusinessDocumentsPermission(String businessType, Long businessId, 
                                              List<DocumentPermissionService.UserPermission> userPermissions) {
        documentPermissionService.syncBusinessDocumentsPermission(businessType, businessId, userPermissions);
    }

    /**
     * 添加用户文档权限
     */
    public void addUserPermission(String businessType, Long businessId, Long userId, String permission) {
        documentPermissionService.addUserPermission(businessType, businessId, userId, permission);
    }

    /**
     * 移除用户文档权限
     */
    public void removeUserPermission(String businessType, Long businessId, Long userId) {
        documentPermissionService.removeUserPermission(businessType, businessId, userId);
    }

    /**
     * 创建用户权限对象
     */
    public DocumentPermissionService.UserPermission createUserPermission(Long userId, String permission) {
        return new DocumentPermissionService.UserPermission(userId, permission);
    }

    /**
     * 批量创建用户权限对象
     */
    public List<DocumentPermissionService.UserPermission> createUserPermissions(
            List<Long> userIds, String permission) {
        return userIds.stream()
                .map(userId -> createUserPermission(userId, permission))
                .collect(Collectors.toList());
    }
} 