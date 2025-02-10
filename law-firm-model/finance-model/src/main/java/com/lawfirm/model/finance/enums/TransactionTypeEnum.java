package com.lawfirm.model.finance.enums;

import lombok.Getter;

@Getter
public enum TransactionTypeEnum {
    INCOME("收入"),
    EXPENSE("支出"),
    REFUND("退款"),
    TRANSFER("转账"),
    DEPOSIT("押金"),
    ADVANCE("预付款"),
    OTHER("其他");

    private final String description;

    TransactionTypeEnum(String description) {
        this.description = description;
    }
} 