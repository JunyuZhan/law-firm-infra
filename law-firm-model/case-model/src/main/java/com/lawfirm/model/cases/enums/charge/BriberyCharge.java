package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 受贿罪
 */
@Getter
public enum BriberyCharge implements BaseEnum<String> {
    ACCEPTING_BRIBES("受贿罪", "080201"),
    UNIT_ACCEPTING_BRIBES("单位受贿罪", "080202"),
    INFLUENCE_ACCEPTING_BRIBES("利用影响力受贿罪", "080203"),
    OFFERING_BRIBES("行贿罪", "080204"),
    INFLUENCE_OFFERING_BRIBES("对有影响力的人行贿罪", "080205"),
    OFFERING_BRIBES_TO_UNIT("对单位行贿罪", "080206"),
    INTRODUCING_BRIBES("介绍贿赂罪", "080207"),
    UNIT_OFFERING_BRIBES("单位行贿罪", "080208");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.BRIBERY;

    BriberyCharge(String description, String code) {
        this.description = description;
        this.code = code;
    }

    @Override
    public String getValue() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 