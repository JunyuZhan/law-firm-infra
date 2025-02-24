package com.lawfirm.model.cases.enums.doc;

/**
 * 文档安全级别枚举
 */
public enum DocumentSecurityLevelEnum {
    PUBLIC("公开"),
    INTERNAL("内部"),
    CONFIDENTIAL("保密"),
    HIGHLY_CONFIDENTIAL("高度保密");

    private final String description;

    DocumentSecurityLevelEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 