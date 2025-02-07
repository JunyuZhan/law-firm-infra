package com.lawfirm.staff.model.request.finance.settlement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "结算更新请求")
public class SettlementUpdateRequest {

    @NotNull(message = "结算ID不能为空")
    @Schema(description = "结算ID")
    private Long id;

    @Schema(description = "结算名称")
    private String name;

    @Schema(description = "结算类型")
    private Integer type;

    @Schema(description = "结算金额")
    private BigDecimal amount;

    @Schema(description = "关联案件ID")
    private Long caseId;

    @Schema(description = "关联客户ID")
    private Long clientId;

    @Schema(description = "关联费用ID列表")
    private List<Long> chargeIds;

    @Schema(description = "关联发票ID列表")
    private List<Long> invoiceIds;

    @Schema(description = "结算说明")
    private String description;

    @Schema(description = "备注")
    private String remark;
} 