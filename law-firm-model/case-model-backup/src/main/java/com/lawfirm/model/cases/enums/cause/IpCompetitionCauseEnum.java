package com.lawfirm.model.cases.enums.cause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 知识产权与竞争纠纷案由
 */
@Getter
public enum IpCompetitionCauseEnum implements BaseEnum<String> {
    
    // 知识产权合同纠纷
    COPYRIGHT_CONTRACT("著作权合同纠纷"),
    TRADEMARK_CONTRACT("商标合同纠纷"),
    PATENT_CONTRACT("专利合同纠纷"),
    PLANT_VARIETY_CONTRACT("植物新品种合同纠纷"),
    IC_LAYOUT_CONTRACT("集成电路布图设计合同纠纷"),
    TRADE_SECRET_CONTRACT("商业秘密合同纠纷"),
    TECHNOLOGY_CONTRACT("技术合同纠纷"),
    FRANCHISE_CONTRACT("特许经营合同纠纷"),
    COMPANY_NAME_CONTRACT("企业名称（商号）合同纠纷"),
    SPECIAL_SYMBOL_CONTRACT("特殊标志合同纠纷"),
    DOMAIN_NAME_CONTRACT("网络域名合同纠纷"),
    IP_PLEDGE_CONTRACT("知识产权质押合同纠纷"),
    
    // 知识产权权属、侵权纠纷
    COPYRIGHT_OWNERSHIP("著作权权属、侵权纠纷"),
    TRADEMARK_OWNERSHIP("商标权权属、侵权纠纷"),
    PATENT_OWNERSHIP("专利权权属、侵权纠纷"),
    PLANT_VARIETY_OWNERSHIP("植物新品种权权属、侵权纠纷"),
    IC_LAYOUT_OWNERSHIP("集成电路布图设计专有权权属、侵权纠纷"),
    COMPANY_NAME_INFRINGEMENT("侵害企业名称（商号）权纠纷"),
    SPECIAL_SYMBOL_INFRINGEMENT("侵害特殊标志专有权纠纷"),
    DOMAIN_NAME_OWNERSHIP("网络域名权属、侵权纠纷"),
    DISCOVERY_RIGHT("发现权纠纷"),
    INVENTION_RIGHT("发明权纠纷"),
    OTHER_TECH_RIGHT("其他科技成果权纠纷"),
    NON_INFRINGEMENT("确认不侵害知识产权纠纷"),
    TEMPORARY_MEASURE("因申请知识产权临时措施损害责任纠纷"),
    MALICIOUS_LITIGATION("因恶意提起知识产权诉讼损害责任纠纷"),
    PATENT_INVALID("专利权宣告无效后返还费用纠纷"),
    
    // 不正当竞争纠纷
    IMITATION("仿冒纠纷"),
    COMMERCIAL_BRIBERY("商业贿赂不正当竞争纠纷"),
    FALSE_PROPAGANDA("虚假宣传纠纷"),
    TRADE_SECRET_INFRINGEMENT("侵害商业秘密纠纷"),
    DUMPING("低价倾销不正当竞争纠纷"),
    TIED_SALE("捆绑销售不正当竞争纠纷"),
    PRIZE_SALE("有奖销售纠纷"),
    COMMERCIAL_DEFAMATION("商业诋毁纠纷"),
    BID_RIGGING("串通投标不正当竞争纠纷"),
    NETWORK_UNFAIR_COMPETITION("网络不正当竞争纠纷"),
    
    // 垄断纠纷
    MONOPOLY_AGREEMENT("垄断协议纠纷"),
    MARKET_DOMINANCE("滥用市场支配地位纠纷"),
    BUSINESS_CONCENTRATION("经营者集中纠纷");

    private final String description;
    private final CaseCauseEnum parentCause = CaseCauseEnum.IP_COMPETITION;

    IpCompetitionCauseEnum(String description) {
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