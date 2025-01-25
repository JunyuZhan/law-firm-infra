package com.lawfirm.cases.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 案件提醒状态枚举
 */
@Getter
public enum ReminderStatusEnum {
    
    PENDING(0, "未提醒"),
    REMINDED(1, "已提醒"),
    CONFIRMED(2, "已确认");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    private final String desc;
    
    ReminderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 