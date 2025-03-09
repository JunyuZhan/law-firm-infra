package com.lawfirm.model.auth.enums;

import lombok.Getter;

/**
 * 系统角色枚举（通用角色，不包含业务角色）
 */
@Getter
public enum RoleEnum {
    
    ADMIN("admin", "系统管理员", "系统最高权限管理者", null, DataScopeEnum.ALL),
    USER("user", "普通用户", "系统普通用户", ADMIN, DataScopeEnum.PERSONAL);

    private final String code;
    private final String name;
    private final String description;
    private final RoleEnum parent;
    private final DataScopeEnum defaultDataScope;

    RoleEnum(String code, String name, String description, RoleEnum parent, DataScopeEnum defaultDataScope) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.parent = parent;
        this.defaultDataScope = defaultDataScope;
    }

    /**
     * 获取角色的所有上级角色
     */
    public RoleEnum[] getParentRoles() {
        if (parent == null) {
            return new RoleEnum[0];
        }
        RoleEnum[] parentRoles = parent.getParentRoles();
        RoleEnum[] result = new RoleEnum[parentRoles.length + 1];
        System.arraycopy(parentRoles, 0, result, 0, parentRoles.length);
        result[parentRoles.length] = parent;
        return result;
    }

    /**
     * 判断是否具有指定角色的权限
     */
    public boolean hasRole(RoleEnum role) {
        if (this == role) {
            return true;
        }
        RoleEnum[] parentRoles = getParentRoles();
        for (RoleEnum parentRole : parentRoles) {
            if (parentRole == role) {
                return true;
            }
        }
        return false;
    }
} 