package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政渎职罪
 */
@Getter
public enum AdministrativeMisconductCharge implements BaseEnum<String> {
    ILLEGAL_LAND_APPROVAL("非法批准征收、征用、占用土地罪", "090701"),
    ILLEGAL_LAND_TRANSFER("非法低价出让国有土地使用权罪", "090702"),
    ILLEGAL_BORDER_DOCUMENTS("办理偷越国（边）境人员出入境证件罪", "090703"),
    ALLOWING_ILLEGAL_BORDER_CROSSING("放行偷越国（边）境人员罪", "090704"),
    FAILING_TO_RESCUE("不解救被拐卖、绑架妇女、儿童罪", "090705"),
    OBSTRUCTING_RESCUE("阻碍解救被拐卖、绑架妇女、儿童罪", "090706"),
    HELPING_CRIMINALS("帮助犯罪分子逃避处罚罪", "090707"),
    RECRUITMENT_FRAUD("招收公务员、学生徇私舞弊罪", "090708");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.ADMINISTRATIVE_MISCONDUCT;

    AdministrativeMisconductCharge(String description, String code) {
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