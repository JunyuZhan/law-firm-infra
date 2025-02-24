package com.lawfirm.model.message.enums;

/**
 * 通知渠道枚举
 */
public enum NotifyChannelEnum {
    
    INTERNAL(1, "站内信"),
    SMS(2, "短信"),
    EMAIL(3, "邮件"),
    WECHAT(4, "微信"),
    APP_PUSH(5, "APP推送"),
    WEBHOOK(6, "Webhook"),
    CUSTOM(99, "自定义渠道");

    private final Integer value;
    private final String desc;

    NotifyChannelEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static NotifyChannelEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (NotifyChannelEnum channel : values()) {
            if (channel.getValue().equals(value)) {
                return channel;
            }
        }
        return null;
    }
} 