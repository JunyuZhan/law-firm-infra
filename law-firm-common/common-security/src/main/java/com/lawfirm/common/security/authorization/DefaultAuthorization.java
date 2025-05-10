package com.lawfirm.common.security.authorization;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 授权接口的默认实现
 */
@Component("commonDefaultAuthorization")
public class DefaultAuthorization implements Authorization {

    private Set<String> permissions = new HashSet<>();
    private Set<String> roles = new HashSet<>();

    @Override
    public Set<String> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    @Override
    public Set<String> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    @Override
    public boolean hasPermission(String permission) {
        if (permission == null) {
            return false;
        }
        return permissions.contains(permission);
    }

    @Override
    public boolean hasRole(String role) {
        if (role == null) {
            return false;
        }
        return roles.contains(role);
    }

    /**
     * 添加权限
     * @param permission 权限标识符
     */
    public void addPermission(String permission) {
        if (permission != null) {
            permissions.add(permission);
        }
    }

    /**
     * 添加角色
     * @param role 角色标识符
     */
    public void addRole(String role) {
        if (role != null) {
            roles.add(role);
        }
    }

    /**
     * 清空所有权限和角色
     */
    public void clear() {
        permissions.clear();
        roles.clear();
    }
} 