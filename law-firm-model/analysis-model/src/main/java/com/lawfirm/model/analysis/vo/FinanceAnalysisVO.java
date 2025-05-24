package com.lawfirm.model.analysis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "财务分析结果")
public class FinanceAnalysisVO {
    @Schema(description = "分析类型（如收入、费用、利润等）")
    private String type;
    @Schema(description = "金额")
    private Double amount;
    @Schema(description = "同比增长率（%）")
    private Double yoyRate;
    @Schema(description = "环比增长率（%）")
    private Double momRate;
} 