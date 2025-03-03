package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 交易类型枚举
 */
@Getter
public enum TransactionTypeEnum implements BaseEnum<String> {
    
    INCOME("INCOME", "收入"),
    EXPENSE("EXPENSE", "支出"),
    REFUND("REFUND", "退款"),
    TRANSFER("TRANSFER", "转账"),
    DEPOSIT("DEPOSIT", "押金"),
    ADVANCE("ADVANCE", "预付款"),
    OTHER("OTHER", "其他");

    private final String value;
    private final String description;

    TransactionTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 