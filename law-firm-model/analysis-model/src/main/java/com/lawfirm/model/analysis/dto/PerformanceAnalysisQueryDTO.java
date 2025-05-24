package com.lawfirm.model.analysis.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * 绩效分析查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "绩效分析查询参数")
public class PerformanceAnalysisQueryDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    @Schema(description = "人员ID")
    private Long personnelId;
    @Schema(description = "部门")
    private String department;
    @Schema(description = "开始日期")
    private LocalDate startDate;
    @Schema(description = "结束日期")
    private LocalDate endDate;
    @Schema(description = "绩效类型")
    private String performanceType;
} 