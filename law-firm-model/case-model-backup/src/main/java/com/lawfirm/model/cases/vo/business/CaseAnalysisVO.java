package com.lawfirm.model.cases.vo.business;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 案件分析视图对象
 */
@Data
public class CaseAnalysisVO {
    
    /**
     * 案件基本统计
     */
    private Integer totalCases; // 案件总数
    private Integer activeCases; // 活跃案件数
    private Integer completedCases; // 已完成案件数
    private BigDecimal totalRevenue; // 总收入
    private BigDecimal averageRevenue; // 平均收入
    
    /**
     * 案件类型分布
     */
    private Map<String, Integer> caseTypeDistribution; // 案件类型分布
    private Map<String, BigDecimal> revenueByType; // 各类型案件收入
    
    /**
     * 案件状态分布
     */
    private Map<String, Integer> caseStatusDistribution; // 案件状态分布
    private Map<String, Double> caseProgressRates; // 案件进展率
    
    /**
     * 时间维度分析
     */
    private Map<String, Integer> monthlyNewCases; // 月度新增案件
    private Map<String, Integer> monthlyCompletedCases; // 月度完成案件
    private Double averageProcessingTime; // 平均处理时间（天）
    
    /**
     * 律师绩效分析
     */
    private Map<String, Integer> casesPerLawyer; // 每个律师的案件数
    private Map<String, BigDecimal> revenuePerLawyer; // 每个律师创收
    private Map<String, Double> successRatePerLawyer; // 每个律师胜诉率
    
    /**
     * 地域分布
     */
    private Map<String, Integer> geographicalDistribution; // 地域分布
    private Map<String, Integer> courtDistribution; // 法院分布
    
    /**
     * 客户分析
     */
    private Map<String, Integer> clientTypeDistribution; // 客户类型分布
    private List<String> topClients; // 主要客户列表
    private Double clientRetentionRate; // 客户保持率
    
    /**
     * 风险分析
     */
    private Integer highRiskCases; // 高风险案件数
    private List<String> riskFactors; // 主要风险因素
    private Map<String, Integer> riskDistribution; // 风险等级分布
    
    /**
     * 趋势分析
     */
    private Map<String, Double> successRateTrend; // 胜诉率趋势
    private Map<String, BigDecimal> revenueTrend; // 收入趋势
    private Map<String, Integer> caseVolumeTrend; // 案件量趋势
} 