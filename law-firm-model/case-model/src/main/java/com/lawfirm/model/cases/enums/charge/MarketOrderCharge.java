package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 扰乱市场秩序罪
 */
@Getter
public enum MarketOrderCharge implements BaseEnum<String> {
    DAMAGE_COMMERCIAL_REPUTATION("损害商业信誉、商品声誉罪", "030801"),
    FALSE_ADVERTISING("虚假广告罪", "030802"),
    BID_RIGGING("串通投标罪", "030803"),
    CONTRACT_FRAUD("合同诈骗罪", "030804"),
    PYRAMID_SCHEME("组织、领导传销活动罪", "030805"),
    ILLEGAL_OPERATION("非法经营罪", "030806"),
    FORCED_TRANSACTION("强迫交易罪", "030807"),
    COUNTERFEIT_TICKETS("伪造、倒卖伪造的有价票证罪", "030808"),
    SCALPING_TICKETS("倒卖车票、船票罪", "030809"),
    ILLEGAL_LAND_TRANSFER("非法转让、倒卖土地使用权罪", "030810"),
    FALSE_CERTIFICATION("提供虚假证明文件罪", "030811"),
    NEGLIGENT_CERTIFICATION("出具证明文件重大失实罪", "030812"),
    EVADE_INSPECTION("逃避商检罪", "030813");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.MARKET_ORDER;

    MarketOrderCharge(String description, String code) {
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