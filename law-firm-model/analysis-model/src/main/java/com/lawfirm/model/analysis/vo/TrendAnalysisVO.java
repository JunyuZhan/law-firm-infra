package com.lawfirm.model.analysis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 趋势分析VO
 */
@Data
@Schema(description = "趋势分析结果")
public class TrendAnalysisVO {
    @Schema(description = "时间点（如2024-05、2024-Q1等）")
    private String timePoint;
    @Schema(description = "数值")
    private Double value;
    @Schema(description = "同比增长率（%）")
    private Double yoyRate;
    @Schema(description = "环比增长率（%）")
    private Double momRate;
} 