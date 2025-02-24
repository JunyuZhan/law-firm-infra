package com.lawfirm.model.auth.enums;

import lombok.Getter;

/**
 * 职位级别枚举
 */
@Getter
public enum PositionLevelEnum {
    
    /**
     * 高层
     */
    HIGH(0, "高层"),
    
    /**
     * 中层
     */
    MIDDLE(1, "中层"),
    
    /**
     * 基层
     */
    LOW(2, "基层");
    
    private final Integer code;
    private final String desc;
    
    PositionLevelEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 