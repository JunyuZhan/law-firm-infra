package com.lawfirm.model.cases.enums.team;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件参与方类型枚举
 */
@Getter
public enum ParticipantTypeEnum implements BaseEnum<Integer> {

    /**
     * 委托人
     */
    CLIENT(1, "委托人"),

    /**
     * 对方当事人
     */
    OPPOSING_PARTY(2, "对方当事人"),

    /**
     * 第三人
     */
    THIRD_PARTY(3, "第三人"),

    /**
     * 证人
     */
    WITNESS(4, "证人"),

    /**
     * 鉴定人
     */
    EXPERT(5, "鉴定人"),

    /**
     * 翻译人员
     */
    TRANSLATOR(6, "翻译人员"),

    /**
     * 代理人
     */
    ATTORNEY(7, "代理人"),

    /**
     * 法定代表人
     */
    LEGAL_REPRESENTATIVE(8, "法定代表人"),

    /**
     * 其他参与人
     */
    OTHER(9, "其他参与人");

    private final Integer value;
    private final String description;

    ParticipantTypeEnum(Integer value, String description) {
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
    public static ParticipantTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (ParticipantTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 是否是主要参与方
     */
    public boolean isPrimaryParticipant() {
        return this == CLIENT || this == OPPOSING_PARTY || this == THIRD_PARTY;
    }

    /**
     * 是否需要身份验证
     */
    public boolean needIdentityVerification() {
        return this == CLIENT || this == OPPOSING_PARTY || this == LEGAL_REPRESENTATIVE;
    }

    /**
     * 是否需要保密协议
     */
    public boolean needConfidentialityAgreement() {
        return this == EXPERT || this == TRANSLATOR;
    }
} 