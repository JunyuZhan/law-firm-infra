package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件类型枚举
 */
@Getter
public enum CaseTypeEnum implements BaseEnum<Integer> {

    /**
     * 民事案件
     */
    CIVIL(1, "民事案件"),

    /**
     * 刑事案件
     */
    CRIMINAL(2, "刑事案件"),

    /**
     * 行政案件
     */
    ADMINISTRATIVE(3, "行政案件"),

    /**
     * 商事仲裁
     */
    COMMERCIAL_ARBITRATION(4, "商事仲裁"),

    /**
     * 劳动仲裁
     */
    LABOR_ARBITRATION(5, "劳动仲裁"),

    /**
     * 非诉讼业务
     */
    NON_LITIGATION(6, "非诉讼业务"),

    /**
     * 执行案件
     */
    ENFORCEMENT(7, "执行案件"),

    /**
     * 破产案件
     */
    BANKRUPTCY(8, "破产案件"),

    /**
     * 国际业务
     */
    INTERNATIONAL(9, "国际业务"),

    /**
     * 知识产权
     */
    INTELLECTUAL_PROPERTY(10, "知识产权");

    private final Integer value;
    private final String description;

    CaseTypeEnum(Integer value, String description) {
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
    public static CaseTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 是否是诉讼案件
     */
    public boolean isLitigation() {
        return this == CIVIL || this == CRIMINAL || this == ADMINISTRATIVE;
    }

    /**
     * 是否是仲裁案件
     */
    public boolean isArbitration() {
        return this == COMMERCIAL_ARBITRATION || this == LABOR_ARBITRATION;
    }

    /**
     * 是否需要法院信息
     */
    public boolean needCourtInfo() {
        return isLitigation() || this == ENFORCEMENT;
    }

    /**
     * 是否需要对方当事人信息
     */
    public boolean needOpposingParty() {
        return this != NON_LITIGATION;
    }

    /**
     * 是否需要特殊资质
     */
    public boolean needSpecialQualification() {
        return this == CRIMINAL || this == BANKRUPTCY || this == INTERNATIONAL;
    }

    /**
     * 是否需要风险评估
     */
    public boolean needRiskAssessment() {
        return this != NON_LITIGATION;
    }
} 