package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 物权纠纷案由
 */
@Getter
public enum PropertyRightCauseEnum implements BaseEnum<String> {
    
    // 不动产登记纠纷
    OBJECTION_REGISTRATION("异议登记不当损害责任纠纷"),
    FALSE_REGISTRATION("虚假登记损害责任纠纷"),
    
    // 物权保护纠纷
    PROPERTY_CONFIRMATION("物权确认纠纷"),
    RETURN_ORIGINAL("返还原物纠纷"),
    REMOVE_OBSTRUCTION("排除妨害纠纷"),
    ELIMINATE_DANGER("消除危险纠纷"),
    REPAIR_REMAKE("修理、重作、更换纠纷"),
    RESTORE_STATUS("恢复原状纠纷"),
    PROPERTY_DAMAGE("财产损害赔偿纠纷"),
    
    // 所有权纠纷
    COLLECTIVE_MEMBER_RIGHTS("侵害集体经济组织成员权益纠纷"),
    BUILDING_OWNERSHIP("建筑物区分所有权纠纷"),
    OWNER_REVOCATION("业主撤销权纠纷"),
    OWNER_INFORMATION("业主知情权纠纷"),
    LOST_PROPERTY("遗失物返还纠纷"),
    DRIFTING_PROPERTY("漂流物返还纠纷"),
    BURIED_PROPERTY("埋藏物返还纠纷"),
    HIDDEN_PROPERTY("隐藏物返还纠纷"),
    ATTACHED_PROPERTY("添附物归属纠纷"),
    NEIGHBORING_RELATION("相邻关系纠纷"),
    CO_OWNERSHIP("共有纠纷"),
    
    // 用益物权纠纷
    SEA_AREA_USE("海域使用权纠纷"),
    MINING_EXPLORATION("探矿权纠纷"),
    MINING_RIGHT("采矿权纠纷"),
    WATER_INTAKE("取水权纠纷"),
    CULTIVATION("养殖权纠纷"),
    FISHING("捕捞权纠纷"),
    LAND_CONTRACT("土地承包经营权纠纷"),
    LAND_MANAGEMENT("土地经营权纠纷"),
    CONSTRUCTION_LAND("建设用地使用权纠纷"),
    HOMESTEAD("宅基地使用权纠纷"),
    RESIDENCE_RIGHT("居住权纠纷"),
    EASEMENT("地役权纠纷"),
    
    // 担保物权纠纷
    MORTGAGE("抵押权纠纷"),
    PLEDGE("质权纠纷"),
    LIEN("留置权纠纷"),
    
    // 占有保护纠纷
    POSSESSION_RETURN("占有物返还纠纷"),
    POSSESSION_OBSTRUCTION("占有排除妨害纠纷"),
    POSSESSION_DANGER("占有消除危险纠纷"),
    POSSESSION_DAMAGE("占有物损害赔偿纠纷");

    private final String description;
    private final CaseCauseEnum parentCause = CaseCauseEnum.PROPERTY_RIGHT;

    PropertyRightCauseEnum(String description) {
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