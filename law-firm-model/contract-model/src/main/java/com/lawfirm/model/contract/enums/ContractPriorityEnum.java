package com.lawfirm.model.contract.enums;

import lombok.Getter;

/**
 * 合同优先级枚举
 */
@Getter
public enum ContractPriorityEnum {
    HIGH(1, "高优先级"),
    NORMAL(2, "普通优先级"),
    LOW(3, "低优先级");

    private final int code;
    private final String description;

    ContractPriorityEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
} 