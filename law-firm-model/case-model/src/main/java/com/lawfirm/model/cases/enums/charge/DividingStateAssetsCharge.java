package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 私分国有资产罪
 */
@Getter
public enum DividingStateAssetsCharge implements BaseEnum<String> {
    DIVIDING_STATE_ASSETS("私分国有资产罪", "080401"),
    DIVIDING_CONFISCATED_ASSETS("私分罚没财物罪", "080402");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.DIVIDING_STATE_ASSETS;

    DividingStateAssetsCharge(String description, String code) {
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