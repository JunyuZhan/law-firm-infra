package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 妨害国（边）境管理罪
 */
@Getter
public enum BorderControlCharge implements BaseEnum<String> {
    ORGANIZING_BORDER_CROSSING("组织他人偷越国（边）境罪", "060301"),
    FRAUDULENT_DOCUMENTS("骗取出境证件罪", "060302"),
    PROVIDING_FALSE_DOCUMENTS("提供伪造、变造的出入境证件罪", "060303"),
    SELLING_DOCUMENTS("出售出入境证件罪", "060304"),
    TRANSPORTING_ILLEGAL_CROSSING("运送他人偷越国（边）境罪", "060305"),
    ILLEGAL_BORDER_CROSSING("偷越国（边）境罪", "060306"),
    DAMAGING_BORDER_MARKERS("破坏界碑、界桩罪", "060307"),
    DAMAGING_SURVEY_MARKERS("破坏永久性测量标志罪", "060308");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.BORDER_CONTROL;

    BorderControlCharge(String description, String code) {
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