package com.lawfirm.core.workflow.enums;

import lombok.Getter;

/**
 * 流程模板枚举
 */
@Getter
public enum ProcessTemplateEnum {
    
    // 案件流程
    CASE_CREATE("case-create", "案件立案流程", "cases"),
    CASE_CLOSE("case-close", "案件结案流程", "cases"),
    CASE_TRANSFER("case-transfer", "案件转办流程", "cases"),
    CASE_ARCHIVE("case-archive", "案件归档流程", "cases"),
    
    // 合同流程
    CONTRACT_DRAFT("contract-draft", "合同起草流程", "contracts"),
    CONTRACT_REVIEW("contract-review", "合同审批流程", "contracts"),
    CONTRACT_SIGN("contract-sign", "合同签署流程", "contracts"),
    CONTRACT_ARCHIVE("contract-archive", "合同归档流程", "contracts"),
    
    // 财务流程
    FINANCE_EXPENSE("finance-expense", "费用申请流程", "finance"),
    FINANCE_REIMBURSE("finance-reimburse", "报销申请流程", "finance"),
    FINANCE_PAYMENT("finance-payment", "付款申请流程", "finance"),
    FINANCE_INVOICE("finance-invoice", "开票申请流程", "finance"),
    
    // 行政流程
    ADMIN_LEAVE("admin-leave", "请假申请流程", "admin"),
    ADMIN_TRAVEL("admin-travel", "出差申请流程", "admin"),
    ADMIN_OVERTIME("admin-overtime", "加班申请流程", "admin"),
    ADMIN_SUPPLIES("admin-supplies", "物资申请流程", "admin");
    
    /**
     * 流程定义Key
     */
    private final String processKey;
    
    /**
     * 流程名称
     */
    private final String processName;
    
    /**
     * 流程分类
     */
    private final String category;
    
    ProcessTemplateEnum(String processKey, String processName, String category) {
        this.processKey = processKey;
        this.processName = processName;
        this.category = category;
    }
} 