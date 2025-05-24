package com.lawfirm.model.analysis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

@Data
@Schema(description = "律师绩效分析请求参数")
public class LawyerPerformanceRequestDTO {
    @Schema(description = "开始日期")
    private Date startDate;
    @Schema(description = "结束日期")
    private Date endDate;
    @Schema(description = "团队ID，可选")
    private Long teamId;
    @Schema(description = "排序方式（如案件数/工时/结案率等）")
    private String orderBy;
} 