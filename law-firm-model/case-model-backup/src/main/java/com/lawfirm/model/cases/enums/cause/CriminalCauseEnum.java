package com.lawfirm.model.cases.enums.cause;

import com.lawfirm.model.base.enums.BaseEnum;
import com.lawfirm.model.cases.enums.base.CaseTypeEnum;
import lombok.Getter;

/**
 * 刑事案件案由
 */
@Getter
public enum CriminalCauseEnum implements BaseEnum<String> {
    
    // 危害国家安全罪
    TREASON("危害国家安全罪"),
    SECESSION("分裂国家罪"),
    ESPIONAGE("间谍罪"),
    STATE_SECRET("泄露国家秘密罪"),
    
    // 危害公共安全罪
    ARSON("放火罪"),
    EXPLOSION("爆炸罪"),
    POISON("投毒罪"),
    DANGEROUS_SUBSTANCE("危险物品肇事罪"),
    TRAFFIC_ACCIDENT("交通肇事罪"),
    SAFETY_ACCIDENT("重大责任事故罪"),
    
    // 破坏社会主义市场经济秩序罪
    SMUGGLING("走私罪"),
    COUNTERFEIT_CURRENCY("假币罪"),
    TAX_EVASION("偷税罪"),
    FRAUD("诈骗罪"),
    FINANCIAL_FRAUD("金融诈骗罪"),
    INSIDER_TRADING("内幕交易罪"),
    MONEY_LAUNDERING("洗钱罪"),
    
    // 侵犯公民人身权利、民主权利罪
    INTENTIONAL_INJURY("故意伤害罪"),
    RAPE("强奸罪"),
    KIDNAPPING("绑架罪"),
    ILLEGAL_DETENTION("非法拘禁罪"),
    DEFAMATION("诽谤罪"),
    
    // 侵犯财产罪
    THEFT("盗窃罪"),
    ROBBERY("抢劫罪"),
    EXTORTION("敲诈勒索罪"),
    MISAPPROPRIATION("职务侵占罪"),
    EMBEZZLEMENT("贪污罪"),
    
    // 妨害社会管理秩序罪
    DISTURBING_ORDER("扰乱公共秩序罪"),
    GAMBLING("赌博罪"),
    DRUG_TRAFFICKING("贩卖毒品罪"),
    ORGANIZING_PROSTITUTION("组织卖淫罪"),
    
    // 贪污贿赂罪
    CORRUPTION("贪污罪"),
    BRIBERY("受贿罪"),
    OFFERING_BRIBE("行贿罪"),
    
    // 渎职罪
    DERELICTION("玩忽职守罪"),
    ABUSE_OF_POWER("滥用职权罪"),
    
    // 军人违反职责罪
    MILITARY_DUTY("违反职责罪"),
    MILITARY_WEAPON("擅离职守罪"),
    
    // 环境资源犯罪
    ENVIRONMENTAL_POLLUTION("污染环境罪"),
    ILLEGAL_MINING("非法采矿罪"),
    ILLEGAL_LOGGING("滥伐林木罪"),
    
    // 知识产权犯罪
    TRADEMARK_INFRINGEMENT("假冒注册商标罪"),
    COPYRIGHT_INFRINGEMENT("侵犯著作权罪"),
    TRADE_SECRET("侵犯商业秘密罪"),
    
    // 组织犯罪
    ORGANIZED_CRIME("组织、领导、参加黑社会性质组织罪"),
    GANG_CRIME("聚众斗殴罪"),
    
    // 金融犯罪
    ILLEGAL_FUNDRAISING("非法集资罪"),
    FINANCIAL_DOCUMENT("金融票据诈骗罪"),
    CREDIT_CARD("信用卡诈骗罪"),
    
    // 网络犯罪
    CYBER_THEFT("网络盗窃罪"),
    CYBER_FRAUD("网络诈骗罪"),
    CYBER_ATTACK("破坏计算机信息系统罪"),
    
    // 其他
    OTHER_CRIMINAL("其他刑事案由");

    private final String description;
    private final CaseTypeEnum caseType = CaseTypeEnum.CRIMINAL;

    CriminalCauseEnum(String description) {
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