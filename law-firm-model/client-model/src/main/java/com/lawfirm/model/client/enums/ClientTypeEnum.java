package com.lawfirm.model.client.enums;

/**
 * 客户类型枚举
 */
public enum ClientTypeEnum {
    
    PERSONAL(1, "个人"),
    ENTERPRISE(2, "企业");

    private final Integer value;
    private final String desc;

    ClientTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static ClientTypeEnum getByValue(Integer value) {
        for (ClientTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
} 