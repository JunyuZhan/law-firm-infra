package com.lawfirm.model.analysis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "律师绩效分析结果")
public class LawyerPerformanceVO {
    @Schema(description = "律师ID")
    private Long lawyerId;
    @Schema(description = "律师姓名")
    private String lawyerName;
    @Schema(description = "案件数量")
    private Integer caseCount;
    @Schema(description = "总工时")
    private Double totalWorkHours;
    @Schema(description = "结案率（0-1）")
    private Double closeRate;
} 