package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 妨害对公司、企业的管理秩序罪
 */
@Getter
public enum CompanyManagementCharge implements BaseEnum<String> {
    FALSE_REGISTRATION("虚报注册资本罪", "030301"),
    FALSE_CAPITAL("虚假出资、抽逃出资罪", "030302"),
    FRAUDULENT_SECURITIES("欺诈发行证券罪", "030303"),
    DISCLOSURE_VIOLATION("违规披露、不披露重要信息罪", "030304"),
    OBSTRUCT_LIQUIDATION("妨害清算罪", "030305"),
    CONCEAL_ACCOUNTING("隐匿、故意销毁会计凭证、会计帐簿、财务会计报告罪", "030306"),
    FALSE_BANKRUPTCY("虚假破产罪", "030307"),
    NON_STATE_BRIBERY("非国家工作人员受贿罪", "030308"),
    BRIBE_NON_STATE("对非国家工作人员行贿罪", "030309"),
    BRIBE_FOREIGN_OFFICIALS("对外国公职人员、国际公共组织官员行贿罪", "030310"),
    ILLEGAL_BUSINESS("非法经营同类营业罪", "030311"),
    RELATIVES_PROFIT("为亲友非法牟利罪", "030312"),
    CONTRACT_FRAUD_NEGLIGENCE("签订、履行合同失职被骗罪", "030313"),
    STATE_COMPANY_NEGLIGENCE("国有公司、企业、事业单位人员失职罪", "030314"),
    STATE_COMPANY_ABUSE("国有公司、企业、事业单位人员滥用职权罪", "030315"),
    STATE_ASSETS_SALE("徇私舞弊低价折股、出售国有资产罪", "030316"),
    LISTED_COMPANY_BREACH("背信损害上市公司利益罪", "030317");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.COMPANY_MANAGEMENT;

    CompanyManagementCharge(String description, String code) {
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