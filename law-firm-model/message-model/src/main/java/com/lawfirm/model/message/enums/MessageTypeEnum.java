package com.lawfirm.model.message.enums;

/**
 * 消息类型枚举
 */
public enum MessageTypeEnum {
    
    SYSTEM(1, "系统消息"),
    CASE(2, "案件消息"),
    CONTRACT(3, "合同消息"),
    DOCUMENT(4, "文档消息"),
    WORKFLOW(5, "工作流消息"),
    NOTICE(6, "通知消息"),
    CUSTOM(99, "自定义消息");

    private final Integer value;
    private final String desc;

    MessageTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static MessageTypeEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (MessageTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
} 