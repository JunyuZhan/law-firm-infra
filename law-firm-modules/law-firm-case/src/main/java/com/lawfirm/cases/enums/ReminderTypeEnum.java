package com.lawfirm.cases.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 案件提醒类型枚举
 */
@Getter
public enum ReminderTypeEnum {
    
    COURT(1, "开庭提醒"),
    DEADLINE(2, "截止日期提醒"),
    MILESTONE(3, "重要节点提醒");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    private final String desc;
    
    ReminderTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 