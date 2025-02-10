package com.lawfirm.finance.controller;

import com.lawfirm.finance.entity.ExpenseRecord;
import com.lawfirm.finance.service.ExpenseRecordService;
import com.lawfirm.common.core.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "支出记录管理", description = "提供支出记录的创建、审批、查询等功能")
@RestController
@RequestMapping("/api/v1/expense-records")
@RequiredArgsConstructor
public class ExpenseRecordController {

    private final ExpenseRecordService expenseRecordService;

    @Operation(summary = "创建支出记录",
            description = "创建一条新的支出记录，包括日常运营支出、人员工资等")
    @ApiResponse(responseCode = "200", description = "创建成功",
            content = @Content(schema = @Schema(implementation = ExpenseRecord.class)))
    @PostMapping
    public BaseResponse<ExpenseRecord> createExpenseRecord(
            @Parameter(description = "支出记录信息", required = true)
            @Validated @RequestBody ExpenseRecord expenseRecord) {
        return BaseResponse.success(expenseRecordService.createExpenseRecord(expenseRecord));
    }

    @Operation(summary = "审批支出",
            description = "审批通过指定的支出记录")
    @ApiResponse(responseCode = "200", description = "审批成功")
    @PostMapping("/{id}/approve")
    public BaseResponse<Void> approveExpense(
            @Parameter(description = "支出记录ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "审批人ID", required = true)
            @RequestParam Long approverId) {
        expenseRecordService.approveExpense(id, approverId);
        return BaseResponse.success();
    }

    @Operation(summary = "驳回支出",
            description = "驳回指定的支出记录")
    @ApiResponse(responseCode = "200", description = "驳回成功")
    @PostMapping("/{id}/reject")
    public BaseResponse<Void> rejectExpense(
            @Parameter(description = "支出记录ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "审批人ID", required = true)
            @RequestParam Long approverId,
            @Parameter(description = "驳回原因", required = true)
            @RequestParam String reason) {
        expenseRecordService.rejectExpense(id, approverId, reason);
        return BaseResponse.success();
    }

    @Operation(summary = "获取律所支出记录",
            description = "获取指定律所的所有支出记录")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ExpenseRecord.class)))
    @GetMapping("/law-firm/{lawFirmId}")
    public BaseResponse<List<ExpenseRecord>> getExpensesByLawFirm(
            @Parameter(description = "律所ID", required = true)
            @PathVariable Long lawFirmId) {
        return BaseResponse.success(expenseRecordService.getExpensesByLawFirm(lawFirmId));
    }

    @Operation(summary = "获取部门支出记录",
            description = "获取指定部门的所有支出记录")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ExpenseRecord.class)))
    @GetMapping("/department/{departmentId}")
    public BaseResponse<List<ExpenseRecord>> getExpensesByDepartment(
            @Parameter(description = "部门ID", required = true)
            @PathVariable Long departmentId) {
        return BaseResponse.success(expenseRecordService.getExpensesByDepartment(departmentId));
    }

    @Operation(summary = "计算总支出",
            description = "计算指定律所的总支出金额")
    @ApiResponse(responseCode = "200", description = "计算成功")
    @GetMapping("/total")
    public BaseResponse<BigDecimal> calculateTotalExpense(
            @Parameter(description = "律所ID", required = true)
            @RequestParam Long lawFirmId) {
        return BaseResponse.success(expenseRecordService.calculateTotalExpense(lawFirmId));
    }
} 