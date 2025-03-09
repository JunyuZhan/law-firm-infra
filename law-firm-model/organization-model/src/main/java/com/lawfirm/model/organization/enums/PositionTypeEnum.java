package com.lawfirm.model.organization.enums;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 职位类型枚举
 */
public enum PositionTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 管理职位
     */
    MANAGEMENT(1, "管理职位"),
    
    /**
     * 专业职位
     */
    PROFESSIONAL(2, "专业职位"),
    
    /**
     * 律师职位
     */
    LAWYER(3, "律师职位"),
    
    /**
     * 行政职位
     */
    ADMINISTRATIVE(4, "行政职位"),
    
    /**
     * 其他职位
     */
    OTHER(9, "其他职位");
    
    private final Integer value;
    private final String description;
    
    PositionTypeEnum(Integer value, String description) {
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
    
    public static PositionTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (PositionTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
} 