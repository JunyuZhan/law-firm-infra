package com.lawfirm.model.cases.enums;

/**
 * 任务优先级枚举
 */
public enum TaskPriorityEnum {
    LOW("低"),
    MEDIUM("中"),
    HIGH("高"),
    URGENT("紧急");

    private final String description;

    TaskPriorityEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 