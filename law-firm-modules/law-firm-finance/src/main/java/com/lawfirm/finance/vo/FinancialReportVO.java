package com.lawfirm.finance.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 财务报表VO
 */
@Data
public class FinancialReportVO {
    
    private Long lawFirmId;              // 律所ID
    private LocalDateTime startTime;      // 统计开始时间
    private LocalDateTime endTime;        // 统计结束时间
    
    // 收入统计
    private BigDecimal totalIncome;                  // 总收入
    private Map<String, BigDecimal> incomeByType;    // 按类型统计收入
    private BigDecimal totalPaidIncome;              // 已收总额
    private BigDecimal totalUnpaidIncome;            // 未收总额
    
    // 支出统计
    private BigDecimal totalExpense;                 // 总支出
    private Map<String, BigDecimal> expenseByType;   // 按类型统计支出
    private BigDecimal totalPaidExpense;             // 已付总额
    private BigDecimal totalUnpaidExpense;           // 未付总额
    
    // 利润统计
    private BigDecimal grossProfit;                  // 毛利润(总收入-总支出)
    private BigDecimal netProfit;                    // 净利润(已收收入-已付支出)
    
    // 其他统计
    private Integer totalCases;                      // 总案件数
    private Integer totalClients;                    // 总客户数
    private Map<String, Integer> casesByStatus;      // 按状态统计案件
    private Map<String, BigDecimal> profitByMonth;   // 按月统计利润
} 