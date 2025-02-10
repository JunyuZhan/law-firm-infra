package com.lawfirm.finance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支出类型枚举
 */
@Getter
@AllArgsConstructor
public enum ExpenseType {
    
    OPERATION("OPERATION", "日常运营"),
    SALARY("SALARY", "人员工资"),
    EQUIPMENT("EQUIPMENT", "办公设备"),
    OTHER("OTHER", "其他支出");
    
    private final String code;
    private final String desc;
    
    public static String getDesc(String code) {
        if (code == null) {
            return null;
        }
        for (ExpenseType type : values()) {
            if (type.getCode().equals(code)) {
                return type.getDesc();
            }
        }
        return null;
    }
} 