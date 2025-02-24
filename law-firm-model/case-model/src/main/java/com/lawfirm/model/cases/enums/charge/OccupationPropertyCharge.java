package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 职务侵犯财产罪
 */
@Getter
public enum OccupationPropertyCharge implements BaseEnum<String> {
    MISAPPROPRIATION_OF_FUNDS("职务侵占罪", "050301"),
    MISAPPROPRIATION_OF_SAVINGS("挪用资金罪", "050302"),
    MISAPPROPRIATION_OF_SPECIFIC_FUNDS("挪用特定款物罪", "050303"),
    MISAPPROPRIATION_OF_PUBLIC_FUNDS("挪用公款罪", "050304"),
    MISAPPROPRIATION_OF_RELIEF_FUNDS("挪用救灾、优抚、救济款物罪", "050305");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.OCCUPATION_PROPERTY;

    OccupationPropertyCharge(String description, String code) {
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