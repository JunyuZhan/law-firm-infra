package com.lawfirm.finance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 发票类型枚举
 */
@Getter
@AllArgsConstructor
public enum InvoiceType {
    
    NORMAL(1, "增值税普通发票"),
    SPECIAL(2, "增值税专用发票");
    
    private final Integer code;
    private final String desc;
    
    public static String getDesc(Integer code) {
        if (code == null) {
            return null;
        }
        for (InvoiceType type : values()) {
            if (type.getCode().equals(code)) {
                return type.getDesc();
            }
        }
        return null;
    }
} 