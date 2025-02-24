package com.lawfirm.model.cases.enums.reminder;

/**
 * 重复类型枚举
 */
public enum RepeatTypeEnum {
    NONE("不重复"),
    DAILY("每天"),
    WEEKLY("每周"),
    MONTHLY("每月"),
    YEARLY("每年"),
    CUSTOM("自定义");

    private final String description;

    RepeatTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 