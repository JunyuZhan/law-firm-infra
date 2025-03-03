package com.lawfirm.model.cases.enums.team;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件参与方类型枚举
 */
@Getter
public enum CaseParticipantTypeEnum implements BaseEnum<Integer> {

    /**
     * 委托人
     */
    CLIENT(1, "委托人"),

    /**
     * 对方当事人
     */
    OPPOSING_PARTY(2, "对方当事人"),

    /**
     * 证人
     */
    WITNESS(3, "证人"),

    /**
     * 法官
     */
    JUDGE(4, "法官"),

    /**
     * 仲裁员
     */
    ARBITRATOR(5, "仲裁员"),

    /**
     * 鉴定人
     */
    EXPERT(6, "鉴定人"),

    /**
     * 调解员
     */
    MEDIATOR(7, "调解员"),

    /**
     * 第三人
     */
    THIRD_PARTY(8, "第三人"),

    /**
     * 代理人
     */
    AGENT(9, "代理人"),

    /**
     * 法定代表人
     */
    LEGAL_REPRESENTATIVE(10, "法定代表人"),

    /**
     * 其他
     */
    OTHER(99, "其他");

    private final Integer value;
    private final String description;

    CaseParticipantTypeEnum(Integer value, String description) {
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
    public static CaseParticipantTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseParticipantTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 是否是我方参与者
     */
    public boolean isOurSide() {
        return this == CLIENT || this == AGENT || this == LEGAL_REPRESENTATIVE;
    }

    /**
     * 是否是对方参与者
     */
    public boolean isOpposingSide() {
        return this == OPPOSING_PARTY;
    }

    /**
     * 是否是中立参与者
     */
    public boolean isNeutral() {
        return this == JUDGE || this == ARBITRATOR || this == MEDIATOR || this == EXPERT;
    }

    /**
     * 是否是关键参与者
     */
    public boolean isKeyParticipant() {
        return this == CLIENT || this == OPPOSING_PARTY || this == JUDGE || 
               this == ARBITRATOR || this == LEGAL_REPRESENTATIVE;
    }

    /**
     * 是否需要联系信息
     */
    public boolean needContactInfo() {
        return this == CLIENT || this == OPPOSING_PARTY || this == WITNESS || 
               this == AGENT || this == THIRD_PARTY;
    }

    /**
     * 是否需要身份证明
     */
    public boolean needIdentification() {
        return this == CLIENT || this == OPPOSING_PARTY || this == WITNESS || 
               this == LEGAL_REPRESENTATIVE;
    }

    /**
     * 是否需要通知案件进展
     */
    public boolean needProgressNotification() {
        return this == CLIENT || this == LEGAL_REPRESENTATIVE;
    }

    /**
     * 获取参与方类型分类
     */
    public String getCategory() {
        if (isOurSide()) {
            return "我方";
        } else if (isOpposingSide()) {
            return "对方";
        } else if (isNeutral()) {
            return "中立方";
        } else {
            return "其他";
        }
    }
} 