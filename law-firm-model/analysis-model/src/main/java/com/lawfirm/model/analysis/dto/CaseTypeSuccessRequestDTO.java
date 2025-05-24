package com.lawfirm.model.analysis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

@Data
@Schema(description = "案件类型与成功率分析请求参数")
public class CaseTypeSuccessRequestDTO {
    @Schema(description = "开始日期")
    private Date startDate;
    @Schema(description = "结束日期")
    private Date endDate;
    @Schema(description = "案件类型，可选")
    private String caseType;
    @Schema(description = "团队ID，可选")
    private Long teamId;
} 