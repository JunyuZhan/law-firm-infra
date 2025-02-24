package com.lawfirm.model.workflow.enums;

/**
 * 任务类型枚举
 */
public enum TaskTypeEnum {
    
    APPROVAL(1, "审批任务"),
    HANDLE(2, "处理任务"),
    REVIEW(3, "审核任务"),
    SIGN(4, "签字任务"),
    NOTICE(5, "通知任务"),
    COUNTERSIGN(6, "会签任务"),
    CUSTOM(99, "自定义任务");

    private final Integer value;
    private final String desc;

    TaskTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static TaskTypeEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (TaskTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
} 