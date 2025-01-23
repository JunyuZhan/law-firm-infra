package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum EmployeeTypeEnum implements BaseEnum<String> {
    
    LAWYER("LAWYER", "律师"),
    PARALEGAL("PARALEGAL", "律师助理"),
    ADMINISTRATIVE("ADMINISTRATIVE", "行政人员"),
    FINANCE("FINANCE", "财务人员"),
    HR("HR", "人力资源"),
    IT("IT", "信息技术"),
    MARKETING("MARKETING", "市场营销"),
    INTERN("INTERN", "实习生"),
    OTHER("OTHER", "其他");

    private final String value;
    private final String description;

    EmployeeTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
} 