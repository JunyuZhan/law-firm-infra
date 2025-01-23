package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum CaseTypeEnum implements BaseEnum<String> {
    
    CIVIL("CIVIL", "民事案件"),
    CRIMINAL("CRIMINAL", "刑事案件"),
    ADMINISTRATIVE("ADMINISTRATIVE", "行政案件"),
    COMMERCIAL("COMMERCIAL", "商事案件"),
    INTELLECTUAL_PROPERTY("INTELLECTUAL_PROPERTY", "知识产权案件"),
    LABOR("LABOR", "劳动案件"),
    MARITIME("MARITIME", "海事案件"),
    OTHER("OTHER", "其他案件");

    private final String value;
    private final String description;

    CaseTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 