package com.lawfirm.model.schedule.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 提醒类型枚举
 */
@Getter
public enum ReminderType {
    
    SYSTEM(1, "系统提醒"),
    EMAIL(2, "邮件提醒"),
    SMS(3, "短信提醒"),
    APP_PUSH(4, "APP推送");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    
    private final String desc;
    
    ReminderType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static ReminderType getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ReminderType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 