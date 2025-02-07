package com.lawfirm.staff.model.response.finance.invoice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 发票统计响应
 */
@Data
@Schema(description = "发票统计响应")
public class InvoiceStatisticsResponse {

    @Schema(description = "发票总数")
    private Long totalCount;

    @Schema(description = "发票总金额")
    private BigDecimal totalAmount;

    @Schema(description = "待审核数量")
    private Long pendingCount;

    @Schema(description = "已审核数量")
    private Long approvedCount;

    @Schema(description = "已驳回数量")
    private Long rejectedCount;

    @Schema(description = "本月发票数量")
    private Long monthlyCount;

    @Schema(description = "本月发票金额")
    private BigDecimal monthlyAmount;

    @Schema(description = "本年发票数量")
    private Long yearlyCount;

    @Schema(description = "本年发票金额")
    private BigDecimal yearlyAmount;

    @Schema(description = "按月统计")
    private List<MonthlyStatistics> monthlyStatistics;

    @Schema(description = "按类型统计")
    private List<TypeStatistics> typeStatistics;

    @Data
    @Schema(description = "月度统计")
    public static class MonthlyStatistics {
        
        @Schema(description = "月份")
        private String month;
        
        @Schema(description = "发票数量")
        private Long count;
        
        @Schema(description = "发票金额")
        private BigDecimal amount;
    }

    @Data
    @Schema(description = "类型统计")
    public static class TypeStatistics {
        
        @Schema(description = "发票类型")
        private Integer type;
        
        @Schema(description = "类型名称")
        private String typeName;
        
        @Schema(description = "发票数量")
        private Long count;
        
        @Schema(description = "发票金额")
        private BigDecimal amount;
        
        @Schema(description = "占比")
        private BigDecimal percentage;
    }
} 