package com.lawfirm.finance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支出状态枚举
 */
@Getter
@AllArgsConstructor
public enum ExpenseStatus {
    
    PENDING("PENDING", "待审批"),
    APPROVED("APPROVED", "已审批"),
    REJECTED("REJECTED", "已驳回"),
    PAID("PAID", "已支付");
    
    private final String code;
    private final String desc;
    
    public static String getDesc(String code) {
        if (code == null) {
            return null;
        }
        for (ExpenseStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status.getDesc();
            }
        }
        return null;
    }
} 