package com.lawfirm.conflict.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 利益冲突状态枚举
 */
@Getter
@AllArgsConstructor
public enum ConflictStatus {
    
    PENDING(1, "待处理"),
    PROCESSING(2, "处理中"),
    RESOLVED(3, "已解决"),
    REJECTED(4, "已驳回");
    
    private final Integer code;
    private final String desc;
    
    public static String getDesc(Integer code) {
        if (code == null) {
            return null;
        }
        for (ConflictStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status.getDesc();
            }
        }
        return null;
    }
}
