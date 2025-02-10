package com.lawfirm.model.cases.enums;

/**
 * 时间线事件类型枚举
 */
public enum TimelineEventTypeEnum {
    CASE_CREATED("案件创建"),
    DOCUMENT_ADDED("文件添加"),
    STATUS_CHANGED("状态变更"),
    TEAM_UPDATED("团队更新"),
    TASK_COMPLETED("任务完成"),
    NOTE_ADDED("备注添加"),
    REMINDER_SET("提醒设置"),
    OTHER("其他");

    private final String description;

    TimelineEventTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 