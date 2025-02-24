package com.lawfirm.model.cases.enums.doc;

/**
 * 文档状态枚举
 */
public enum DocumentStatusEnum {
    DRAFT("草稿"),
    REVIEWING("审核中"),
    APPROVED("已批准"),
    REJECTED("已拒绝"),
    ARCHIVED("已归档");

    private final String description;

    DocumentStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 