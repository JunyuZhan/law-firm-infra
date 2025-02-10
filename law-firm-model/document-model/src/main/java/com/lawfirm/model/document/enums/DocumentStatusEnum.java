package com.lawfirm.model.document.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文档状态枚举
 */
@Getter
public enum DocumentStatusEnum {
    
    DRAFT("DRAFT", "草稿"),
    REVIEWING("REVIEWING", "审核中"),
    APPROVED("APPROVED", "已审核"),
    REJECTED("REJECTED", "已驳回"),
    ARCHIVED("ARCHIVED", "已归档"),
    TEMPLATE("TEMPLATE", "模板"),
    DEPRECATED("DEPRECATED", "已废弃");

    @EnumValue
    @JsonValue
    private final String code;
    private final String description;

    DocumentStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
} 