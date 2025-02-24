package com.lawfirm.model.cases.enums.event;

/**
 * 事件状态枚举
 */
public enum EventStatusEnum {
    SCHEDULED("已安排"),
    IN_PROGRESS("进行中"),
    COMPLETED("已完成"),
    CANCELLED("已取消"),
    POSTPONED("已推迟");

    private final String description;

    EventStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 