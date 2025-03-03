package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 预算类型枚举
 */
@Getter
public enum BudgetTypeEnum implements BaseEnum<String> {
    
    ANNUAL("ANNUAL", "年度预算"),
    QUARTERLY("QUARTERLY", "季度预算"),
    MONTHLY("MONTHLY", "月度预算"),
    PROJECT("PROJECT", "项目预算"),
    DEPARTMENT("DEPARTMENT", "部门预算"),
    SPECIAL("SPECIAL", "专项预算"),
    FIXED("FIXED", "固定预算"),
    FLEXIBLE("FLEXIBLE", "弹性预算"),
    TEMPORARY("TEMPORARY", "临时预算"),
    COST_CENTER("COST_CENTER", "成本中心预算"),
    OTHER("OTHER", "其他预算");

    private final String value;
    private final String description;

    BudgetTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 