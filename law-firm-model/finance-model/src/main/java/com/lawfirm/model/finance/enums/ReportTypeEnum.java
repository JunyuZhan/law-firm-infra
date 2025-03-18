package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 报表类型枚举
 */
@Getter
public enum ReportTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 利润表
     */
    INCOME_STATEMENT(1, "利润表"),
    
    /**
     * 资产负债表
     */
    BALANCE_SHEET(2, "资产负债表"),
    
    /**
     * 现金流量表
     */
    CASH_FLOW(3, "现金流量表"),
    
    /**
     * 预算执行表
     */
    BUDGET_EXECUTION(4, "预算执行表"),
    
    /**
     * 成本分析表
     */
    COST_ANALYSIS(5, "成本分析表"),
    
    /**
     * 部门业绩表
     */
    DEPARTMENT_PERFORMANCE(6, "部门业绩表"),
    
    /**
     * 客户分析表
     */
    CLIENT_ANALYSIS(7, "客户分析表"),
    
    /**
     * 案件分析表
     */
    CASE_ANALYSIS(8, "案件分析表");

    private final Integer value;
    private final String description;

    ReportTypeEnum(Integer value, String description) {
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
    public static ReportTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (ReportTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
} 