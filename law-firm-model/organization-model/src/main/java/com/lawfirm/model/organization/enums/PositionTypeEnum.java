package com.lawfirm.model.organization.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum PositionTypeEnum implements BaseEnum<String> {
    
    PARTNER("PARTNER", "合伙人"),
    LAWYER("LAWYER", "律师"),
    PARALEGAL("PARALEGAL", "律师助理"),
    MANAGER("MANAGER", "经理"),
    STAFF("STAFF", "职员"),
    INTERN("INTERN", "实习生"),
    CONSULTANT("CONSULTANT", "顾问");

    private final String value;
    private final String description;

    PositionTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
} 