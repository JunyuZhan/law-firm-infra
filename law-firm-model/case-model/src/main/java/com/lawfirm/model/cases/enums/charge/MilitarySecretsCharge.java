package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 军事秘密罪
 */
@Getter
public enum MilitarySecretsCharge implements BaseEnum<String> {
    ILLEGAL_OBTAINING("非法获取军事秘密罪", "100201"),
    FOREIGN_ESPIONAGE("为境外窃取、剌探、收买、非法提供军事秘密罪", "100202"),
    INTENTIONAL_LEAKING("故意泄露军事秘密罪", "100203"),
    NEGLIGENT_LEAKING("过失泄露军事秘密罪", "100204");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.MILITARY_SECRETS;

    MilitarySecretsCharge(String description, String code) {
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