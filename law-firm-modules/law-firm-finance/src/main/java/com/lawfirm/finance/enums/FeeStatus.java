package com.lawfirm.finance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 收费状态枚举
 */
@Getter
@AllArgsConstructor
public enum FeeStatus {
    
    UNPAID("UNPAID", "未支付"),
    PAID("PAID", "已支付"),
    PARTIAL("PARTIAL", "部分支付"),
    REFUNDED("REFUNDED", "已退款");
    
    private final String code;
    private final String desc;
    
    public static String getDesc(String code) {
        if (code == null) {
            return null;
        }
        for (FeeStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status.getDesc();
            }
        }
        return null;
    }
} 