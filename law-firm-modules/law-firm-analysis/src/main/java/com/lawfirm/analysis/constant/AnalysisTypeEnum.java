package com.lawfirm.analysis.constant;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 分析类型枚举
 */
@Getter
public enum AnalysisTypeEnum implements BaseEnum<Integer> {
    CASE_ANALYSIS(1, "案件分析"),
    FINANCE_ANALYSIS(2, "财务分析"),
    PERFORMANCE_ANALYSIS(3, "绩效分析");

    private final Integer value;
    private final String label;

    AnalysisTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return label;
    }
} 
