package com.lawfirm.model.cases.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 案件提醒状态枚举
 */
@Getter
@AllArgsConstructor
public enum ReminderStatusEnum {
    
    PENDING(0, "待处理"),
    CONFIRMED(1, "已确认"),
    COMPLETED(2, "已完成"),
    CANCELLED(3, "已取消");
    
    private final Integer code;
    private final String desc;
} 