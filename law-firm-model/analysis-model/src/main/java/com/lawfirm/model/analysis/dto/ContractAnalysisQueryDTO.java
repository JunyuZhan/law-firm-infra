package com.lawfirm.model.analysis.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * 合同分析查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "合同分析查询参数")
public class ContractAnalysisQueryDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    @Schema(description = "合同类型")
    private String contractType;
    @Schema(description = "开始日期")
    private LocalDate startDate;
    @Schema(description = "结束日期")
    private LocalDate endDate;
    @Schema(description = "客户ID")
    private Long clientId;
    @Schema(description = "主办律师ID")
    private Long leadAttorneyId;
    @Schema(description = "合同状态")
    private String status;
} 