package com.lawfirm.staff.model.request.finance.invoice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 发票驳回请求
 */
@Data
@Schema(description = "发票驳回请求")
public class InvoiceRejectRequest {

    @Schema(description = "驳回原因")
    @NotBlank(message = "驳回原因不能为空")
    @Size(max = 500, message = "驳回原因长度不能超过500个字符")
    private String rejectReason;
} 