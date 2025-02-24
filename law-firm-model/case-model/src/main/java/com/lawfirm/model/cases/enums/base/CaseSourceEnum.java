package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件来源枚举
 */
public enum CaseSourceEnum implements BaseEnum<Integer> {

    /**
     * 直接委托
     */
    DIRECT_COMMISSION(1, "直接委托"),

    /**
     * 转办案件
     */
    TRANSFER(2, "转办案件"),

    /**
     * 法院指派
     */
    COURT_ASSIGNED(3, "法院指派"),

    /**
     * 政府指派
     */
    GOVERNMENT_ASSIGNED(4, "政府指派"),

    /**
     * 合作律所推荐
     */
    PARTNER_REFERRAL(5, "合作律所推荐"),

    /**
     * 客户推荐
     */
    CLIENT_REFERRAL(6, "客户推荐"),

    /**
     * 网络咨询
     */
    ONLINE_CONSULTATION(7, "网络咨询"),

    /**
     * 电话咨询
     */
    PHONE_CONSULTATION(8, "电话咨询"),

    /**
     * 律所活动
     */
    FIRM_EVENT(9, "律所活动"),

    /**
     * 其他来源
     */
    OTHER(10, "其他来源");

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

    /**
     * 根据值获取枚举
     */
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

    /**
     * 是否是主动获取
     */
    public boolean isActiveAcquisition() {
        return this == DIRECT_COMMISSION || this == PARTNER_REFERRAL || 
               this == CLIENT_REFERRAL || this == FIRM_EVENT;
    }

    /**
     * 是否是被动获取
     */
    public boolean isPassiveAcquisition() {
        return this == COURT_ASSIGNED || this == GOVERNMENT_ASSIGNED;
    }

    /**
     * 是否是咨询转化
     */
    public boolean isConsultationConversion() {
        return this == ONLINE_CONSULTATION || this == PHONE_CONSULTATION;
    }

    /**
     * 是否需要回访
     */
    public boolean needFollowUp() {
        return this == ONLINE_CONSULTATION || this == PHONE_CONSULTATION || 
               this == CLIENT_REFERRAL;
    }

    /**
     * 是否需要特别感谢
     */
    public boolean needSpecialThanks() {
        return this == PARTNER_REFERRAL || this == CLIENT_REFERRAL;
    }
} 