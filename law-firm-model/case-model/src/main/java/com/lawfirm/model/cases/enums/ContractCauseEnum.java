package com.lawfirm.model.cases.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 合同、准合同纠纷案由
 */
@Getter
public enum ContractCauseEnum implements BaseEnum<String> {
    
    // 合同纠纷
    CONTRACT_FORMATION("缔约过失责任纠纷"),
    PRELIMINARY_CONTRACT("预约合同纠纷"),
    CONTRACT_VALIDITY("确认合同效力纠纷"),
    CREDITOR_SUBROGATION("债权人代位权纠纷"),
    CREDITOR_REVOCATION("债权人撤销权纠纷"),
    CREDIT_ASSIGNMENT("债权转让合同纠纷"),
    DEBT_TRANSFER("债务转移合同纠纷"),
    DEBT_CREDIT_TRANSFER("债权债务概括转移合同纠纷"),
    DEBT_ADDITION("债务加入纠纷"),
    REWARD_AD("悬赏广告纠纷"),
    
    // 买卖合同纠纷
    SALE_CONTRACT("买卖合同纠纷"),
    AUCTION_CONTRACT("拍卖合同纠纷"),
    CONSTRUCTION_LAND_USE("建设用地使用权合同纠纷"),
    TEMPORARY_LAND_USE("临时用地合同纠纷"),
    MINING_EXPLORATION_TRANSFER("探矿权转让合同纠纷"),
    MINING_RIGHT_TRANSFER("采矿权转让合同纠纷"),
    
    // 房地产合同纠纷
    REAL_ESTATE_DEVELOPMENT("房地产开发经营合同纠纷"),
    HOUSE_SALE("房屋买卖合同纠纷"),
    HOUSE_DEMOLITION("民事主体间房屋拆迁补偿合同纠纷"),
    
    // 供用合同纠纷
    POWER_SUPPLY("供用电合同纠纷"),
    WATER_SUPPLY("供用水合同纠纷"),
    GAS_SUPPLY("供用气合同纠纷"),
    HEAT_SUPPLY("供用热力合同纠纷"),
    
    // 赠与合同纠纷
    GIFT_CONTRACT("赠与合同纠纷"),
    
    // 借款合同纠纷
    LOAN_CONTRACT("借款合同纠纷"),
    
    // 租赁合同纠纷
    LEASE_CONTRACT("租赁合同纠纷"),
    FINANCIAL_LEASE("融资租赁合同纠纷"),
    FACTORING("保理合同纠纷"),
    
    // 承揽合同纠纷
    CONTRACTOR("承揽合同纠纷"),
    CONSTRUCTION("建设工程合同纠纷"),
    
    // 运输合同纠纷
    TRANSPORT_CONTRACT("运输合同纠纷"),
    
    // 技术合同纠纷
    TECHNOLOGY_CONTRACT("技术合同纠纷"),
    
    // 保管合同纠纷
    STORAGE_CONTRACT("保管合同纠纷"),
    WAREHOUSE_CONTRACT("仓储合同纠纷"),
    
    // 委托合同纠纷
    COMMISSION_CONTRACT("委托合同纠纷"),
    FINANCIAL_MANAGEMENT("委托理财合同纠纷"),
    
    // 服务合同纠纷
    SERVICE_CONTRACT("服务合同纠纷"),
    
    // 不当得利纠纷
    UNJUST_ENRICHMENT("不当得利纠纷"),
    
    // 无因管理纠纷
    NEGOTIORUM_GESTIO("无因管理纠纷");

    private final String description;
    private final CaseCauseEnum parentCause = CaseCauseEnum.CONTRACT;

    ContractCauseEnum(String description) {
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