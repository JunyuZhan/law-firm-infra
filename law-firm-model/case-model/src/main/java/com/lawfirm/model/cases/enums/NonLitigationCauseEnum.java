package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 非讼程序案件案由
 */
@Getter
public enum NonLitigationCauseEnum implements BaseEnum<String> {
    
    // 选民资格案件
    VOTER_QUALIFICATION("申请确定选民资格"),
    
    // 宣告失踪、宣告死亡案件
    DECLARE_MISSING("申请宣告自然人失踪"),
    REVOKE_MISSING("申请撤销宣告失踪判决"),
    PROPERTY_MANAGER("申请为失踪人财产指定、变更代管人"),
    DECLARE_DEATH("申请宣告自然人死亡"),
    REVOKE_DEATH("申请撤销宣告自然人死亡判决"),
    
    // 认定无民事行为能力、限制民事行为能力案件
    DECLARE_INCAPACITY("申请宣告自然人无民事行为能力"),
    DECLARE_LIMITED_CAPACITY("申请宣告自然人限制民事行为能力"),
    RESTORE_LIMITED_CAPACITY("申请宣告自然人恢复限制民事行为能力"),
    RESTORE_FULL_CAPACITY("申请宣告自然人恢复完全民事行为能力"),
    
    // 指定遗产管理人案件
    ESTATE_MANAGER("申请指定遗产管理人"),
    
    // 认定财产无主案件
    OWNERLESS_PROPERTY("申请认定财产无主"),
    REVOKE_OWNERLESS("申请撤销认定财产无主判决"),
    
    // 确认调解协议案件
    CONFIRM_MEDIATION("申请司法确认调解协议"),
    REVOKE_MEDIATION("申请撤销确认调解协议裁定"),
    
    // 实现担保物权案件
    REALIZE_SECURITY("申请实现担保物权"),
    REVOKE_SECURITY("申请撤销准许实现担保物权裁定"),
    
    // 监护权特别程序案件
    CONFIRM_GUARDIAN("申请确定监护人"),
    APPOINT_GUARDIAN("申请指定监护人"),
    CHANGE_GUARDIAN("申请变更监护人"),
    REVOKE_GUARDIAN("申请撤销监护人资格"),
    RESTORE_GUARDIAN("申请恢复监护人资格"),
    
    // 督促程序案件
    PAYMENT_ORDER("申请支付令"),
    
    // 公示催告程序案件
    PUBLIC_SUMMONS("申请公示催告"),
    
    // 公司清算案件
    COMPANY_LIQUIDATION("申请公司清算"),
    
    // 破产程序案件
    BANKRUPTCY_LIQUIDATION("申请破产清算"),
    BANKRUPTCY_REORGANIZATION("申请破产重整"),
    BANKRUPTCY_COMPROMISE("申请破产和解"),
    BANKRUPTCY_DISTRIBUTION("申请对破产财产追加分配"),
    
    // 申请诉前停止侵害知识产权案件
    STOP_PATENT("申请诉前停止侵害专利权"),
    STOP_TRADEMARK("申请诉前停止侵害注册商标专用权"),
    STOP_COPYRIGHT("申请诉前停止侵害著作权"),
    STOP_PLANT_VARIETY("申请诉前停止侵害植物新品种权"),
    STOP_SOFTWARE("申请诉前停止侵害计算机软件著作权"),
    STOP_IC_LAYOUT("申请诉前停止侵害集成电路布图设计专用权"),
    
    // 申请保全案件
    PRE_LITIGATION_PROPERTY("申请诉前财产保全"),
    PRE_LITIGATION_BEHAVIOR("申请诉前行为保全"),
    PRE_LITIGATION_EVIDENCE("申请诉前证据保全"),
    PRE_ARBITRATION_PROPERTY("申请仲裁前财产保全"),
    PRE_ARBITRATION_BEHAVIOR("申请仲裁前行为保全"),
    PRE_ARBITRATION_EVIDENCE("申请仲裁前证据保全"),
    ARBITRATION_PROPERTY("仲裁程序中的财产保全"),
    ARBITRATION_EVIDENCE("仲裁程序中的证据保全"),
    PRE_EXECUTION_PROPERTY("申请执行前财产保全"),
    STOP_LC_PAYMENT("申请中止支付信用证项下款项"),
    STOP_GUARANTEE_PAYMENT("申请中止支付保函项下款项"),
    
    // 申请人身安全保护令案件
    PERSONAL_PROTECTION("申请人身安全保护令"),
    
    // 申请人格权侵害禁令案件
    PERSONALITY_INJUNCTION("申请人格权侵害禁令"),
    
    // 仲裁程序案件
    ARBITRATION_AGREEMENT("申请确认仲裁协议效力"),
    REVOKE_ARBITRATION("申请撤销仲裁裁决"),
    
    // 海事诉讼特别程序案件
    MARITIME_PRESERVATION("申请海事请求保全"),
    MARITIME_ORDER("申请海事支付令"),
    MARITIME_INJUNCTION("申请海事强制令"),
    MARITIME_EVIDENCE("申请海事证据保全"),
    MARITIME_LIABILITY_FUND("申请设立海事赔偿责任限制基金"),
    MARITIME_PRIORITY("申请船舶优先权催告"),
    MARITIME_CLAIM("申请海事债权登记与受偿"),
    
    // 申请承认与执行法院判决、仲裁裁决案件
    EXECUTE_MARITIME_ARBITRATION("申请执行海事仲裁裁决"),
    EXECUTE_IP_ARBITRATION("申请执行知识产权仲裁裁决"),
    EXECUTE_FOREIGN_ARBITRATION("申请执行涉外仲裁裁决"),
    RECOGNIZE_HK_JUDGMENT("申请认可和执行香港特别行政区法院民事判决"),
    RECOGNIZE_HK_ARBITRATION("申请认可和执行香港特别行政区仲裁裁决"),
    RECOGNIZE_MACAO_JUDGMENT("申请认可和执行澳门特别行政区法院民事判决"),
    RECOGNIZE_MACAO_ARBITRATION("申请认可和执行澳门特别行政区仲裁裁决"),
    RECOGNIZE_TAIWAN_JUDGMENT("申请认可和执行台湾地区法院民事判决"),
    RECOGNIZE_TAIWAN_ARBITRATION("申请认可和执行台湾地区仲裁裁决"),
    RECOGNIZE_FOREIGN_JUDGMENT("申请承认和执行外国法院民事判决、裁定"),
    RECOGNIZE_FOREIGN_ARBITRATION("申请承认和执行外国仲裁裁决");

    private final String description;
    private final CaseCauseEnum parentCause = CaseCauseEnum.NON_LITIGATION;

    NonLitigationCauseEnum(String description) {
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