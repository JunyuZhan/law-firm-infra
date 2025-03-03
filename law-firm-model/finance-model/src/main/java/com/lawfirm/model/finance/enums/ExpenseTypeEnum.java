package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 支出类型枚举
 */
@Getter
public enum ExpenseTypeEnum implements BaseEnum<String> {
    
    SALARY("SALARY", "工资薪酬"),
    OFFICE("OFFICE", "办公费用"),
    TRAVEL("TRAVEL", "差旅费用"),
    ENTERTAINMENT("ENTERTAINMENT", "业务招待"),
    RENT("RENT", "房租物业"),
    UTILITIES("UTILITIES", "水电费用"),
    EQUIPMENT("EQUIPMENT", "设备采购"),
    MAINTENANCE("MAINTENANCE", "维修维护"),
    TRAINING("TRAINING", "培训费用"),
    MARKETING("MARKETING", "市场营销"),
    CONSULTING("CONSULTING", "咨询费用"),
    TAX("TAX", "税费支出"),
    INSURANCE("INSURANCE", "保险费用"),
    OTHER("OTHER", "其他支出");

    private final String value;
    private final String description;

    ExpenseTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 