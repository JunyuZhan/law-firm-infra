package com.lawfirm.model.document.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum DocumentSecurityLevelEnum implements BaseEnum<String> {
    
    PUBLIC("PUBLIC", "公开"),
    INTERNAL("INTERNAL", "内部"),
    CONFIDENTIAL("CONFIDENTIAL", "保密"),
    STRICTLY_CONFIDENTIAL("STRICTLY_CONFIDENTIAL", "绝密");

    private final String value;
    private final String description;

    DocumentSecurityLevelEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
} 