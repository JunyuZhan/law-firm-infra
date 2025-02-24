package com.lawfirm.model.client.enums;

/**
 * 联系人类型枚举
 */
public enum ContactTypeEnum {
    
    PRIMARY(1, "主要联系人"),
    LEGAL(2, "法务联系人"),
    FINANCIAL(3, "财务联系人"),
    BUSINESS(4, "业务联系人"),
    OTHER(99, "其他联系人");

    private final Integer value;
    private final String desc;

    ContactTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static ContactTypeEnum getByValue(Integer value) {
        for (ContactTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
} 