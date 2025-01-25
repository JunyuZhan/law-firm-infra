package com.lawfirm.finance.constant;

/**
 * 财务模块常量定义
 */
public class FinanceConstant {
    
    private FinanceConstant() {
        throw new IllegalStateException("Constant class");
    }

    // 收费相关常量
    public static final String FEE_CACHE_PREFIX = "finance:fee:";
    public static final String INVOICE_CACHE_PREFIX = "finance:invoice:";
    
    // 财务状态相关常量
    public static final String STATUS_UNPAID = "UNPAID";           // 未支付
    public static final String STATUS_PAID = "PAID";               // 已支付
    public static final String STATUS_PARTIAL_PAID = "PARTIAL";    // 部分支付
    public static final String STATUS_REFUNDED = "REFUNDED";       // 已退款
    
    // 金额相关常量
    public static final int AMOUNT_SCALE = 2;                      // 金额精度
    public static final String CURRENCY_CNY = "CNY";               // 人民币
} 