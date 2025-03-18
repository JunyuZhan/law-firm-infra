package com.lawfirm.model.document.service;

import java.util.List;

/**
 * 文档权限服务接口
 * 管理文档的权限分配和校验
 */
public interface DocumentPermissionService {

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
