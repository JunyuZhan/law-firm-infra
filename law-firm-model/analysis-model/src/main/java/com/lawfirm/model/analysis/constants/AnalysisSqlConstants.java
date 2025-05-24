package com.lawfirm.model.analysis.constants;

/**
 * 分析模块SQL常量类
 * 集中管理分析相关SQL查询语句，提高可维护性和安全性
 */
public class AnalysisSqlConstants {
    private AnalysisSqlConstants() { throw new IllegalStateException("常量类不应被实例化"); }
    // 示例：
    public static final String SELECT_CASE_ANALYSIS = "SELECT case_type, COUNT(*) as total FROM case_info WHERE deleted = 0 GROUP BY case_type";
    public static final String SELECT_FINANCE_TREND = "SELECT month, SUM(amount) as total FROM finance_record WHERE deleted = 0 GROUP BY month ORDER BY month DESC";
} 