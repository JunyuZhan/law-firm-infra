package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 妨害司法罪
 */
@Getter
public enum ObstructingJusticeCharge implements BaseEnum<String> {
    PERJURY("伪证罪", "060201", "刑法第305条"),
    LAWYER_EVIDENCE_TAMPERING("辩护人、诉讼代理人毁灭证据、伪造证据、妨害作证罪", "060202", "刑法第306条"),
    WITNESS_TAMPERING("妨害作证罪", "060203", "刑法第307条第1款"),
    ASSISTING_EVIDENCE_TAMPERING("帮助毁灭、伪造证据罪", "060204", "刑法第307条第2款"),
    FALSE_LITIGATION("虚假诉讼罪", "060205", "刑法第307条之一"),
    WITNESS_RETALIATION("打击报复证人罪", "060206", "刑法第308条"),
    LEAKING_CASE_INFO("泄露不应公开的案件信息罪", "060207", "刑法第308条之一第1款"),
    DISCLOSING_CASE_INFO("披露、报道不应公开的案件信息罪", "060208", "刑法第308条之一第3款"),
    DISRUPTING_COURT("扰乱法庭秩序罪", "060209", "刑法第309条"),
    HARBORING_CRIMINALS("窝藏、包庇罪", "060210", "刑法第310条"),
    REFUSING_EVIDENCE("拒绝提供间谍犯罪、恐怖主义犯罪、极端主义犯罪证据罪", "060211", "刑法第311条"),
    CONCEALING_CRIMINAL_GAINS("掩饰、隐瞒犯罪所得、犯罪所得收益罪", "060212", "刑法第312条"),
    REFUSING_JUDGMENT("拒不执行判决、裁定罪", "060213", "刑法第313条"),
    ILLEGAL_DISPOSAL_SEIZED("非法处置查封、扣押、冻结的财产罪", "060214", "刑法第314条"),
    DISRUPTING_SUPERVISION("破坏监管秩序罪", "060215", "刑法第315条"),
    ESCAPE("脱逃罪", "060216", "刑法第316条第1款"),
    HIJACKING_PRISONERS("劫夺被押解人员罪", "060217", "刑法第316条第2款"),
    ORGANIZING_PRISON_BREAK("组织越狱罪", "060218", "刑法第317条第1款"),
    VIOLENT_PRISON_BREAK("暴动越狱罪", "060219", "刑法第317条第2款"),
    ARMED_PRISON_BREAK("聚众持械劫狱罪", "060220", "刑法第317条第2款");

    private final String description;
    private final String code;
    private final String lawArticle;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.OBSTRUCTING_JUSTICE;

    ObstructingJusticeCharge(String description, String code, String lawArticle) {
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