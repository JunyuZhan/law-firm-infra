package com.lawfirm.cases.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lawfirm.common.core.base.BaseEnum;
import lombok.Getter;

/**
 * 案件状态枚举
 */
@Getter
public enum CaseStatusEnum implements BaseEnum<String> {
    
    // 立案阶段
    REGISTERED("REGISTERED", "已登记", "案件基本信息已登记"),
    FILING("FILING", "立案中", "正在进行立案审批"),
    FILED("FILED", "已立案", "立案审批通过"),
    REJECTED("REJECTED", "已驳回", "立案审批被驳回"),
    
    // 办理阶段
    PROCESSING("PROCESSING", "办理中", "案件正在处理中"),
    PREPARING("PREPARING", "准备中", "正在准备开庭"),
    IN_COURT("IN_COURT", "开庭中", "正在开庭审理"),
    MEDIATION("MEDIATION", "调解中", "正在进行调解"),
    JUDGMENT("JUDGMENT", "已判决", "已收到判决结果"),
    
    // 执行阶段
    EXECUTING("EXECUTING", "执行中", "正在执行判决结果"),
    EXECUTED("EXECUTED", "已执行", "判决结果已执行完毕"),
    
    // 结案阶段
    CLOSING("CLOSING", "结案中", "正在进行结案处理"),
    CLOSED("CLOSED", "已结案", "案件已经结案"),
    
    // 其他状态
    SUSPENDED("SUSPENDED", "已暂停", "案件暂停处理"),
    TERMINATED("TERMINATED", "已终止", "案件终止"),
    CANCELLED("CANCELLED", "已撤销", "案件被撤销");
    
    @EnumValue
    private final String value;
    private final String label;
    private final String description;
    
    CaseStatusEnum(String value, String label, String description) {
        this.value = value;
        this.label = label;
        this.description = description;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    @Override
    public String getDescription() {
        return this.label;
    }
} 