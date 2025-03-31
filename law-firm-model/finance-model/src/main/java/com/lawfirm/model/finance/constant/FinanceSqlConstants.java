package com.lawfirm.model.finance.constant;

/**
 * 财务模块SQL常量类
 * 集中管理财务相关SQL查询语句，提高可维护性和安全性
 */
public class FinanceSqlConstants {
    
    /**
     * 收入相关SQL常量
     */
    public static class Income {
        /**
         * 按案件ID分组统计收入
         */
        public static final String STATISTIC_INCOME_BY_CASE = 
                "SELECT case_id, SUM(amount) as amount, COUNT(1) as count FROM fin_income GROUP BY case_id";
    }
    
    /**
     * 支出相关SQL常量
     */
    public static class Expense {
        /**
         * 按类型统计支出
         */
        public static final String STATISTIC_EXPENSE_BY_TYPE = 
                "SELECT expense_type, SUM(amount) as amount, COUNT(1) as count FROM fin_expense GROUP BY expense_type";
                
        /**
         * 按案件ID分组统计支出
         */
        public static final String STATISTIC_EXPENSE_BY_CASE = 
                "SELECT case_id, SUM(amount) as amount, COUNT(1) as count FROM fin_expense GROUP BY case_id";
    }
    
    /**
     * 发票相关SQL常量
     */
    public static class Invoice {
        /**
         * 按状态统计发票
         */
        public static final String STATISTIC_INVOICE_BY_STATUS = 
                "SELECT status, SUM(amount) as amount, COUNT(1) as count FROM fin_invoice GROUP BY status";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private FinanceSqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 