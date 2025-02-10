package com.lawfirm.model.cases.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政案件案由
 */
@Getter
public enum AdministrativeCauseEnum implements BaseEnum<String> {
    
    // 行政许可类
    LICENSE_REJECT("不予行政许可"),
    LICENSE_REVOKE("撤销行政许可"),
    LICENSE_CHANGE("变更行政许可"),
    LICENSE_EXTENSION("行政许可延续"),
    LICENSE_CONDITION("行政许可附加条件"),
    
    // 行政处罚类
    ADMIN_PENALTY("行政处罚"),
    ADMIN_DETENTION("行政拘留"),
    ADMIN_CONFISCATION("没收违法所得"),
    ADMIN_FINE("罚款"),
    LICENSE_REVOCATION("吊销许可证"),
    BUSINESS_SUSPENSION("责令停产停业"),
    
    // 行政强制类
    ADMIN_ENFORCEMENT("行政强制"),
    PROPERTY_SEIZURE("查封、扣押、冻结财产"),
    ADMIN_EXECUTION("代履行"),
    
    // 行政征收类
    ADMIN_COLLECTION("行政征收"),
    LAND_ACQUISITION("土地征收"),
    HOUSE_DEMOLITION("房屋征收"),
    
    // 行政确认类
    ADMIN_CONFIRMATION("行政确认"),
    QUALIFICATION_CONFIRM("资格确认"),
    STATUS_CONFIRM("法律地位确认"),
    RIGHT_CONFIRM("权益确认"),
    
    // 行政裁决类
    ADMIN_ADJUDICATION("行政裁决"),
    COMPENSATION_DISPUTE("行政赔偿纠纷"),
    ADMIN_MEDIATION("行政调解"),
    
    // 行政复议类
    ADMIN_RECONSIDERATION("行政复议"),
    RECONSIDERATION_ACCEPTANCE("行政复议受理"),
    RECONSIDERATION_DECISION("行政复议决定"),
    
    // 政府信息公开类
    INFO_DISCLOSURE("政府信息公开"),
    INFO_CORRECTION("政府信息更正"),
    
    // 行政协议类
    ADMIN_AGREEMENT("行政协议"),
    AGREEMENT_CHANGE("行政协议变更"),
    AGREEMENT_TERMINATION("行政协议终止"),
    
    // 其他行政行为类
    ADMIN_INACTION("行政不作为"),
    ADMIN_DELAY("行政拖延"),
    ADMIN_ABUSE("滥用行政权力"),
    ADMIN_DISCRIMINATION("行政歧视"),
    
    // 行政赔偿类
    COMPENSATION_LIFE("人身损害赔偿"),
    COMPENSATION_PROPERTY("财产损害赔偿"),
    COMPENSATION_DELAY("延迟履行赔偿"),
    
    // 执法监督类
    LAW_ENFORCEMENT("违法执法行为"),
    ADMIN_SUPERVISION("行政监督"),
    ADMIN_INSPECTION("行政检查"),
    
    // 特殊行政领域
    URBAN_PLANNING("城市规划"),
    ENVIRONMENTAL_PROTECTION("环境保护"),
    LAND_RESOURCES("国土资源"),
    MARKET_REGULATION("市场监管"),
    TAX_ADMINISTRATION("税务管理"),
    CUSTOMS_SUPERVISION("海关监管"),
    EDUCATION_ADMIN("教育行政"),
    HEALTH_ADMIN("卫生行政"),
    TRAFFIC_ADMIN("交通行政"),
    LABOR_ADMIN("劳动行政"),
    
    // 其他
    OTHER_ADMINISTRATIVE("其他行政案由");

    private final String description;
    private final CaseTypeEnum caseType = CaseTypeEnum.ADMINISTRATIVE;

    AdministrativeCauseEnum(String description) {
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