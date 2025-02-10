package com.lawfirm.model.document.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文档安全级别枚举
 */
@Getter
public enum DocumentSecurityLevelEnum {
    
    PUBLIC("PUBLIC", "公开"),
    INTERNAL("INTERNAL", "内部"),
    CONFIDENTIAL("CONFIDENTIAL", "保密"),
    STRICTLY_CONFIDENTIAL("STRICTLY_CONFIDENTIAL", "绝密");

    @EnumValue
    @JsonValue
    private final String code;
    private final String description;

    DocumentSecurityLevelEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
} 