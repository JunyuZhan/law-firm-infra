package com.lawfirm.model.analysis.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * 财务分析查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "财务分析查询参数")
public class FinanceAnalysisQueryDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    @Schema(description = "财务类型")
    private String financeType;
    @Schema(description = "开始日期")
    private LocalDate startDate;
    @Schema(description = "结束日期")
    private LocalDate endDate;
    @Schema(description = "合同ID")
    private Long contractId;
    @Schema(description = "客户ID")
    private Long clientId;
    @Schema(description = "财务状态")
    private String status;
}