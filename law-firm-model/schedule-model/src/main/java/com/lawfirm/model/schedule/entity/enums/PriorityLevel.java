package com.lawfirm.model.schedule.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 优先级枚举
 */
@Getter
public enum PriorityLevel {
    
    HIGH(1, "高"),
    MEDIUM(2, "中"),
    LOW(3, "低");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    
    private final String desc;
    
    PriorityLevel(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static PriorityLevel getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (PriorityLevel level : values()) {
            if (level.getCode().equals(code)) {
                return level;
            }
        }
        return null;
    }
} 