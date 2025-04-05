package com.lawfirm.model.schedule.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 提醒状态枚举
 */
@Getter
public enum ReminderStatus {
    
    PENDING(0, "待提醒"),
    SENT(1, "已提醒"),
    IGNORED(2, "已忽略");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    
    private final String desc;
    
    ReminderStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static ReminderStatus getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ReminderStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 