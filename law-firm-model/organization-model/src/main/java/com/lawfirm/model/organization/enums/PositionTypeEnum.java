package com.lawfirm.model.organization.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 职位类型枚举
 */
@Getter
public enum PositionTypeEnum implements BaseEnum<String> {
    
    MANAGEMENT("MANAGEMENT", "管理岗"),
    PROFESSIONAL("PROFESSIONAL", "专业岗"),
    TECHNICAL("TECHNICAL", "技术岗"),
    SUPPORT("SUPPORT", "支持岗"),
    OTHER("OTHER", "其他");

    private final String value;
    private final String description;

    PositionTypeEnum(String value, String description) {
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