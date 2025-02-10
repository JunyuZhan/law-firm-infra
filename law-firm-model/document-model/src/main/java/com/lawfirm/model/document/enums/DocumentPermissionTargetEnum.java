package com.lawfirm.model.document.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文档权限目标类型枚举
 */
@Getter
public enum DocumentPermissionTargetEnum {
    
    USER("USER", "用户"),
    ROLE("ROLE", "角色"),
    DEPARTMENT("DEPARTMENT", "部门"),
    TEAM("TEAM", "团队"),
    GROUP("GROUP", "群组");

    @EnumValue
    @JsonValue
    private final String code;
    private final String description;

    DocumentPermissionTargetEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
} 