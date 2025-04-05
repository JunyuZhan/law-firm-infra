package com.lawfirm.model.schedule.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 日程类型枚举
 */
@Getter
public enum ScheduleType {
    
    MEETING(1, "会议"),
    TASK(2, "任务"),
    APPOINTMENT(3, "约见"),
    COURT(4, "法庭出庭"),
    TRAVEL(5, "出差"),
    LEAVE(6, "休假"),
    OTHER(99, "其他");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    
    private final String desc;
    
    ScheduleType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static ScheduleType getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ScheduleType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 