package com.lawfirm.staff.model.response.finance.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 费用统计响应
 */
@Data
@Schema(description = "费用统计响应")
public class ExpenseStatisticsResponse {

    @Schema(description = "费用总数")
    private Long totalCount;

    @Schema(description = "费用总金额")
    private BigDecimal totalAmount;

    @Schema(description = "待审核数量")
    private Long pendingCount;

    @Schema(description = "已审核数量")
    private Long approvedCount;

    @Schema(description = "已驳回数量")
    private Long rejectedCount;

    @Schema(description = "本月费用数量")
    private Long monthlyCount;

    @Schema(description = "本月费用金额")
    private BigDecimal monthlyAmount;

    @Schema(description = "本年费用数量")
    private Long yearlyCount;

    @Schema(description = "本年费用金额")
    private BigDecimal yearlyAmount;

    @Schema(description = "按月统计")
    private List<MonthlyStatistics> monthlyStatistics;

    @Schema(description = "按类型统计")
    private List<CategoryStatistics> categoryStatistics;

    @Schema(description = "按案件统计")
    private List<CaseStatistics> caseStatistics;

    @Data
    @Schema(description = "月度统计")
    public static class MonthlyStatistics {
        
        @Schema(description = "月份")
        private String month;
        
        @Schema(description = "费用数量")
        private Long count;
        
        @Schema(description = "费用金额")
        private BigDecimal amount;
    }

    @Data
    @Schema(description = "类型统计")
    public static class CategoryStatistics {
        
        @Schema(description = "费用类型ID")
        private Long categoryId;
        
        @Schema(description = "类型名称")
        private String categoryName;
        
        @Schema(description = "费用数量")
        private Long count;
        
        @Schema(description = "费用金额")
        private BigDecimal amount;
        
        @Schema(description = "占比")
        private BigDecimal percentage;
    }

    @Data
    @Schema(description = "案件统计")
    public static class CaseStatistics {
        
        @Schema(description = "案件ID")
        private Long caseId;
        
        @Schema(description = "案件名称")
        private String caseName;
        
        @Schema(description = "费用数量")
        private Long count;
        
        @Schema(description = "费用金额")
        private BigDecimal amount;
        
        @Schema(description = "占比")
        private BigDecimal percentage;
    }
} 