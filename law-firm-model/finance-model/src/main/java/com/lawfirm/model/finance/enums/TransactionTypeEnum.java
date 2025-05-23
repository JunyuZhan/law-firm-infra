package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 交易类型枚举
 */
@Getter
public enum TransactionTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 收入
     */
    INCOME(1, "收入"),
    
    /**
     * 支出
     */
    EXPENSE(2, "支出"),
    
    /**
     * 退款
     */
    REFUND(3, "退款"),
    
    /**
     * 转账
     */
    TRANSFER(4, "转账"),
    
    /**
     * 押金
     */
    DEPOSIT(5, "押金"),
    
    /**
     * 预付款
     */
    ADVANCE(6, "预付款"),
    
    /**
     * 其他
     */
    OTHER(99, "其他");

    private final Integer value;
    private final String description;

    TransactionTypeEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * 根据值获取枚举
     */
    public static TransactionTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (TransactionTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
} 