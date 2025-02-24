package com.lawfirm.model.cases.enums.reminder;

/**
 * 提醒类型枚举
 */
public enum ReminderTypeEnum {
    DEADLINE("截止日期"),
    MEETING("会议"),
    COURT_HEARING("开庭"),
    DOCUMENT_SUBMISSION("文件提交"),
    PAYMENT("付款"),
    OTHER("其他");

    private final String description;

    ReminderTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 