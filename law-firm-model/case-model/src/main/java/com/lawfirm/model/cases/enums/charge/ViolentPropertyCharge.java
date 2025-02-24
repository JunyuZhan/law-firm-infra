package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 暴力侵犯财产罪
 */
@Getter
public enum ViolentPropertyCharge implements BaseEnum<String> {
    ROBBERY("抢劫罪", "050101"),
    FORCIBLE_SEIZURE("抢夺罪", "050102"),
    MOB_ROBBERY("聚众哄抢罪", "050103"),
    EXTORTION("敲诈勒索罪", "050104");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.VIOLENT_PROPERTY;

    ViolentPropertyCharge(String description, String code) {
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