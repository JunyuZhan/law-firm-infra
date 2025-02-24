package com.lawfirm.model.message.enums;

/**
 * 消息状态枚举
 */
public enum MessageStatusEnum {
    
    DRAFT(0, "草稿"),
    UNREAD(1, "未读"),
    READ(2, "已读"),
    PROCESSED(3, "已处理"),
    ARCHIVED(4, "已归档"),
    DELETED(5, "已删除");

    private final Integer value;
    private final String desc;

    MessageStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static MessageStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (MessageStatusEnum status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
} 