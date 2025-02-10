package com.lawfirm.model.cases.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 海事海商纠纷案由
 */
@Getter
public enum MaritimeCauseEnum implements BaseEnum<String> {
    
    // 船舶碰撞及相关损害
    SHIP_COLLISION("船舶碰撞损害责任纠纷"),
    SHIP_TOUCH("船舶触碰损害责任纠纷"),
    SHIP_FACILITY_DAMAGE("船舶损坏空中设施、水下设施损害责任纠纷"),
    SHIP_POLLUTION("船舶污染损害责任纠纷"),
    MARITIME_POLLUTION("海上、通海水域污染损害责任纠纷"),
    MARITIME_AQUACULTURE("海上、通海水域养殖损害责任纠纷"),
    MARITIME_PROPERTY("海上、通海水域财产损害责任纠纷"),
    MARITIME_PERSONAL("海上、通海水域人身损害责任纠纷"),
    ILLEGAL_DETENTION("非法留置船舶、船载货物、船用燃油、船用物料损害责任纠纷"),
    
    // 海上运输合同
    MARITIME_CARGO("海上、通海水域货物运输合同纠纷"),
    MARITIME_PASSENGER("海上、通海水域旅客运输合同纠纷"),
    MARITIME_LUGGAGE("海上、通海水域行李运输合同纠纷"),
    
    // 船舶相关合同
    SHIP_MANAGEMENT("船舶经营管理合同纠纷"),
    SHIP_SALE("船舶买卖合同纠纷"),
    SHIP_BUILD("船舶建造合同纠纷"),
    SHIP_REPAIR("船舶修理合同纠纷"),
    SHIP_REBUILD("船舶改建合同纠纷"),
    SHIP_DISMANTLE("船舶拆解合同纠纷"),
    SHIP_MORTGAGE("船舶抵押合同纠纷"),
    VOYAGE_CHARTER("航次租船合同纠纷"),
    SHIP_LEASE("船舶租用合同纠纷"),
    SHIP_FINANCE_LEASE("船舶融资租赁合同纠纷"),
    
    // 其他海事纠纷
    MARITIME_TRANSPORT("海上、通海水域运输船舶承包合同纠纷"),
    FISHING_BOAT("渔船承包合同纠纷"),
    SHIP_EQUIPMENT("船舶属具租赁合同纠纷"),
    SHIP_EQUIPMENT_STORAGE("船舶属具保管合同纠纷"),
    CONTAINER_LEASE("海运集装箱租赁合同纠纷"),
    CONTAINER_STORAGE("海运集装箱保管合同纠纷"),
    PORT_CARGO_STORAGE("港口货物保管合同纠纷"),
    SHIP_AGENT("船舶代理合同纠纷"),
    MARITIME_CARGO_AGENT("海上、通海水域货运代理合同纠纷"),
    TALLY("理货合同纠纷"),
    SHIP_MATERIAL("船舶物料和备品供应合同纠纷"),
    CREW_SERVICE("船员劳务合同纠纷"),
    MARITIME_RESCUE("海难救助合同纠纷"),
    MARITIME_SALVAGE("海上、通海水域打捞合同纠纷"),
    MARITIME_TOWAGE("海上、通海水域拖航合同纠纷"),
    MARITIME_INSURANCE("海上、通海水域保险合同纠纷"),
    MARITIME_PROTECTION("海上、通海水域保赔合同纠纷"),
    MARITIME_JOINT("海上、通海水域运输联营合同纠纷"),
    SHIP_OPERATION_LOAN("船舶营运借款合同纠纷"),
    MARITIME_SECURITY("海事担保合同纠纷"),
    CHANNEL_PORT("航道、港口疏浚合同纠纷"),
    DOCK_CONSTRUCTION("船坞、码头建造合同纠纷"),
    SHIP_INSPECTION("船舶检验合同纠纷"),
    MARITIME_CLAIM("海事请求担保纠纷"),
    MARITIME_ACCIDENT("海上、通海水域运输重大责任事故责任纠纷"),
    PORT_ACCIDENT("港口作业重大责任事故责任纠纷"),
    PORT_OPERATION("港口作业纠纷"),
    GENERAL_AVERAGE("共同海损纠纷"),
    OCEAN_DEVELOPMENT("海洋开发利用纠纷"),
    SHIP_OWNERSHIP("船舶共有纠纷"),
    SHIP_RIGHTS("船舶权属纠纷"),
    MARITIME_FRAUD("海运欺诈纠纷"),
    MARITIME_DEBT("海事债权确权纠纷");

    private final String description;
    private final CaseCauseEnum parentCause = CaseCauseEnum.MARITIME;

    MaritimeCauseEnum(String description) {
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