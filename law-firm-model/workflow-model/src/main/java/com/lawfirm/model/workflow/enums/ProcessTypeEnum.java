package com.lawfirm.model.workflow.enums;

/**
 * 流程类型枚举
 */
public enum ProcessTypeEnum {
    
    CASE(1, "案件流程"),
    CONTRACT(2, "合同流程"),
    DOCUMENT(3, "文档流程"),
    FINANCE(4, "财务流程"),
    APPROVAL(5, "审批流程"),
    CUSTOM(99, "自定义流程");

    private final Integer value;
    private final String desc;

    ProcessTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static ProcessTypeEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (ProcessTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
} 