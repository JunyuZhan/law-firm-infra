package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 侵犯民主权利罪
 */
@Getter
public enum DemocraticRightsCharge implements BaseEnum<String> {
    ELECTION_INTERFERENCE("破坏选举罪", "040401"),
    MARRIAGE_INTERFERENCE("暴力干涉婚姻自由罪", "040402"),
    BIGAMY("重婚罪", "040403"),
    MILITARY_MARRIAGE("破坏军婚罪", "040404");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.DEMOCRATIC_RIGHTS;

    DemocraticRightsCharge(String description, String code) {
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