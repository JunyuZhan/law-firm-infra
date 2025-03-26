package com.lawfirm.common.security.dao;

import java.util.Set;

/**
 * 授权数据访问接口
 * 
 * <p>该接口定义了获取当前用户权限和角色数据的方法。
 * 实现类需要提供具体的数据访问逻辑，如从数据库、缓存或其他数据源获取授权信息。</p>
 * 
 * <p>该接口通常与{@link com.lawfirm.common.security.authorization.Authorization}接口的实现类配合使用，
 * 为授权功能提供数据支持。</p>
 * 
 * @author 律所系统
 * @since 1.0.0
 */
public interface AuthorizationDao {
    
    /**
     * 获取当前用户的权限列表
     * 
     * <p>查询并返回当前登录用户拥有的所有权限标识符。
     * 通常情况下，这些权限标识符是由系统预先定义的字符串，用于标识特定的操作权限。</p>
     * 
     * @return 当前用户的权限标识符集合
     */
    Set<String> findCurrentUserPermissions();
    
    /**
     * 获取当前用户的角色列表
     * 
     * <p>查询并返回当前登录用户拥有的所有角色标识符。
     * 角色通常代表用户在系统中的某种身份或职责。</p>
     * 
     * @return 当前用户的角色标识符集合
     */
    Set<String> findCurrentUserRoles();
} 