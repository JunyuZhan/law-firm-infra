package com.lawfirm.staff.model.request.finance;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "作废发票请求")
public class VoidInvoiceRequest {

    @NotNull(message = "发票ID不能为空")
    @Schema(description = "发票ID")
    private Long id;

    @NotBlank(message = "作废原因不能为空")
    @Schema(description = "作废原因")
    private String reason;
} 