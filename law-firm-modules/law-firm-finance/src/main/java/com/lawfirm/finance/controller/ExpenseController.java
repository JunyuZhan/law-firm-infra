package com.lawfirm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.response.ResponseResult;
import com.lawfirm.model.finance.entity.Expense;
import com.lawfirm.model.finance.service.ExpenseService;
import com.lawfirm.model.finance.service.ExpenseService.ExpenseStat;
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

/**
 * 支出管理控制器
 */
@Slf4j
@RestController("expenseController")
@RequiredArgsConstructor
@RequestMapping("/finance/expense")
@Tag(name = "支出管理", description = "支出相关接口")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    @Operation(summary = "记录支出")
    public ResponseResult<Long> recordExpense(@Valid @RequestBody Expense expense) {
        return ResponseResult.success(expenseService.recordExpense(expense));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新支出")
    public ResponseResult<Boolean> updateExpense(@PathVariable("id") Long id, 
                                               @Valid @RequestBody Expense expense) {
        expense.setId(id);
        return ResponseResult.success(expenseService.updateExpense(expense));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除支出")
    public ResponseResult<Boolean> deleteExpense(@PathVariable("id") Long id) {
        return ResponseResult.success(expenseService.deleteExpense(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取支出详情")
    public ResponseResult<Expense> getExpense(@PathVariable("id") Long id) {
        return ResponseResult.success(expenseService.getExpenseById(id));
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "审批支出")
    public ResponseResult<Boolean> approveExpense(
            @PathVariable("id") Long id,
            @Parameter(description = "审批人ID") @RequestParam Long approverId,
            @Parameter(description = "是否通过") @RequestParam boolean approved,
            @Parameter(description = "审批备注") @RequestParam(required = false) String remark) {
        return ResponseResult.success(expenseService.approveExpense(id, approverId, approved, remark));
    }

    @PutMapping("/{id}/pay")
    @Operation(summary = "支付支出")
    public ResponseResult<Boolean> payExpense(
            @PathVariable("id") Long id,
            @Parameter(description = "支付账户ID") @RequestParam Long accountId,
            @Parameter(description = "支付时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime paymentTime,
            @Parameter(description = "支付备注") @RequestParam(required = false) String remark) {
        return ResponseResult.success(expenseService.payExpense(id, accountId, paymentTime, remark));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "取消支出")
    public ResponseResult<Boolean> cancelExpense(
            @PathVariable("id") Long id,
            @Parameter(description = "取消原因") @RequestParam String reason) {
        return ResponseResult.success(expenseService.cancelExpense(id, reason));
    }

    @GetMapping("/list")
    @Operation(summary = "查询支出列表")
    public ResponseResult<List<Expense>> listExpenses(
            @Parameter(description = "支出类型") @RequestParam(required = false) Integer expenseType,
            @Parameter(description = "支出状态") @RequestParam(required = false) Integer status,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(expenseService.listExpenses(expenseType, status, startTime, endTime));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询支出")
    public ResponseResult<IPage<Expense>> pageExpenses(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "支出类型") @RequestParam(required = false) Integer expenseType,
            @Parameter(description = "支出状态") @RequestParam(required = false) Integer status,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Page<Expense> page = new Page<>(current, size);
        return ResponseResult.success(expenseService.pageExpenses(page, expenseType, status, startTime, endTime));
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "按部门查询支出")
    public ResponseResult<List<Expense>> listExpensesByDepartment(
            @PathVariable("departmentId") Long departmentId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(expenseService.listExpensesByDepartment(departmentId, startTime, endTime));
    }

    @GetMapping("/cost-center/{costCenterId}")
    @Operation(summary = "按成本中心查询支出")
    public ResponseResult<List<Expense>> listExpensesByCostCenter(
            @PathVariable("costCenterId") Long costCenterId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(expenseService.listExpensesByCostCenter(costCenterId, startTime, endTime));
    }

    @GetMapping("/sum")
    @Operation(summary = "统计支出总额")
    public ResponseResult<BigDecimal> sumExpenseAmount(
            @Parameter(description = "支出类型") @RequestParam(required = false) Integer expenseType,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(expenseService.sumExpenseAmount(expenseType, startTime, endTime));
    }

    @GetMapping("/statistics/month")
    @Operation(summary = "按月统计支出")
    public ResponseResult<List<ExpenseStat>> statisticExpenseByMonth(
            @Parameter(description = "年份") @RequestParam Integer year,
            @Parameter(description = "月份") @RequestParam(required = false) Integer month) {
        return ResponseResult.success(expenseService.statisticExpenseByMonth(year, month));
    }

    @GetMapping("/statistics/type")
    @Operation(summary = "按类型统计支出")
    public ResponseResult<List<ExpenseStat>> statisticExpenseByType(
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(expenseService.statisticExpenseByType(startTime, endTime));
    }
}