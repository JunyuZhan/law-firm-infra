package com.lawfirm.model.cases.enums;

/**
 * 事件类型枚举
 */
public enum EventTypeEnum {
    MEETING("会议"),
    COURT_HEARING("开庭"),
    DEADLINE("截止日期"),
    CLIENT_VISIT("客户拜访"),
    NEGOTIATION("谈判"),
    CONSULTATION("咨询"),
    OTHER("其他");

    private final String description;

    EventTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 