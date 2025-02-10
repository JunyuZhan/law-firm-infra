package com.lawfirm.staff.model.request.clerk.supply;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "办公用品申请驳回请求")
public class SupplyRequestRejectRequest {

    @NotBlank(message = "驳回原因不能为空")
    @Schema(description = "驳回原因")
    private String reason;

    @Schema(description = "备注")
    private String remark;
} 