package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 账单状态枚举
 */
@Getter
public enum BillingStatusEnum implements BaseEnum<String> {
    
    DRAFT("DRAFT", "草稿"),
    PENDING("PENDING", "待付款"),
    PAID("PAID", "已付款"),
    OVERDUE("OVERDUE", "已逾期"),
    CANCELLED("CANCELLED", "已取消");

    private final String value;
    private final String description;

    BillingStatusEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 