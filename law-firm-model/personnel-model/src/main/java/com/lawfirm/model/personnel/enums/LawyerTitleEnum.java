package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum LawyerTitleEnum implements BaseEnum<String> {
    
    SENIOR_PARTNER("SENIOR_PARTNER", "高级合伙人"),
    PARTNER("PARTNER", "合伙人"),
    SENIOR_LAWYER("SENIOR_LAWYER", "高级律师"),
    LAWYER("LAWYER", "律师"),
    JUNIOR_LAWYER("JUNIOR_LAWYER", "初级律师"),
    ASSISTANT_LAWYER("ASSISTANT_LAWYER", "实习律师"),
    PARALEGAL("PARALEGAL", "律师助理");

    private final String value;
    private final String description;

    LawyerTitleEnum(String value, String description) {
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