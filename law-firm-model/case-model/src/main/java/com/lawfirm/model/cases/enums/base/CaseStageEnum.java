package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件阶段枚举
 */
@Getter
public enum CaseStageEnum implements BaseEnum<Integer> {

    /**
     * 立案前
     */
    PRE_FILING(1, "立案前"),

    /**
     * 一审
     */
    FIRST_INSTANCE(2, "一审"),

    /**
     * 二审
     */
    SECOND_INSTANCE(3, "二审"),

    /**
     * 再审
     */
    RETRIAL(4, "再审"),

    /**
     * 执行
     */
    ENFORCEMENT(5, "执行"),

    /**
     * 仲裁
     */
    ARBITRATION(6, "仲裁"),

    /**
     * 调解
     */
    MEDIATION(7, "调解"),

    /**
     * 和解
     */
    SETTLEMENT(8, "和解"),

    /**
     * 非诉
     */
    NON_LITIGATION(9, "非诉");

    private final Integer value;
    private final String description;

    CaseStageEnum(Integer value, String description) {
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
    public static CaseStageEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseStageEnum stage : values()) {
            if (stage.value.equals(value)) {
                return stage;
            }
        }
        return null;
    }

    /**
     * 是否是诉讼阶段
     */
    public boolean isLitigationStage() {
        return this == FIRST_INSTANCE || this == SECOND_INSTANCE || this == RETRIAL;
    }

    /**
     * 是否是替代性纠纷解决阶段
     */
    public boolean isAdrStage() {
        return this == ARBITRATION || this == MEDIATION || this == SETTLEMENT;
    }

    /**
     * 是否需要法院信息
     */
    public boolean needCourtInfo() {
        return isLitigationStage() || this == ENFORCEMENT;
    }

    /**
     * 是否需要仲裁机构信息
     */
    public boolean needArbitrationInfo() {
        return this == ARBITRATION;
    }

    /**
     * 是否需要调解机构信息
     */
    public boolean needMediationInfo() {
        return this == MEDIATION;
    }

    /**
     * 是否需要执行机构信息
     */
    public boolean needEnforcementInfo() {
        return this == ENFORCEMENT;
    }

    /**
     * 获取下一个可能的阶段
     */
    public CaseStageEnum[] getNextPossibleStages() {
        switch (this) {
            case PRE_FILING:
                return new CaseStageEnum[]{FIRST_INSTANCE, ARBITRATION, MEDIATION, NON_LITIGATION};
            case FIRST_INSTANCE:
                return new CaseStageEnum[]{SECOND_INSTANCE, ENFORCEMENT, SETTLEMENT};
            case SECOND_INSTANCE:
                return new CaseStageEnum[]{RETRIAL, ENFORCEMENT, SETTLEMENT};
            case RETRIAL:
                return new CaseStageEnum[]{ENFORCEMENT, SETTLEMENT};
            case ARBITRATION:
                return new CaseStageEnum[]{ENFORCEMENT, SETTLEMENT};
            case MEDIATION:
                return new CaseStageEnum[]{FIRST_INSTANCE, ARBITRATION, SETTLEMENT};
            case ENFORCEMENT:
                return new CaseStageEnum[]{};
            case SETTLEMENT:
                return new CaseStageEnum[]{};
            case NON_LITIGATION:
                return new CaseStageEnum[]{};
            default:
                return new CaseStageEnum[]{};
        }
    }
} 