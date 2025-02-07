package com.lawfirm.staff.model.request.finance.invoice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 发票审核请求
 */
@Data
@Schema(description = "发票审核请求")
public class InvoiceApproveRequest {

    @Schema(description = "审核意见")
    @NotBlank(message = "审核意见不能为空")
    @Size(max = 500, message = "审核意见长度不能超过500个字符")
    private String approveRemark;
} 