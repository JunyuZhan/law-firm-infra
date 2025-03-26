package com.lawfirm.auth.security.dao;

import com.lawfirm.auth.utils.SecurityUtils;
import com.lawfirm.common.security.dao.AuthorizationDao;
import com.lawfirm.model.auth.entity.Role;
import com.lawfirm.model.auth.service.PermissionService;
import com.lawfirm.model.auth.service.RoleService;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 授权数据访问实现类
 * 
 * <p>基于Spring Security和数据库实现的授权数据访问，
 * 通过PermissionService和RoleService获取用户的权限和角色信息。</p>
 * 
 * @author 律所系统
 * @since 1.0.0
 */
@Repository
public class AuthorizationDaoImpl implements AuthorizationDao {
    
    private final PermissionService permissionService;
    private final RoleService roleService;
    
    public AuthorizationDaoImpl(PermissionService permissionService, RoleService roleService) {
        this.permissionService = permissionService;
        this.roleService = roleService;
    }

    @Override
    public Set<String> findCurrentUserPermissions() {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Collections.emptySet();
        }
        
        List<String> permissionCodes = permissionService.listPermissionCodesByUserId(userId);
        return new HashSet<>(permissionCodes);
    }

    @Override
    public Set<String> findCurrentUserRoles() {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return Collections.emptySet();
        }
        
        List<Role> roles = roleService.getRolesByUserId(userId);
        Set<String> roleCodes = new HashSet<>();
        for (Role role : roles) {
            roleCodes.add(role.getCode());
        }
        
        return roleCodes;
    }
    
    /**
     * 获取当前登录用户的ID
     * 
     * @return 用户ID，如果未登录则返回null
     */
    private Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }
} 