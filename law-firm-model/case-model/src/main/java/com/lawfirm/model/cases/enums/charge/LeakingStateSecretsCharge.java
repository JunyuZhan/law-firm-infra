package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 泄露国家秘密罪
 */
@Getter
public enum LeakingStateSecretsCharge implements BaseEnum<String> {
    INTENTIONAL_LEAKING("故意泄露国家秘密罪", "090301"),
    NEGLIGENT_LEAKING("过失泄露国家秘密罪", "090302");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.LEAKING_STATE_SECRETS;

    LeakingStateSecretsCharge(String description, String code) {
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