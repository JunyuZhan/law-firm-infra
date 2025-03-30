package com.lawfirm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.response.ResponseResult;
import com.lawfirm.model.finance.entity.Budget;
import com.lawfirm.model.finance.enums.BudgetStatusEnum;
import com.lawfirm.model.finance.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Tag(name = "预算管理", description = "预算管理接口")
@RestController("budgetController")
@RequestMapping("/finance/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    @Operation(summary = "创建预算")
    public ResponseResult<Long> createBudget(@Valid @RequestBody Budget budget) {
        return ResponseResult.success(budgetService.createBudget(budget));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新预算")
    public ResponseResult<Boolean> updateBudget(@PathVariable("id") Long id, 
                                          @Valid @RequestBody Budget budget) {
        budget.setId(id);
        return ResponseResult.success(budgetService.updateBudget(budget));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除预算")
    public ResponseResult<Boolean> deleteBudget(@PathVariable("id") Long id) {
        return ResponseResult.success(budgetService.deleteBudget(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取预算详情")
    public ResponseResult<Budget> getBudget(@PathVariable("id") Long id) {
        return ResponseResult.success(budgetService.getBudgetById(id));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新预算状态")
    public ResponseResult<Boolean> updateBudgetStatus(
            @PathVariable("id") Long id,
            @Parameter(description = "预算状态") @RequestParam BudgetStatusEnum status,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return ResponseResult.success(budgetService.updateBudgetStatus(id, status, remark));
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "审批预算")
    public ResponseResult<Boolean> approveBudget(
            @PathVariable("id") Long id,
            @Parameter(description = "是否通过") @RequestParam boolean approved,
            @Parameter(description = "审批人ID") @RequestParam Long approverId,
            @Parameter(description = "审批意见") @RequestParam(required = false) String remark) {
        return ResponseResult.success(budgetService.approveBudget(id, approved, approverId, remark));
    }

    @PutMapping("/{id}/adjust")
    @Operation(summary = "调整预算")
    public ResponseResult<Boolean> adjustBudget(
            @PathVariable("id") Long id,
            @Parameter(description = "调整金额") @RequestParam BigDecimal amount,
            @Parameter(description = "操作人ID") @RequestParam Long operatorId,
            @Parameter(description = "调整原因") @RequestParam(required = false) String remark) {
        return ResponseResult.success(budgetService.adjustBudget(id, amount, operatorId, remark));
    }

    @GetMapping("/list")
    @Operation(summary = "查询预算列表")
    public ResponseResult<List<Budget>> listBudgets(
            @Parameter(description = "预算状态") @RequestParam(required = false) BudgetStatusEnum status,
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId,
            @Parameter(description = "成本中心ID") @RequestParam(required = false) Long costCenterId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(budgetService.listBudgets(status, departmentId, costCenterId, startTime, endTime));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询预算")
    public ResponseResult<IPage<Budget>> pageBudgets(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "预算状态") @RequestParam(required = false) BudgetStatusEnum status,
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId,
            @Parameter(description = "成本中心ID") @RequestParam(required = false) Long costCenterId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Page<Budget> page = new Page<>(current, size);
        return ResponseResult.success(budgetService.pageBudgets(page, status, departmentId, costCenterId, startTime, endTime));
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "按部门查询预算")
    public ResponseResult<List<Budget>> listBudgetsByDepartment(
            @PathVariable("departmentId") Long departmentId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(budgetService.listBudgetsByDepartment(departmentId, startTime, endTime));
    }

    @GetMapping("/cost-center/{costCenterId}")
    @Operation(summary = "按成本中心查询预算")
    public ResponseResult<List<Budget>> listBudgetsByCostCenter(
            @PathVariable("costCenterId") Long costCenterId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(budgetService.listBudgetsByCostCenter(costCenterId, startTime, endTime));
    }

    @GetMapping("/sum")
    @Operation(summary = "统计预算金额")
    public ResponseResult<BigDecimal> sumBudgetAmount(
            @Parameter(description = "预算状态") @RequestParam(required = false) BudgetStatusEnum status,
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId,
            @Parameter(description = "成本中心ID") @RequestParam(required = false) Long costCenterId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(budgetService.sumBudgetAmount(status, departmentId, costCenterId, startTime, endTime));
    }

    @GetMapping("/sum/department/{departmentId}")
    @Operation(summary = "统计部门预算金额")
    public ResponseResult<BigDecimal> sumDepartmentBudgetAmount(
            @PathVariable("departmentId") Long departmentId,
            @Parameter(description = "预算状态") @RequestParam(required = false) BudgetStatusEnum status,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(budgetService.sumDepartmentBudgetAmount(departmentId, status, startTime, endTime));
    }

    @GetMapping("/sum/cost-center/{costCenterId}")
    @Operation(summary = "统计成本中心预算金额")
    public ResponseResult<BigDecimal> sumCostCenterBudgetAmount(
            @PathVariable("costCenterId") Long costCenterId,
            @Parameter(description = "预算状态") @RequestParam(required = false) BudgetStatusEnum status,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(budgetService.sumCostCenterBudgetAmount(costCenterId, status, startTime, endTime));
    }

    @GetMapping("/export")
    @Operation(summary = "导出预算数据")
    public ResponseResult<String> exportBudgets(
            @Parameter(description = "预算ID列表") @RequestParam List<Long> budgetIds) {
        return ResponseResult.success(budgetService.exportBudgets(budgetIds));
    }
}
