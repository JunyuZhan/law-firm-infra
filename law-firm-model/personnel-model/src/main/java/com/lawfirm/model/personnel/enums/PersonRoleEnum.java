package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 律师事务所人员角色枚举
 * 定义律所组织中的各类业务角色
 */
@Getter
public enum PersonRoleEnum implements BaseEnum<String> {
    
    /**
     * 系统管理员
     */
    ADMIN("admin", "系统管理员", "系统最高权限管理者", null, "ADMIN", "ALL"),
    
    /**
     * 律所主任
     */
    DIRECTOR("director", "律所主任", "律所业务管理者", ADMIN, "USER", "ALL"),
    
    /**
     * 合伙人律师
     */
    PARTNER("partner", "合伙人律师", "高级律师", DIRECTOR, "USER", "TEAM"),
    
    /**
     * 执业律师
     */
    LAWYER("lawyer", "执业律师", "普通执业律师", PARTNER, "USER", "TEAM"),
    
    /**
     * 实习律师
     */
    TRAINEE("trainee", "实习律师", "实习期律师", LAWYER, "USER", "PERSONAL"),
    
    /**
     * 行政人员
     */
    CLERK("clerk", "行政人员", "负责行政事务管理", DIRECTOR, "USER", "TEAM"),
    
    /**
     * 财务人员
     */
    FINANCE("finance", "财务人员", "负责财务管理", DIRECTOR, "USER", "TEAM");

    private final String code;
    private final String name;
    private final String description;
    private final PersonRoleEnum parent;
    private final String systemRoleCode; // 关联的系统角色编码，由业务层解析
    private final String dataScopeCode;  // 默认数据范围编码，由业务层解析

    PersonRoleEnum(String code, String name, String description, PersonRoleEnum parent, 
                  String systemRoleCode, String dataScopeCode) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.parent = parent;
        this.systemRoleCode = systemRoleCode;
        this.dataScopeCode = dataScopeCode;
    }

    /**
     * 获取角色的所有上级角色
     */
    public PersonRoleEnum[] getParentRoles() {
        if (parent == null) {
            return new PersonRoleEnum[0];
        }
        PersonRoleEnum[] parentRoles = parent.getParentRoles();
        PersonRoleEnum[] result = new PersonRoleEnum[parentRoles.length + 1];
        System.arraycopy(parentRoles, 0, result, 0, parentRoles.length);
        result[parentRoles.length] = parent;
        return result;
    }

    /**
     * 判断当前角色是否具有指定角色的权限
     * 即当前角色是否是指定角色或指定角色的上级
     */
    public boolean hasRole(PersonRoleEnum role) {
        if (this == role) {
            return true;
        }
        if (role == null) {
            return false;
        }
        for (PersonRoleEnum parent : role.getParentRoles()) {
            if (this == parent) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 根据角色编码获取枚举实例
     */
    public static PersonRoleEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (PersonRoleEnum role : values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        return null;
    }

    @Override
    public String getValue() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.name;
    }
} 