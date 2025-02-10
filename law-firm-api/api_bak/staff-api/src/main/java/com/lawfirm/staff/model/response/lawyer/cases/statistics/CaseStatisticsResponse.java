package com.lawfirm.staff.model.response.lawyer.cases.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "案件统计响应")
public class CaseStatisticsResponse {
    
    @Schema(description = "案件总数")
    private Long totalCount;
    
    @Schema(description = "进行中案件数")
    private Long processingCount;
    
    @Schema(description = "已结案数")
    private Long closedCount;
    
    @Schema(description = "本月新增案件数")
    private Long monthlyNewCount;
    
    @Schema(description = "本月结案数")
    private Long monthlyClosedCount;
    
    @Schema(description = "本月收费总额")
    private BigDecimal monthlyFeeAmount;
    
    @Schema(description = "案件类型分布")
    private List<TypeDistribution> typeDistributions;
    
    @Schema(description = "案件进度分布")
    private List<ProgressDistribution> progressDistributions;
    
    @Schema(description = "案件来源分布")
    private List<SourceDistribution> sourceDistributions;
    
    @Data
    @Schema(description = "类型分布")
    public static class TypeDistribution {
        @Schema(description = "类型")
        private Integer type;
        
        @Schema(description = "类型名称")
        private String typeName;
        
        @Schema(description = "数量")
        private Long count;
        
        @Schema(description = "占比")
        private BigDecimal percentage;
    }
    
    @Data
    @Schema(description = "进度分布")
    public static class ProgressDistribution {
        @Schema(description = "进度阶段")
        private Integer stage;
        
        @Schema(description = "进度阶段名称")
        private String stageName;
        
        @Schema(description = "数量")
        private Long count;
        
        @Schema(description = "占比")
        private BigDecimal percentage;
    }
    
    @Data
    @Schema(description = "来源分布")
    public static class SourceDistribution {
        @Schema(description = "来源")
        private Integer source;
        
        @Schema(description = "来源名称")
        private String sourceName;
        
        @Schema(description = "数量")
        private Long count;
        
        @Schema(description = "占比")
        private BigDecimal percentage;
    }
} 