package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 贪污罪
 */
@Getter
public enum EmbezzlementCharge implements BaseEnum<String> {
    EMBEZZLEMENT("贪污罪", "080101"),
    MISAPPROPRIATION("挪用公款罪", "080102");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.EMBEZZLEMENT;

    EmbezzlementCharge(String description, String code) {
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