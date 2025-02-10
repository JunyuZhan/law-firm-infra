package com.lawfirm.finance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 收费类型枚举
 */
@Getter
@AllArgsConstructor
public enum FeeType {
    
    CASE("CASE", "案件收费"),
    CONSULTATION("CONSULTATION", "咨询收费"),
    OTHER("OTHER", "其他收费");
    
    private final String code;
    private final String desc;
    
    public static String getDesc(String code) {
        if (code == null) {
            return null;
        }
        for (FeeType type : values()) {
            if (type.getCode().equals(code)) {
                return type.getDesc();
            }
        }
        return null;
    }
} 