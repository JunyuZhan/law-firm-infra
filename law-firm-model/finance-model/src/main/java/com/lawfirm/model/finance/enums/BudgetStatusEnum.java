package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 预算状态枚举
 */
@Getter
public enum BudgetStatusEnum implements BaseEnum<String> {
    
    DRAFT("DRAFT", "草稿"),
    PENDING("PENDING", "待审批"),
    APPROVED("APPROVED", "已审批"),
    REJECTED("REJECTED", "已驳回"),
    CANCELLED("CANCELLED", "已取消");

    private final String value;
    private final String description;

    BudgetStatusEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 