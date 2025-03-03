package com.lawfirm.model.finance.constants;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 财务错误码常量
 */
public interface FinanceErrorConstants extends BaseConstants {
    
    /**
     * 通用错误码（5000-5099）
     */
    interface CommonError {
        String PREFIX = "FIN";  // 财务模块前缀
        
        String SYSTEM_ERROR = PREFIX + "5000";  // 系统错误
        String PARAM_ERROR = PREFIX + "5001";   // 参数错误
        String DATA_NOT_FOUND = PREFIX + "5002";  // 数据不存在
        String DATA_ALREADY_EXISTS = PREFIX + "5003";  // 数据已存在
        String OPERATION_NOT_ALLOWED = PREFIX + "5004";  // 操作不允许
    }
    
    /**
     * 交易错误码（5100-5199）
     */
    interface TransactionError {
        String INSUFFICIENT_BALANCE = "FIN5100";  // 余额不足
        String ACCOUNT_FROZEN = "FIN5101";  // 账户已冻结
        String INVALID_AMOUNT = "FIN5102";  // 无效金额
        String TRANSACTION_FAILED = "FIN5103";  // 交易失败
        String DUPLICATE_TRANSACTION = "FIN5104";  // 重复交易
    }
    
    /**
     * 发票错误码（5200-5299）
     */
    interface InvoiceError {
        String INVOICE_NOT_FOUND = "FIN5200";  // 发票不存在
        String INVOICE_ALREADY_EXISTS = "FIN5201";  // 发票已存在
        String INVALID_INVOICE_AMOUNT = "FIN5202";  // 无效发票金额
        String INVOICE_ALREADY_VOID = "FIN5203";  // 发票已作废
        String INVOICE_STATUS_ERROR = "FIN5204";  // 发票状态错误
    }
    
    /**
     * 账单错误码（5300-5399）
     */
    interface BillingError {
        String BILLING_NOT_FOUND = "FIN5300";  // 账单不存在
        String BILLING_ALREADY_PAID = "FIN5301";  // 账单已支付
        String INVALID_BILLING_AMOUNT = "FIN5302";  // 无效账单金额
        String BILLING_EXPIRED = "FIN5303";  // 账单已过期
        String BILLING_STATUS_ERROR = "FIN5304";  // 账单状态错误
    }
    
    /**
     * 预算错误码（5400-5499）
     */
    interface BudgetError {
        String BUDGET_NOT_FOUND = "FIN5400";  // 预算不存在
        String BUDGET_EXCEEDED = "FIN5401";  // 超出预算
        String INVALID_BUDGET_AMOUNT = "FIN5402";  // 无效预算金额
        String BUDGET_PERIOD_ERROR = "FIN5403";  // 预算期间错误
        String BUDGET_STATUS_ERROR = "FIN5404";  // 预算状态错误
    }
    
    /**
     * 账户错误码（5500-5599）
     */
    interface AccountError {
        String ACCOUNT_NOT_FOUND = "FIN5500";  // 账户不存在
        String ACCOUNT_ALREADY_EXISTS = "FIN5501";  // 账户已存在
        String ACCOUNT_FROZEN = "FIN5502";  // 账户已冻结
        String ACCOUNT_CLOSED = "FIN5503";  // 账户已关闭
        String INVALID_ACCOUNT_STATUS = "FIN5504";  // 无效账户状态
    }
} 