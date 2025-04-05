package com.lawfirm.model.schedule.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 日程状态枚举
 */
@Getter
public enum ScheduleStatus {
    
    PLANNED(0, "计划中"),
    IN_PROGRESS(1, "进行中"),
    COMPLETED(2, "已完成"),
    CANCELED(3, "已取消");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    
    private final String desc;
    
    ScheduleStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static ScheduleStatus getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ScheduleStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 