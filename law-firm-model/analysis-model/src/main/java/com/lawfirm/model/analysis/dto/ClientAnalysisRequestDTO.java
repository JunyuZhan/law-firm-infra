package com.lawfirm.model.analysis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

@Data
@Schema(description = "客户分析请求参数")
public class ClientAnalysisRequestDTO {
    @Schema(description = "开始日期")
    private Date startDate;
    @Schema(description = "结束日期")
    private Date endDate;
    @Schema(description = "客户类型，可选")
    private String clientType;
    @Schema(description = "分析维度（如行业、活跃度、风险等级等）")
    private String dimension;
} 