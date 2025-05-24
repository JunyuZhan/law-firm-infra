package com.lawfirm.model.analysis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

@Data
@Schema(description = "趋势分析请求参数")
public class TrendAnalysisRequestDTO {
    @Schema(description = "开始日期")
    private Date startDate;
    @Schema(description = "结束日期")
    private Date endDate;
    @Schema(description = "分析对象（如案件、收入、客户等）")
    private String target;
    @Schema(description = "分析维度（如类型、部门等）")
    private String dimension;
    @Schema(description = "时间粒度（如day、month、quarter、year）")
    private String granularity;
} 