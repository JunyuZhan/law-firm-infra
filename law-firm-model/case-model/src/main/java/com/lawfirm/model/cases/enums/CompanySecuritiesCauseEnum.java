package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 公司、证券、保险、票据等纠纷案由
 */
@Getter
public enum CompanySecuritiesCauseEnum implements BaseEnum<String> {
    
    // 与企业有关的纠纷
    ENTERPRISE_INVESTOR("企业出资人权益确认纠纷"),
    ENTERPRISE_INVESTOR_RIGHTS("侵害企业出资人权益纠纷"),
    ENTERPRISE_REFORM("企业公司制改造合同纠纷"),
    ENTERPRISE_SHARE("企业股份合作制改造合同纠纷"),
    ENTERPRISE_DEBT_EQUITY("企业债权转股权合同纠纷"),
    ENTERPRISE_DIVISION("企业分立合同纠纷"),
    ENTERPRISE_LEASE("企业租赁经营合同纠纷"),
    ENTERPRISE_SALE("企业出售合同纠纷"),
    ENTERPRISE_AFFILIATION("挂靠经营合同纠纷"),
    ENTERPRISE_MERGER("企业兼并合同纠纷"),
    JOINT_OPERATION("联营合同纠纷"),
    ENTERPRISE_CONTRACT("企业承包经营合同纠纷"),
    JOINT_VENTURE("中外合资经营企业合同纠纷"),
    COOPERATIVE_VENTURE("中外合作经营企业合同纠纷"),
    
    // 与公司有关的纠纷
    SHAREHOLDER_QUALIFICATION("股东资格确认纠纷"),
    SHAREHOLDER_REGISTER("股东名册记载纠纷"),
    COMPANY_REGISTRATION("请求变更公司登记纠纷"),
    SHAREHOLDER_CONTRIBUTION("股东出资纠纷"),
    CAPITAL_INCREASE("新增资本认购纠纷"),
    SHAREHOLDER_RIGHTS("股东知情权纠纷"),
    SHARE_BUYBACK("请求公司收购股份纠纷"),
    SHARE_TRANSFER("股权转让纠纷"),
    COMPANY_RESOLUTION("公司决议纠纷"),
    COMPANY_ESTABLISHMENT("公司设立纠纷"),
    COMPANY_CERTIFICATE("公司证照返还纠纷"),
    PROMOTER_LIABILITY("发起人责任纠纷"),
    COMPANY_PROFIT("公司盈余分配纠纷"),
    SHAREHOLDER_DAMAGE("损害股东利益责任纠纷"),
    COMPANY_DAMAGE("损害公司利益责任纠纷"),
    CREDITOR_DAMAGE("损害公司债权人利益责任纠纷"),
    RELATED_TRANSACTION("公司关联交易损害责任纠纷"),
    COMPANY_MERGER("公司合并纠纷"),
    COMPANY_DIVISION("公司分立纠纷"),
    CAPITAL_REDUCTION("公司减资纠纷"),
    CAPITAL_INCREASE_DISPUTE("公司增资纠纷"),
    COMPANY_DISSOLUTION("公司解散纠纷"),
    LIQUIDATION("清算责任纠纷"),
    LISTED_COMPANY("上市公司收购纠纷"),
    
    // 合伙企业纠纷
    PARTNERSHIP_ENTRY("入伙纠纷"),
    PARTNERSHIP_EXIT("退伙纠纷"),
    PARTNERSHIP_SHARE("合伙企业财产份额转让纠纷"),
    
    // 与破产有关的纠纷
    BANKRUPTCY_REVOCATION("请求撤销个别清偿行为纠纷"),
    BANKRUPTCY_INVALID("请求确认债务人行为无效纠纷"),
    DEBT_COLLECTION("对外追收债权纠纷"),
    CAPITAL_COLLECTION("追收未缴出资纠纷"),
    CAPITAL_WITHDRAWAL("追收抽逃出资纠纷"),
    ABNORMAL_INCOME("追收非正常收入纠纷"),
    BANKRUPTCY_CLAIM("破产债权确认纠纷"),
    RECOVERY_RIGHT("取回权纠纷"),
    BANKRUPTCY_OFFSET("破产抵销权纠纷"),
    SEPARATE_RIGHT("别除权纠纷"),
    BANKRUPTCY_REVOKE("破产撤销权纠纷"),
    DEBTOR_DAMAGE("损害债务人利益赔偿纠纷"),
    ADMINISTRATOR_LIABILITY("管理人责任纠纷"),
    
    // 证券纠纷
    SECURITIES_RIGHTS("证券权利确认纠纷"),
    SECURITIES_TRADING("证券交易合同纠纷"),
    DERIVATIVE_TRADING("金融衍生品种交易纠纷"),
    SECURITIES_UNDERWRITING("证券承销合同纠纷"),
    SECURITIES_CONSULTING("证券投资咨询纠纷"),
    SECURITIES_RATING("证券资信评级服务合同纠纷"),
    SECURITIES_REPURCHASE("证券回购合同纠纷"),
    SECURITIES_LISTING("证券上市合同纠纷"),
    SECURITIES_AGENCY("证券交易代理合同纠纷"),
    SECURITIES_SPONSOR("证券上市保荐合同纠纷"),
    SECURITIES_ISSUANCE("证券发行纠纷"),
    SECURITIES_RETURN("证券返还纠纷"),
    SECURITIES_FRAUD("证券欺诈责任纠纷"),
    SECURITIES_CUSTODY("证券托管纠纷"),
    SECURITIES_SETTLEMENT("证券登记、存管、结算纠纷"),
    MARGIN_TRADING("融资融券交易纠纷"),
    SETTLEMENT_FUND("客户交易结算资金纠纷"),
    
    // 期货交易纠纷
    FUTURES_BROKER("期货经纪合同纠纷"),
    FUTURES_OVERDRAFT("期货透支交易纠纷"),
    FUTURES_LIQUIDATION("期货强行平仓纠纷"),
    FUTURES_DELIVERY("期货实物交割纠纷"),
    FUTURES_GUARANTEE("期货保证合约纠纷"),
    FUTURES_AGENCY("期货交易代理合同纠纷"),
    FUTURES_MARGIN("侵占期货交易保证金纠纷"),
    FUTURES_FRAUD("期货欺诈责任纠纷"),
    FUTURES_MANIPULATION("操纵期货交易市场责任纠纷"),
    FUTURES_INSIDER("期货内幕交易责任纠纷"),
    FUTURES_FALSE_INFO("期货虚假信息责任纠纷"),
    
    // 信托纠纷
    CIVIL_TRUST("民事信托纠纷"),
    BUSINESS_TRUST("营业信托纠纷"),
    PUBLIC_TRUST("公益信托纠纷"),
    
    // 保险纠纷
    PROPERTY_INSURANCE("财产保险合同纠纷"),
    PERSONAL_INSURANCE("人身保险合同纠纷"),
    REINSURANCE("再保险合同纠纷"),
    INSURANCE_BROKER("保险经纪合同纠纷"),
    INSURANCE_AGENT("保险代理合同纠纷"),
    EXPORT_CREDIT("进出口信用保险合同纠纷"),
    INSURANCE_PREMIUM("保险费纠纷"),
    
    // 票据纠纷
    BILL_PAYMENT("票据付款请求权纠纷"),
    BILL_RECOURSE("票据追索权纠纷"),
    BILL_DELIVERY("票据交付请求权纠纷"),
    BILL_RETURN("票据返还请求权纠纷"),
    BILL_DAMAGE("票据损害责任纠纷"),
    BILL_INTEREST("票据利益返还请求权纠纷"),
    BILL_RECEIPT("汇票回单签发请求权纠纷"),
    BILL_GUARANTEE("票据保证纠纷"),
    BILL_INVALID("确认票据无效纠纷"),
    BILL_AGENCY("票据代理纠纷"),
    BILL_REPURCHASE("票据回购纠纷"),
    
    // 信用证纠纷
    LC_OPENING("委托开立信用证纠纷"),
    LC_ISSUANCE("信用证开证纠纷"),
    LC_NEGOTIATION("信用证议付纠纷"),
    LC_FRAUD("信用证欺诈纠纷"),
    LC_FINANCING("信用证融资纠纷"),
    LC_TRANSFER("信用证转让纠纷"),
    
    // 独立保函纠纷
    GUARANTEE_ISSUANCE("独立保函开立纠纷"),
    GUARANTEE_PAYMENT("独立保函付款纠纷"),
    GUARANTEE_RECOURSE("独立保函追偿纠纷"),
    GUARANTEE_FRAUD("独立保函欺诈纠纷"),
    GUARANTEE_TRANSFER("独立保函转让纠纷"),
    GUARANTEE_NOTICE("独立保函通知纠纷"),
    GUARANTEE_REVOCATION("独立保函撤销纠纷");

    private final String description;
    private final CaseCauseEnum parentCause = CaseCauseEnum.COMPANY_SECURITIES;

    CompanySecuritiesCauseEnum(String description) {
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 