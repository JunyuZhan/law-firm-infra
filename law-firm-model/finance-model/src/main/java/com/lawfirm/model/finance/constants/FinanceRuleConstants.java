package com.lawfirm.model.finance.constants;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 财务规则常量
 */
public interface FinanceRuleConstants extends BaseConstants {
    
    /**
     * 金额规则
     */
    interface AmountRule {
        int MIN_AMOUNT = 0;  // 最小金额
        int MAX_AMOUNT = 999999999;  // 最大金额（9位数）
        int DECIMAL_PLACES = 2;  // 小数位数
        double MIN_TRANSACTION_AMOUNT = 0.01;  // 最小交易金额
    }
    
    /**
     * 时间规则
     */
    interface TimeRule {
        int MAX_OVERDUE_DAYS = 90;  // 最大逾期天数
        int PAYMENT_DEADLINE_DAYS = 30;  // 付款截止天数
        int INVOICE_VALID_DAYS = 180;  // 发票有效期（天）
        int BUDGET_MAX_MONTHS = 12;  // 预算最大月数
    }
    
    /**
     * 审批规则
     */
    interface ApprovalRule {
        double APPROVAL_THRESHOLD = 10000.00;  // 需要审批的金额阈值
        int MAX_APPROVAL_LEVEL = 3;  // 最大审批级别
        int AUTO_APPROVAL_AMOUNT = 1000;  // 自动审批金额
    }
    
    /**
     * 账户规则
     */
    interface AccountRule {
        int MAX_ACCOUNTS_PER_USER = 5;  // 每个用户最大账户数
        int MIN_BALANCE_ALERT = 1000;  // 最小余额警告
        double MAX_DAILY_TRANSACTION = 100000.00;  // 每日最大交易额
        int MAX_DAILY_TRANSACTIONS = 100;  // 每日最大交易次数
    }
    
    /**
     * 发票规则
     */
    interface InvoiceRule {
        int MAX_ITEMS_PER_INVOICE = 20;  // 每张发票最大项目数
        double MIN_INVOICE_AMOUNT = 0.01;  // 最小开票金额
        double MAX_INVOICE_AMOUNT = 9999999.99;  // 最大开票金额
        int INVOICE_NUMBER_LENGTH = 20;  // 发票号码长度
    }
    
    /**
     * 预算规则
     */
    interface BudgetRule {
        double BUDGET_ALERT_THRESHOLD = 0.8;  // 预算警告阈值（80%）
        double BUDGET_LOCK_THRESHOLD = 1.0;  // 预算锁定阈值（100%）
        int MIN_BUDGET_PERIOD_MONTHS = 1;  // 最小预算期间（月）
        int MAX_BUDGET_PERIOD_MONTHS = 12;  // 最大预算期间（月）
    }
} 