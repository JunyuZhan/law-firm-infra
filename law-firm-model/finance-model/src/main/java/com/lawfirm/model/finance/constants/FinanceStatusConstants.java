package com.lawfirm.model.finance.constants;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 财务状态相关常量
 */
public interface FinanceStatusConstants extends BaseConstants {
    
    /**
     * 交易状态
     */
    interface TransactionStatus {
        String PENDING = "PENDING";        // 待处理
        String PROCESSING = "PROCESSING";   // 处理中
        String SUCCESS = "SUCCESS";         // 成功
        String FAILED = "FAILED";           // 失败
        String CANCELLED = "CANCELLED";     // 已取消
    }
    
    /**
     * 发票状态
     */
    interface InvoiceStatus {
        String DRAFT = "DRAFT";           // 草稿
        String ISSUED = "ISSUED";         // 已开具
        String VOID = "VOID";            // 已作废
        String CANCELLED = "CANCELLED";   // 已取消
    }
    
    /**
     * 账单状态
     */
    interface BillingStatus {
        String UNPAID = "UNPAID";          // 未支付
        String PARTIAL_PAID = "PARTIAL_PAID";  // 部分支付
        String PAID = "PAID";              // 已支付
        String OVERDUE = "OVERDUE";        // 已逾期
    }
    
    /**
     * 预算状态
     */
    interface BudgetStatus {
        String DRAFT = "DRAFT";           // 草稿
        String APPROVED = "APPROVED";     // 已批准
        String REJECTED = "REJECTED";     // 已拒绝
        String EXECUTED = "EXECUTED";     // 已执行
    }
    
    /**
     * 付款状态
     */
    interface PaymentStatus {
        String PENDING = "PENDING";      // 待付款
        String PROCESSING = "PROCESSING";  // 付款中
        String SUCCESS = "SUCCESS";      // 付款成功
        String FAILED = "FAILED";        // 付款失败
    }
    
    /**
     * 账户状态
     */
    interface AccountStatus {
        String ACTIVE = "ACTIVE";        // 活跃
        String INACTIVE = "INACTIVE";    // 不活跃
        String FROZEN = "FROZEN";        // 冻结
        String CLOSED = "CLOSED";        // 已关闭
    }
} 