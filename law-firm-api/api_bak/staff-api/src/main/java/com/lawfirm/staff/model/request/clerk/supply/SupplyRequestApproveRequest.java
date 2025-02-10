package com.lawfirm.staff.model.request.clerk.supply;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "办公用品申请审批请求")
public class SupplyRequestApproveRequest {

    @NotNull(message = "审批结果不能为空")
    @Schema(description = "审批结果")
    private Boolean approved;

    @Schema(description = "审批意见")
    private String comment;

    @Schema(description = "预计交付日期")
    private String estimatedDeliveryDate;
} 