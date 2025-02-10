package com.lawfirm.conflict.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 利益冲突类型枚举
 */
@Getter
@AllArgsConstructor
public enum ConflictType {
    
    PARTY_CONFLICT(1, "当事人冲突"),
    CASE_CONFLICT(2, "案件冲突"),
    LAWYER_CONFLICT(3, "律师冲突"),
    BUSINESS_CONFLICT(4, "业务冲突");
    
    private final Integer code;
    private final String desc;
    
    public static String getDesc(Integer code) {
        if (code == null) {
            return null;
        }
        for (ConflictType type : values()) {
            if (type.getCode().equals(code)) {
                return type.getDesc();
            }
        }
        return null;
    }
}
