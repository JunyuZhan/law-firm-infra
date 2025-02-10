package com.lawfirm.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 客户状态枚举
 */
@Getter
@AllArgsConstructor
public enum ClientStatus {
    
    POTENTIAL(1, "潜在"),
    FORMAL(2, "正式"),
    LOST(3, "流失");
    
    private final Integer code;
    private final String desc;
    
    public static String getDesc(Integer code) {
        if (code == null) {
            return null;
        }
        for (ClientStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status.getDesc();
            }
        }
        return null;
    }
} 