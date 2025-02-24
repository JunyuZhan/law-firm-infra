package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 滥用职权罪
 */
@Getter
public enum AbuseOfPowerCharge implements BaseEnum<String> {
    ABUSE_OF_POWER("滥用职权罪", "090101"),
    DERELICTION_OF_DUTY("玩忽职守罪", "090102");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.ABUSE_OF_POWER;

    AbuseOfPowerCharge(String description, String code) {
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