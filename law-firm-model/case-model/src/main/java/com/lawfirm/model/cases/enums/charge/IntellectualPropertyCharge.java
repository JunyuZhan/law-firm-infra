package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 侵犯知识产权罪
 */
@Getter
public enum IntellectualPropertyCharge implements BaseEnum<String> {
    COUNTERFEIT_TRADEMARK("假冒注册商标罪", "030701"),
    SELL_COUNTERFEIT_TRADEMARK("销售假冒注册商标的商品罪", "030702"),
    ILLEGAL_TRADEMARK_PRODUCTION("非法制造、销售非法制造的注册商标标识罪", "030703"),
    COUNTERFEIT_PATENT("假冒专利罪", "030704"),
    COPYRIGHT_INFRINGEMENT("侵犯著作权罪", "030705"),
    SELL_COPYRIGHT_INFRINGEMENT("销售侵权复制品罪", "030706"),
    TRADE_SECRET_INFRINGEMENT("侵犯商业秘密罪", "030707"),
    FOREIGN_TRADE_SECRET("为境外窃取、刺探、收买、非法提供商业秘密罪", "030708");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.INTELLECTUAL_PROPERTY;

    IntellectualPropertyCharge(String description, String code) {
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