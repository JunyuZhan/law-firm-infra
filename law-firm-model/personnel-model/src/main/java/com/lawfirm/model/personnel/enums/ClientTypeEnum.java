package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum ClientTypeEnum implements BaseEnum<String> {
    
    INDIVIDUAL("INDIVIDUAL", "个人"),
    COMPANY("COMPANY", "公司"),
    GOVERNMENT("GOVERNMENT", "政府机构"),
    NON_PROFIT("NON_PROFIT", "非营利组织"),
    OTHER("OTHER", "其他");

    private final String value;
    private final String description;

    ClientTypeEnum(String value, String description) {
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