package com.lawfirm.staff.model.response.finance.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 费用预算响应
 */
@Data
@Schema(description = "费用预算响应")
public class ExpenseBudgetResponse {

    @Schema(description = "预算ID")
    private Long id;

    @Schema(description = "预算名称")
    private String name;

    @Schema(description = "预算金额")
    private BigDecimal amount;

    @Schema(description = "已用金额")
    private BigDecimal usedAmount;

    @Schema(description = "剩余金额")
    private BigDecimal remainingAmount;

    @Schema(description = "使用比例(%)")
    private BigDecimal usageRate;

    @Schema(description = "费用类型ID")
    private Long categoryId;

    @Schema(description = "费用类型名称")
    private String categoryName;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Schema(description = "部门名称")
    private String departmentName;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "预警阈值(%)")
    private Integer warningThreshold;

    @Schema(description = "状态(1:正常 2:预警 3:超支)")
    private Integer status;

    @Schema(description = "状态名称")
    private String statusName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 