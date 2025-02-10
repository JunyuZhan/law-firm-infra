package com.lawfirm.document.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档类型枚举
 */
@Getter
@AllArgsConstructor
public enum DocumentType {
    
    CONTRACT(1, "合同"),
    AGREEMENT(2, "协议"),
    REPORT(3, "报告"),
    OTHER(4, "其他");
    
    private final Integer code;
    private final String desc;
    
    public static String getDesc(Integer code) {
        if (code == null) {
            return null;
        }
        for (DocumentType type : values()) {
            if (type.getCode().equals(code)) {
                return type.getDesc();
            }
        }
        return null;
    }
} 