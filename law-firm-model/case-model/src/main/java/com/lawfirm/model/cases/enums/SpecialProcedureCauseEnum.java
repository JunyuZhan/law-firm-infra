package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 特别程序案件案由
 */
@Getter
public enum SpecialProcedureCauseEnum implements BaseEnum<String> {
    
    // 选民资格案件
    VOTER_QUALIFICATION("选民资格案件"),
    
    // 宣告失踪、宣告死亡案件
    DECLARE_MISSING("宣告失踪案件"),
    DECLARE_DEATH("宣告死亡案件"),
    
    // 认定公民无民事行为能力、限制民事行为能力案件
    DECLARE_INCAPACITY("认定公民无民事行为能力案件"),
    DECLARE_LIMITED_CAPACITY("认定公民限制民事行为能力案件"),
    
    // 认定财产无主案件
    OWNERLESS_PROPERTY("认定财产无主案件"),
    
    // 监护权案件
    GUARDIAN_QUALIFICATION("确认监护人资格案件"),
    APPOINT_GUARDIAN("指定监护人案件"),
    CHANGE_GUARDIAN("变更监护人案件"),
    REVOKE_GUARDIAN("撤销监护人资格案件"),
    
    // 督促程序案件
    PAYMENT_ORDER("支付令案件"),
    
    // 公示催告程序案件
    PUBLIC_SUMMONS("公示催告案件"),
    
    // 企业法人清算案件
    COMPANY_LIQUIDATION("企业法人清算案件"),
    
    // 破产案件
    BANKRUPTCY_LIQUIDATION("破产清算案件"),
    BANKRUPTCY_REORGANIZATION("破产重整案件"),
    BANKRUPTCY_COMPROMISE("破产和解案件"),
    
    // 证券纠纷案件
    SECURITIES_DISPUTE("证券纠纷案件"),
    
    // 海事案件
    MARITIME_DISPUTE("海事案件"),
    
    // 知识产权案件
    IP_DISPUTE("知识产权案件"),
    
    // 涉外民商事案件
    FOREIGN_CIVIL_COMMERCIAL("涉外民商事案件"),
    
    // 执行案件
    EXECUTION("执行案件"),
    
    // 其他特别程序案件
    OTHER_SPECIAL("其他特别程序案件");

    private final String description;
    private final CaseCauseEnum parentCause = CaseCauseEnum.SPECIAL_LITIGATION;

    SpecialProcedureCauseEnum(String description) {
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