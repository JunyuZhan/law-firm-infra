package com.lawfirm.model.cases.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 侵权责任纠纷案由
 */
@Getter
public enum TortCauseEnum implements BaseEnum<String> {
    
    // 一般侵权责任
    GUARDIAN_LIABILITY("监护人责任纠纷"),
    EMPLOYER_LIABILITY("用人单位责任纠纷"),
    LABOR_DISPATCH("劳务派遣工作人员侵权责任纠纷"),
    SERVICE_PROVIDER_HARM("提供劳务者致害责任纠纷"),
    SERVICE_PROVIDER_DAMAGE("提供劳务者受害责任纠纷"),
    NETWORK_TORT("网络侵权责任纠纷"),
    SAFETY_OBLIGATION("违反安全保障义务责任纠纷"),
    EDUCATION_LIABILITY("教育机构责任纠纷"),
    SEXUAL_HARASSMENT("性骚扰损害责任纠纷"),
    
    // 产品责任
    PRODUCT_LIABILITY("产品责任纠纷"),
    
    // 交通事故责任
    MOTOR_VEHICLE_ACCIDENT("机动车交通事故责任纠纷"),
    NON_MOTOR_VEHICLE_ACCIDENT("非机动车交通事故责任纠纷"),
    
    // 医疗损害责任
    MEDICAL_DAMAGE("医疗损害责任纠纷"),
    
    // 环境污染责任
    ENVIRONMENTAL_POLLUTION("环境污染责任纠纷"),
    ECOLOGICAL_DAMAGE("生态破坏责任纠纷"),
    
    // 高度危险责任
    HIGH_RISK_LIABILITY("高度危险责任纠纷"),
    
    // 动物致害责任
    ANIMAL_DAMAGE("饲养动物损害责任纠纷"),
    
    // 物件损害责任
    BUILDING_DAMAGE("建筑物和物件损害责任纠纷"),
    
    // 其他特殊侵权
    ELECTRIC_SHOCK("触电人身损害责任纠纷"),
    VOLUNTARY_SERVICE("义务帮工人受害责任纠纷"),
    GOOD_SAMARITAN("见义勇为人受害责任纠纷"),
    NOTARY_DAMAGE("公证损害责任纠纷"),
    EXCESSIVE_DEFENSE("防卫过当损害责任纠纷"),
    EMERGENCY_DANGER("紧急避险损害责任纠纷"),
    MILITARY_TORT("驻香港、澳门特别行政区军人执行职务侵权责任纠纷"),
    
    // 运输损害责任
    RAILWAY_TRANSPORT("铁路运输损害责任纠纷"),
    WATER_TRANSPORT("水上运输损害责任纠纷"),
    AIR_TRANSPORT("航空运输损害责任纠纷"),
    
    // 保全损害责任
    PROPERTY_PRESERVATION("因申请财产保全损害责任纠纷"),
    BEHAVIOR_PRESERVATION("因申请行为保全损害责任纠纷"),
    EVIDENCE_PRESERVATION("因申请证据保全损害责任纠纷"),
    ADVANCE_EXECUTION("因申请先予执行损害责任纠纷");

    private final String description;
    private final CaseCauseEnum parentCause = CaseCauseEnum.TORT;

    TortCauseEnum(String description) {
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