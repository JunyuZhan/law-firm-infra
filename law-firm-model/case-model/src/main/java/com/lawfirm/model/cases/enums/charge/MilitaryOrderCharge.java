package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 破坏军事管理秩序罪
 */
@Getter
public enum MilitaryOrderCharge implements BaseEnum<String> {
    IMPERSONATING_MILITARY("冒充军人招摇撞骗罪", "070101"),
    FORGING_MILITARY_DOCUMENTS("伪造、变造、买卖武装部队公文、证件、印章罪", "070102"),
    STEALING_MILITARY_DOCUMENTS("盗窃、抢夺武装部队公文、证件、印章罪", "070103"),
    ILLEGAL_MILITARY_UNIFORMS("非法生产、买卖军用标志罪", "070104"),
    ILLEGAL_MILITARY_EQUIPMENT("非法生产、买卖军用装备罪", "070105"),
    ILLEGAL_MILITARY_SUPPLIES("聚众冲击军事禁区罪", "070106"),
    DISRUPTING_MILITARY_OPERATIONS("聚众扰乱军事管理区秩序罪", "070107"),
    MILITARY_SECRETS("冒充军人招摇撞骗罪", "070108"),
    MILITARY_FACILITIES("破坏军事设施罪", "070109"),
    MILITARY_COMMUNICATIONS("破坏军事通信罪", "070110");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.MILITARY_ORDER;

    MilitaryOrderCharge(String description, String code) {
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