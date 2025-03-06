package com.lawfirm.model.auth.enums;

import lombok.Getter;

/**
 * 系统角色枚举
 */
@Getter
public enum RoleEnum {
    
    ADMIN("admin", "系统管理员", "系统最高权限管理者", null, DataScopeEnum.ALL),
    DIRECTOR("director", "律所主任", "律所业务管理者", ADMIN, DataScopeEnum.ALL),
    PARTNER("partner", "合伙人律师", "高级律师", DIRECTOR, DataScopeEnum.TEAM),
    LAWYER("lawyer", "执业律师", "普通执业律师", PARTNER, DataScopeEnum.TEAM),
    TRAINEE("trainee", "实习律师", "实习期律师", LAWYER, DataScopeEnum.PERSONAL),
    CLERK_FINANCE("clerk_finance", "行政/财务人员", "负责行政和财务管理", DIRECTOR, DataScopeEnum.TEAM);

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