package com.lawfirm.staff.model.request.finance.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 费用驳回请求
 */
@Data
@Schema(description = "费用驳回请求")
public class ExpenseRejectRequest {

    @Schema(description = "驳回原因")
    @NotBlank(message = "驳回原因不能为空")
    @Size(max = 500, message = "驳回原因长度不能超过500个字符")
    private String rejectReason;
} 