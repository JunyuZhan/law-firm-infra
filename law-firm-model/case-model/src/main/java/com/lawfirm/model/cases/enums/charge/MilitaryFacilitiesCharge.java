package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 破坏军事设施罪
 */
@Getter
public enum MilitaryFacilitiesCharge implements BaseEnum<String> {
    DAMAGING_MILITARY_EQUIPMENT("破坏武器装备、军事设施、军事通信罪", "070201", "刑法第369条"),
    NEGLIGENT_DAMAGE("过失损坏武器装备、军事设施、军事通信罪", "070202", "刑法第369条第2款"),
    PROVIDING_SUBSTANDARD_EQUIPMENT("故意提供不合格武器装备、军事设施罪", "070203", "刑法第370条第1款"),
    NEGLIGENT_SUBSTANDARD("过失提供不合格武器装备、军事设施罪", "070204", "刑法第370条第2款");

    private final String description;
    private final String code;
    private final String lawArticle;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.MILITARY_FACILITIES;

    MilitaryFacilitiesCharge(String description, String code, String lawArticle) {
        this.description = description;
        this.code = code;
        this.lawArticle = lawArticle;
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