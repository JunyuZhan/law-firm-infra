package com.lawfirm.staff.model.request.finance.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 费用预算创建请求
 */
@Data
@Schema(description = "费用预算创建请求")
public class ExpenseBudgetCreateRequest {

    @Schema(description = "预算名称")
    @NotBlank(message = "预算名称不能为空")
    @Size(max = 100, message = "预算名称长度不能超过100个字符")
    private String name;

    @Schema(description = "预算金额")
    @NotNull(message = "预算金额不能为空")
    private BigDecimal amount;

    @Schema(description = "费用类型ID")
    @NotNull(message = "费用类型不能为空")
    private Long categoryId;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Schema(description = "开始时间")
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    @Schema(description = "预警阈值(%)")
    private Integer warningThreshold;

    @Schema(description = "备注")
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
} 