package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 战俘罪
 */
@Getter
public enum PrisonerOfWarCharge implements BaseEnum<String> {
    RELEASING_POW("私放俘虏罪", "100501"),
    ABUSING_POW("虐待俘虏罪", "100502");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.PRISONER_OF_WAR;

    PrisonerOfWarCharge(String description, String code) {
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