package com.lawfirm.model.document.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文档访问级别枚举
 */
@Getter
public enum DocumentAccessLevelEnum {

    /**
     * 公开 - 所有人可访问
     */
    PUBLIC("PUBLIC", "公开"),

    /**
     * 内部 - 本律所内部可访问
     */
    INTERNAL("INTERNAL", "内部"),

    /**
     * 部门 - 指定部门可访问
     */
    DEPARTMENT("DEPARTMENT", "部门"),

    /**
     * 团队 - 指定团队可访问
     */
    TEAM("TEAM", "团队"),

    /**
     * 个人 - 仅创建者和授权人可访问
     */
    PRIVATE("PRIVATE", "个人"),

    /**
     * 保密 - 需要特殊权限访问
     */
    CONFIDENTIAL("CONFIDENTIAL", "保密");

    /**
     * 级别编码
     */
    @EnumValue
    @JsonValue
    private final String code;

    /**
     * 级别名称
     */
    private final String name;

    DocumentAccessLevelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据编码获取枚举
     */
    public static DocumentAccessLevelEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (DocumentAccessLevelEnum level : values()) {
            if (level.getCode().equals(code)) {
                return level;
            }
        }
        return null;
    }
} 