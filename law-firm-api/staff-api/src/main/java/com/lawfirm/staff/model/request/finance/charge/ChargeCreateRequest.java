package com.lawfirm.staff.model.request.finance.charge;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "费用创建请求")
public class ChargeCreateRequest {

    @NotBlank(message = "费用名称不能为空")
    @Schema(description = "费用名称")
    private String name;

    @NotNull(message = "费用类型不能为空")
    @Schema(description = "费用类型")
    private Integer type;

    @NotNull(message = "费用金额不能为空")
    @Schema(description = "费用金额")
    private BigDecimal amount;

    @Schema(description = "关联案件ID")
    private Long caseId;

    @Schema(description = "关联客户ID")
    private Long clientId;

    @Schema(description = "费用说明")
    private String description;

    @Schema(description = "备注")
    private String remark;
} 