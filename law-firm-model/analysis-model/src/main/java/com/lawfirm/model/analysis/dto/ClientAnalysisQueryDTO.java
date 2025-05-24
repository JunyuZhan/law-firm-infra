package com.lawfirm.model.analysis.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * 客户分析查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "客户分析查询参数")
public class ClientAnalysisQueryDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    @Schema(description = "客户类型")
    private String clientType;
    @Schema(description = "客户等级")
    private String clientLevel;
    @Schema(description = "开始日期")
    private LocalDate startDate;
    @Schema(description = "结束日期")
    private LocalDate endDate;
    @Schema(description = "行业")
    private String industry;
    @Schema(description = "地区")
    private String region;
} 