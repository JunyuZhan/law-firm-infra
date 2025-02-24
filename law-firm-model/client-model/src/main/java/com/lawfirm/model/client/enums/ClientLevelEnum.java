package com.lawfirm.model.client.enums;

/**
 * 客户等级枚举
 */
public enum ClientLevelEnum {
    
    NORMAL(1, "普通客户"),
    VIP(2, "VIP客户"),
    PREMIUM(3, "高级VIP客户"),
    STRATEGIC(4, "战略客户");

    private final Integer value;
    private final String desc;

    ClientLevelEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static ClientLevelEnum getByValue(Integer value) {
        for (ClientLevelEnum level : values()) {
            if (level.getValue().equals(value)) {
                return level;
            }
        }
        return null;
    }
} 