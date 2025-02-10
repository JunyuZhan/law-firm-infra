package com.lawfirm.staff.model.request.finance.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 费用申请创建请求
 */
@Data
@Schema(description = "费用申请创建请求")
public class ExpenseCreateRequest {

    @Schema(description = "费用类型ID")
    @NotNull(message = "费用类型不能为空")
    private Long categoryId;

    @Schema(description = "费用金额")
    @NotNull(message = "费用金额不能为空")
    private BigDecimal amount;

    @Schema(description = "费用发生时间")
    @NotNull(message = "费用发生时间不能为空")
    private LocalDateTime expenseTime;

    @Schema(description = "费用说明")
    @NotBlank(message = "费用说明不能为空")
    @Size(max = 500, message = "费用说明长度不能超过500个字符")
    private String description;

    @Schema(description = "关联发票")
    private List<Long> invoiceIds;

    @Schema(description = "关联附件")
    private List<String> attachments;

    @Schema(description = "预算项目ID")
    private Long budgetItemId;

    @Schema(description = "关联案件ID")
    private Long caseId;

    @Schema(description = "关联客户ID")
    private Long clientId;

    @Schema(description = "备注")
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
} 