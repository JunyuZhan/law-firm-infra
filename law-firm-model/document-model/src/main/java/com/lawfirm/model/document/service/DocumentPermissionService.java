package com.lawfirm.model.document.service;

import com.lawfirm.model.document.dto.permission.DocumentPermissionCreateDTO;
import com.lawfirm.model.document.dto.permission.DocumentPermissionUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentPermission;
import com.lawfirm.model.document.enums.DocumentOperationEnum;
import java.util.List;

/**
 * 文档权限服务接口
 * 管理文档的权限分配和校验
 */
public interface DocumentPermissionService {

    /**
     * 创建文档权限
     *
     * @param dto 创建DTO
     * @return 创建的权限实体
     */
    DocumentPermission createPermission(DocumentPermissionCreateDTO dto);

    /**
     * 更新文档权限
     *
     * @param id 权限ID
     * @param dto 更新DTO
     * @return 更新后的权限实体
     */
    DocumentPermission updatePermission(Long id, DocumentPermissionUpdateDTO dto);

    /**
     * 删除文档权限
     *
     * @param id 权限ID
     */
    void deletePermission(Long id);

    /**
     * 获取文档权限详情
     *
     * @param id 权限ID
     * @return 权限实体
     */
    DocumentPermission getPermission(Long id);

    /**
     * 获取所有文档权限
     *
     * @return 权限列表
     */
    List<DocumentPermission> listPermissions();

    /**
     * 根据文档ID查询权限
     *
     * @param documentId 文档ID
     * @return 权限列表
     */
    List<DocumentPermission> getPermissionsByDocumentId(Long documentId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<DocumentPermission> getPermissionsByUserId(Long userId);

    /**
     * 检查用户是否有文档操作权限
     *
     * @param documentId 文档ID
     * @param userId 用户ID
     * @param operation 操作类型
     * @return 是否有权限
     */
    boolean checkPermission(Long documentId, Long userId, DocumentOperationEnum operation);

    /**
     * 为用户分配文档权限
     *
     * @param documentId 文档ID
     * @param userId 用户ID
     * @param operation 操作类型
     */
    void grantPermission(Long documentId, Long userId, DocumentOperationEnum operation);

    /**
     * 撤销用户文档权限
     *
     * @param documentId 文档ID
     * @param userId 用户ID
     * @param operation 操作类型
     */
    void revokePermission(Long documentId, Long userId, DocumentOperationEnum operation);

    /**
     * 检查权限是否存在
     *
     * @param id 权限ID
     * @return 是否存在
     */
    boolean existsPermission(Long id);

    /**
     * 获取权限数量
     *
     * @return 权限数量
     */
    long countPermissions();

    /**
     * 同步业务系统文档权限
     *
     * @param businessType 业务类型（如"CASE"、"CONTRACT"等）
     * @param businessId 业务ID
     * @param userPermissions 用户权限列表
     */
    void syncBusinessDocumentsPermission(String businessType, Long businessId, 
                                        List<UserPermission> userPermissions);

    /**
     * 添加用户文档权限
     *
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @param userId 用户ID
     * @param permission 权限类型
     */
    void addUserPermission(String businessType, Long businessId, Long userId, String permission);

    /**
     * 移除用户文档权限
     *
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @param userId 用户ID
     */
    void removeUserPermission(String businessType, Long businessId, Long userId);
    
    /**
     * 用户权限封装类
     */
    class UserPermission {
        private final Long userId;
        private final String permission;
        
        public UserPermission(Long userId, String permission) {
            this.userId = userId;
            this.permission = permission;
        }
        
        public Long getUserId() {
            return userId;
        }
        
        public String getPermission() {
            return permission;
        }
    }
}
