package com.lawfirm.model.analysis.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 报表导出参数DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "报表导出参数DTO")
public class ReportExportDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    @Schema(description = "报表类型")
    private String reportType;
    @Schema(description = "导出格式（如excel、pdf等）")
    private String exportFormat;
    @Schema(description = "统计周期")
    private String period;
    @Schema(description = "维度")
    private String dimension;
    @Schema(description = "其他筛选条件")
    private String filter;
} 