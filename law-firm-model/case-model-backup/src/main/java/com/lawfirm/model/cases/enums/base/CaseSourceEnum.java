package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件来源枚举
 */
public enum CaseSourceEnum implements BaseEnum<Integer> {
    /**
     * 直接委托
     */
    DIRECT(0, "直接委托"),

    /**
     * 转办
     */
    TRANSFER(1, "转办"),

    /**
     * 法院指派
     */
    COURT_ASSIGNED(2, "法院指派"),

    /**
     * 合作律所推荐
     */
    PARTNER_REFERRAL(3, "合作律所推荐"),

    /**
     * 客户推荐
     */
    CLIENT_REFERRAL(4, "客户推荐"),

    /**
     * 网络咨询
     */
    ONLINE_CONSULTATION(5, "网络咨询"),

    /**
     * 其他
     */
    OTHER(6, "其他");

    private final Integer value;
    private final String description;

    CaseSourceEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static CaseSourceEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseSourceEnum source : values()) {
            if (source.value.equals(value)) {
                return source;
            }
        }
        return null;
    }
} 