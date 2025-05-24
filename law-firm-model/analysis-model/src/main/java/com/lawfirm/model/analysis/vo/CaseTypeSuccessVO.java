package com.lawfirm.model.analysis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "案件类型与成功率分析结果")
public class CaseTypeSuccessVO {
    @Schema(description = "案件类型")
    private String caseType;
    @Schema(description = "案件总数")
    private Integer totalCount;
    @Schema(description = "胜诉数")
    private Integer winCount;
    @Schema(description = "胜诉率（0-1）")
    private Double winRate;
    @Schema(description = "和解数")
    private Integer settleCount;
    @Schema(description = "败诉数")
    private Integer loseCount;
} 