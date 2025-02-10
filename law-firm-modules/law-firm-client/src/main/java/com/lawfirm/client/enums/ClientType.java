package com.lawfirm.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 客户类型枚举
 */
@Getter
@AllArgsConstructor
public enum ClientType {
    
    PERSONAL(1, "个人"),
    ENTERPRISE(2, "企业");
    
    private final Integer code;
    private final String desc;
    
    public static String getDesc(Integer code) {
        if (code == null) {
            return null;
        }
        for (ClientType type : values()) {
            if (type.getCode().equals(code)) {
                return type.getDesc();
            }
        }
        return null;
    }
} 