package com.lawfirm.model.client.enums;

/**
 * 客户来源枚举
 */
public enum ClientSourceEnum {
    
    DIRECT(1, "直接访问"),
    RECOMMENDATION(2, "推荐"),
    MARKETING(3, "市场营销"),
    PARTNER(4, "合作伙伴"),
    ONLINE(5, "线上获取"),
    OTHER(99, "其他");

    private final Integer value;
    private final String desc;

    ClientSourceEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static ClientSourceEnum getByValue(Integer value) {
        for (ClientSourceEnum source : values()) {
            if (source.getValue().equals(value)) {
                return source;
            }
        }
        return null;
    }
} 