package com.lawfirm.finance.constant;

/**
 * 财务模块常量定义
 */
public class FinanceConstants {
    
    /**
     * 收费状态
     */
    public static class FeeStatus {
        /** 未支付 */
        public static final String UNPAID = "UNPAID";
        /** 已支付 */
        public static final String PAID = "PAID";
        /** 部分支付 */
        public static final String PARTIAL = "PARTIAL";
        /** 已退款 */
        public static final String REFUNDED = "REFUNDED";
    }

    /**
     * 收费类型
     */
    public static class FeeType {
        /** 案件收费 */
        public static final String CASE = "CASE";
        /** 咨询收费 */
        public static final String CONSULTATION = "CONSULTATION";
        /** 其他收费 */
        public static final String OTHER = "OTHER";
    }

    /**
     * 支出状态
     */
    public static class ExpenseStatus {
        /** 待审批 */
        public static final String PENDING = "PENDING";
        /** 已审批 */
        public static final String APPROVED = "APPROVED";
        /** 已驳回 */
        public static final String REJECTED = "REJECTED";
        /** 已支付 */
        public static final String PAID = "PAID";
    }

    /**
     * 支出类型
     */
    public static class ExpenseType {
        /** 日常运营 */
        public static final String OPERATION = "OPERATION";
        /** 人员工资 */
        public static final String SALARY = "SALARY";
        /** 办公设备 */
        public static final String EQUIPMENT = "EQUIPMENT";
        /** 其他支出 */
        public static final String OTHER = "OTHER";
    }

    /**
     * 发票类型
     */
    public static class InvoiceType {
        /** 增值税普通发票 */
        public static final int NORMAL = 1;
        /** 增值税专用发票 */
        public static final int SPECIAL = 2;
    }

    /**
     * 发票状态
     */
    public static class InvoiceStatus {
        /** 正常 */
        public static final int NORMAL = 1;
        /** 作废 */
        public static final int VOID = 2;
    }

    /**
     * 错误码定义
     */
    public static class ErrorCode {
        /** 收费记录不存在 */
        public static final int FEE_RECORD_NOT_FOUND = 4001;
        /** 支出记录不存在 */
        public static final int EXPENSE_RECORD_NOT_FOUND = 4002;
        /** 发票记录不存在 */
        public static final int INVOICE_NOT_FOUND = 4003;
        /** 发票号码重复 */
        public static final int INVOICE_NUMBER_DUPLICATE = 4004;
        /** 无效的支付金额 */
        public static final int INVALID_PAYMENT_AMOUNT = 4005;
        /** 无效的操作 */
        public static final int INVALID_OPERATION = 4006;
    }
} 