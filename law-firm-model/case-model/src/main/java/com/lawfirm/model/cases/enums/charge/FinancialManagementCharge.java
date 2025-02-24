package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 破坏金融管理秩序罪
 */
@Getter
public enum FinancialManagementCharge implements BaseEnum<String> {
    COUNTERFEIT_CURRENCY("伪造货币罪", "030401"),
    SELL_COUNTERFEIT_CURRENCY("出售、购买、运输假币罪", "030402"),
    FINANCIAL_STAFF_COUNTERFEIT("金融工作人员购买假币、以假币换取货币罪", "030403"),
    HOLD_COUNTERFEIT_CURRENCY("持有、使用假币罪", "030404"),
    ALTER_CURRENCY("变造货币罪", "030405"),
    ILLEGAL_FINANCIAL_INSTITUTION("擅自设立金融机构罪", "030406"),
    FORGE_FINANCIAL_DOCUMENTS("伪造、变造、转让金融机构经营许可证、批准文件罪", "030407"),
    HIGH_INTEREST_LOAN("高利转贷罪", "030408"),
    LOAN_FRAUD("骗取贷款、票据承兑、金融票证罪", "030409"),
    ILLEGAL_DEPOSIT("非法吸收公众存款罪", "030410"),
    FORGE_FINANCIAL_BILLS("伪造、变造金融票证罪", "030411"),
    CREDIT_CARD_FRAUD("妨害信用卡管理罪", "030412"),
    STEAL_CREDIT_CARD_INFO("窃取、收买、非法提供信用卡信息罪", "030413"),
    FORGE_STATE_SECURITIES("伪造、变造国家有价证券罪", "030414"),
    FORGE_STOCKS("伪造、变造股票、公司、企业债券罪", "030415"),
    ILLEGAL_STOCKS("擅自发行股票、公司、企业债券罪", "030416"),
    INSIDER_TRADING("内幕交易、泄露内幕信息罪", "030417"),
    USE_INSIDE_INFO("利用未公开信息交易罪", "030418"),
    SECURITIES_FRAUD("编造并传播证券、期货交易虚假信息罪", "030419"),
    SECURITIES_INVESTMENT_FRAUD("诱骗投资者买卖证券、期货合约罪", "030420"),
    MARKET_MANIPULATION("操纵证券、期货市场罪", "030421"),
    TRUST_FUND_BREACH("背信运用受托财产罪", "030422"),
    ILLEGAL_FUND_USE("违法运用资金罪", "030423"),
    ILLEGAL_LOANS("违法发放贷款罪", "030424"),
    OFF_BOOK_DEPOSITS("吸收客户资金不入账罪", "030425"),
    ILLEGAL_FINANCIAL_CERTIFICATES("违规出具金融票证罪", "030426"),
    ILLEGAL_BILL_ACCEPTANCE("对违法票据承兑、付款、保证罪", "030427"),
    FRAUDULENT_FOREX_PURCHASE("骗购外汇罪", "030428"),
    FOREX_EVASION("逃汇罪", "030429"),
    MONEY_LAUNDERING("洗钱罪", "030430");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.FINANCIAL_MANAGEMENT;

    FinancialManagementCharge(String description, String code) {
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