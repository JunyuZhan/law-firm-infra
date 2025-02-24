package com.lawfirm.model.workflow.enums;

/**
 * 流程状态枚举
 */
public enum ProcessStatusEnum {
    
    DRAFT(0, "草稿"),
    PENDING(1, "待处理"),
    PROCESSING(2, "处理中"),
    SUSPENDED(3, "已暂停"),
    COMPLETED(4, "已完成"),
    TERMINATED(5, "已终止"),
    CANCELLED(6, "已取消");

    private final Integer value;
    private final String desc;

    ProcessStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static ProcessStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (ProcessStatusEnum status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
} 