package com.lawfirm.archive.model.enums;

import lombok.Getter;

/**
 * 档案密级枚举
 */
@Getter
public enum SecurityLevelEnum {
    
    PUBLIC("PUBLIC", "公开"),
    INTERNAL("INTERNAL", "内部"),
    CONFIDENTIAL("CONFIDENTIAL", "保密"),
    SECRET("SECRET", "机密");
    
    private final String code;
    private final String desc;
    
    SecurityLevelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 