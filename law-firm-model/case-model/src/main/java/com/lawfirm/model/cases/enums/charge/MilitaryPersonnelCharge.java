package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 军人人身罪
 */
@Getter
public enum MilitaryPersonnelCharge implements BaseEnum<String> {
    ABUSE_SUBORDINATES("虐待部属罪", "100401"),
    ABANDONING_WOUNDED("遗弃伤病军人罪", "100402"),
    REFUSING_TREATMENT("战时拒不救治伤病军人罪", "100403"),
    HARMING_CIVILIANS("战时残害居民、掠夺居民财物罪", "100404");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.MILITARY_PERSONNEL;

    MilitaryPersonnelCharge(String description, String code) {
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