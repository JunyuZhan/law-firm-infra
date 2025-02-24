package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 侵犯人身自由权利罪
 */
@Getter
public enum PersonalFreedomCharge implements BaseEnum<String> {
    RAPE("强奸罪", "040201", "刑法第236条"),
    CAREGIVER_SEXUAL_ASSAULT("负有照护职责人员性侵罪", "040202", "刑法第236条之一"),
    FORCED_MOLESTATION("强制猥亵、侮辱罪", "040203", "刑法第237条第1款"),
    CHILD_MOLESTATION("猥亵儿童罪", "040204", "刑法第237条第3款"),
    ILLEGAL_DETENTION("非法拘禁罪", "040205", "刑法第238条"),
    KIDNAPPING("绑架罪", "040206", "刑法第239条"),
    TRAFFICKING("拐卖妇女、儿童罪", "040207", "刑法第240条"),
    BUYING_TRAFFICKED("收买被拐卖的妇女、儿童罪", "040208", "刑法第241条第1款"),
    OBSTRUCT_RESCUE("聚众阻碍解救被收买的妇女、儿童罪", "040209", "刑法第242条第2款"),
    FORCED_LABOR("强迫劳动罪", "040210", "刑法第244条"),
    CHILD_LABOR("雇用童工从事危重劳动罪", "040211", "刑法第244条之一"),
    ILLEGAL_SEARCH("非法搜查罪", "040212", "刑法第245条"),
    ILLEGAL_HOME_INVASION("非法侵入住宅罪", "040213", "刑法第245条");

    private final String description;
    private final String code;
    private final String lawArticle;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.PERSONAL_FREEDOM;

    PersonalFreedomCharge(String description, String code, String lawArticle) {
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