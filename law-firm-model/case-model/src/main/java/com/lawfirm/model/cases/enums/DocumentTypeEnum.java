package com.lawfirm.model.cases.enums;

/**
 * 文档类型枚举
 */
public enum DocumentTypeEnum {
    CONTRACT("合同"),
    AGREEMENT("协议"),
    LETTER("函件"),
    REPORT("报告"),
    MEMO("备忘录"),
    OTHER("其他");

    private final String description;

    DocumentTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 