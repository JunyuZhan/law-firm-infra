package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 侵犯人格尊严罪
 */
@Getter
public enum PersonalDignityCharge implements BaseEnum<String> {
    DEFAMATION("诽谤罪", "040301"),
    INSULT("侮辱罪", "040302"),
    PRIVACY_VIOLATION("侵犯公民个人信息罪", "040303"),
    PRIVACY_SALE("出售、非法提供公民个人信息罪", "040304"),
    PRIVACY_THEFT("窃取公民个人信息罪", "040305");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.PERSONAL_DIGNITY;

    PersonalDignityCharge(String description, String code) {
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