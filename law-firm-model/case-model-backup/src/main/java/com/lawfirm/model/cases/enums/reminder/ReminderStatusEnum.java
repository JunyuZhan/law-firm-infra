package com.lawfirm.model.cases.enums.reminder;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件提醒状态枚举
 */
@Getter
public enum ReminderStatusEnum implements BaseEnum<Integer> {
    
    PENDING(0, "待处理"),
    CONFIRMED(1, "已确认"),
    COMPLETED(2, "已完成"),
    CANCELLED(3, "已取消");
    
    private final Integer code;
    private final String desc;

    ReminderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.desc;
    }
} 