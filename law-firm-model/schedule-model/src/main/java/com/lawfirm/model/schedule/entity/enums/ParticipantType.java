package com.lawfirm.model.schedule.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 参与者类型枚举
 */
@Getter
public enum ParticipantType {
    
    ORGANIZER(0, "组织者"),
    REQUIRED(1, "必要参与者"),
    OPTIONAL(2, "可选参与者");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    
    private final String desc;
    
    ParticipantType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static ParticipantType getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ParticipantType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 