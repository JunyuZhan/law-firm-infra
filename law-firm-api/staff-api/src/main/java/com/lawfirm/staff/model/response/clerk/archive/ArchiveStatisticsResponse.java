package com.lawfirm.staff.model.response.clerk.archive;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 档案统计响应
 */
@Data
@Schema(description = "档案统计响应")
public class ArchiveStatisticsResponse {

    @Schema(description = "档案总数")
    private Long totalCount;

    @Schema(description = "借出数量")
    private Long borrowCount;

    @Schema(description = "逾期数量")
    private Long overdueCount;

    @Schema(description = "分类统计")
    private List<CategoryDistribution> categoryDistribution;

    @Schema(description = "月度统计")
    private List<MonthlyStatistics> monthlyStatistics;

    @Data
    @Schema(description = "分类分布")
    public static class CategoryDistribution {
        
        @Schema(description = "分类名称")
        private String categoryName;
        
        @Schema(description = "数量")
        private Long count;
        
        @Schema(description = "占比")
        private Double percentage;
    }

    @Data
    @Schema(description = "月度统计")
    public static class MonthlyStatistics {
        
        @Schema(description = "月份")
        private String month;
        
        @Schema(description = "新增数量")
        private Long newCount;
        
        @Schema(description = "借出数量")
        private Long borrowCount;
        
        @Schema(description = "归还数量")
        private Long returnCount;
    }
} 