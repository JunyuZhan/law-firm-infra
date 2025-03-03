package com.lawfirm.model.finance.constants;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 财务消息提示常量
 */
public interface FinanceMessageConstants extends BaseConstants {
    
    /**
     * 交易相关消息
     */
    interface TransactionMessage {
        String TRANSACTION_SUCCESS = "交易成功";
        String TRANSACTION_FAILED = "交易失败";
        String TRANSACTION_CANCELLED = "交易已取消";
        String INSUFFICIENT_BALANCE = "账户余额不足";
        String ACCOUNT_FROZEN = "账户已被冻结";
        String AMOUNT_INVALID = "交易金额无效";
    }
    
    /**
     * 发票相关消息
     */
    interface InvoiceMessage {
        String INVOICE_ISSUED = "发票已开具";
        String INVOICE_VOID = "发票已作废";
        String INVOICE_CANCELLED = "发票已取消";
        String INVOICE_NUMBER_EXISTS = "发票号码已存在";
        String INVOICE_AMOUNT_INVALID = "发票金额无效";
    }
    
    /**
     * 账单相关消息
     */
    interface BillingMessage {
        String BILLING_CREATED = "账单已创建";
        String BILLING_PAID = "账单已支付";
        String BILLING_PARTIAL_PAID = "账单部分支付";
        String BILLING_OVERDUE = "账单已逾期";
        String PAYMENT_REQUIRED = "请及时支付账单";
    }
    
    /**
     * 预算相关消息
     */
    interface BudgetMessage {
        String BUDGET_APPROVED = "预算已批准";
        String BUDGET_REJECTED = "预算已拒绝";
        String BUDGET_EXECUTED = "预算已执行";
        String BUDGET_EXCEEDED = "超出预算限额";
        String INSUFFICIENT_BUDGET = "预算不足";
    }
    
    /**
     * 账户相关消息
     */
    interface AccountMessage {
        String ACCOUNT_CREATED = "账户已创建";
        String ACCOUNT_UPDATED = "账户已更新";
        String ACCOUNT_FROZEN = "账户已冻结";
        String ACCOUNT_UNFROZEN = "账户已解冻";
        String ACCOUNT_CLOSED = "账户已关闭";
        String BALANCE_UPDATED = "余额已更新";
    }
} 