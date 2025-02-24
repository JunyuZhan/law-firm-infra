package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 危害国家安全罪具体罪名
 */
@Getter
public enum EndangeringStateSecurityCharge implements BaseEnum<String> {
    BETRAYAL("背叛国家罪", "010101", "102"),
    SPLITTING_COUNTRY("分裂国家罪", "010102", "103-1"),
    INCITING_SPLIT("煽动分裂国家罪", "010103", "103-2"),
    ARMED_REBELLION("武装叛乱、暴乱罪", "010104", "104"),
    SUBVERSION("颠覆国家政权罪", "010105", "105-1"),
    INCITING_SUBVERSION("煽动颠覆国家政权罪", "010106", "105-2"),
    FUNDING_ENDANGERING("资助危害国家安全犯罪活动罪", "010107", "107"),
    DEFECTION_TO_ENEMY("投敌叛变罪", "010108", "108"),
    DEFECTION("叛逃罪", "010109", "109"),
    ESPIONAGE("间谍罪", "010110", "110"),
    STEALING_SECRETS("为境外窃取、刺探、收买、非法提供国家秘密、情报罪", "010111", "111"),
    AIDING_ENEMY("资敌罪", "010112", "112");

    private final String description;
    private final String code;
    private final String lawArticle;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.ENDANGERING_STATE_SECURITY;

    EndangeringStateSecurityCharge(String description, String code, String lawArticle) {
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

    /**
     * 获取法条编号
     */
    public String getLawArticle() {
        return this.lawArticle;
    }
} 