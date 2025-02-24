package com.lawfirm.model.document.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档状态枚举
 */
@Getter
@AllArgsConstructor
public enum DocumentStatusEnum {

    DRAFT("DRAFT", "草稿"),
    REVIEWING("REVIEWING", "审核中"),
    PUBLISHED("PUBLISHED", "已发布"),
    ARCHIVED("ARCHIVED", "已归档"),
    DELETED("DELETED", "已删除"),
    LOCKED("LOCKED", "已锁定");

    private final String value;
    private final String label;
} 