package com.lawfirm.archive.model.enums;

import lombok.Getter;

/**
 * 档案保管期限枚举
 */
@Getter
public enum RetentionPeriodEnum {
    
    PERMANENT("PERMANENT", "永久"),
    LONG_TERM("LONG_TERM", "长期(30年)"),
    MEDIUM_TERM("MEDIUM_TERM", "中期(10年)"),
    SHORT_TERM("SHORT_TERM", "短期(5年)"),
    TEMPORARY("TEMPORARY", "临时(2年)");
    
    private final String code;
    private final String desc;
    
    RetentionPeriodEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 