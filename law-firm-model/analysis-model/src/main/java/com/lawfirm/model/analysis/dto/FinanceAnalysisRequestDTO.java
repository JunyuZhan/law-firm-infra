package com.lawfirm.model.analysis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

@Data
@Schema(description = "财务分析请求参数")
public class FinanceAnalysisRequestDTO {
    @Schema(description = "开始日期")
    private Date startDate;
    @Schema(description = "结束日期")
    private Date endDate;
    @Schema(description = "部门ID，可选")
    private Long departmentId;
    @Schema(description = "分析类型（如收入、费用、利润等）")
    private String analysisType;
} 