package com.lawfirm.model.document.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum DocumentStatusEnum implements BaseEnum<String> {
    
    DRAFT("DRAFT", "草稿"),
    REVIEWING("REVIEWING", "审核中"),
    APPROVED("APPROVED", "已批准"),
    REJECTED("REJECTED", "已驳回"),
    PUBLISHED("PUBLISHED", "已发布"),
    ARCHIVED("ARCHIVED", "已归档"),
    DELETED("DELETED", "已删除");

    private final String value;
    private final String description;

    DocumentStatusEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
} 