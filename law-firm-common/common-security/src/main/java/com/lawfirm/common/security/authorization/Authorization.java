package com.lawfirm.common.security.authorization;

import java.util.Set;

/**
 * 授权基础接口
 * 
 * <p>该接口定义了系统授权相关的基本操作，包括获取权限、角色以及权限校验功能。
 * 实现类需要提供具体的授权逻辑，如基于数据库的授权、基于LDAP的授权等。</p>
 * 
 * @author JunyuZhan
 * @since 1.0.0
 */
public interface Authorization {
    
    /**
     * 获取当前用户所有权限集合
     * 
     * <p>返回当前登录用户拥有的所有权限标识符集合</p>
     * 
     * @return 权限标识符集合
     */
    Set<String> getPermissions();
    
    /**
     * 获取当前用户所有角色集合
     * 
     * <p>返回当前登录用户拥有的所有角色标识符集合</p>
     * 
     * @return 角色标识符集合
     */
    Set<String> getRoles();
    
    /**
     * 校验当前用户是否具有指定权限
     * 
     * <p>用于判断当前登录用户是否拥有特定的权限</p>
     * 
     * @param permission 权限标识符
     * @return 如果用户拥有该权限则返回true，否则返回false
     */
    boolean hasPermission(String permission);
    
    /**
     * 校验当前用户是否具有指定角色
     * 
     * <p>用于判断当前登录用户是否拥有特定的角色</p>
     * 
     * @param role 角色标识符
     * @return 如果用户拥有该角色则返回true，否则返回false
     */
    boolean hasRole(String role);
}
