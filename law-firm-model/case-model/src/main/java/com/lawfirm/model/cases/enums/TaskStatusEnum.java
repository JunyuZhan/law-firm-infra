package com.lawfirm.model.cases.enums;

/**
 * 任务状态枚举
 */
public enum TaskStatusEnum {
    NOT_STARTED("未开始"),
    IN_PROGRESS("进行中"),
    PENDING("待处理"),
    COMPLETED("已完成"),
    CANCELLED("已取消");

    private final String description;

    TaskStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 