package com.lawfirm.common.security.authorization;

/**
 * 授权基础接口
 */
public interface Authorization {
    
    /**
     * 是否有权限
     * @param permission 权限标识
     * @return 是否有权限
     */
    boolean hasPermission(String permission);
    
    /**
     * 是否有角色
     * @param role 角色标识
     * @return 是否有角色
     */
    boolean hasRole(String role);
} 