package com.lawfirm.model.auth.enums;

import lombok.Getter;
import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 职位级别枚举
 */
@Getter
public enum PositionLevelEnum implements BaseEnum<Integer> {
    
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

    @Override
    public Integer getValue() {
        return code;
    }

    @Override
    public String getDescription() {
        return desc;
    }
} 