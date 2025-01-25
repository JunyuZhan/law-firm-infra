package com.lawfirm.finance.controller;

import com.lawfirm.finance.entity.Expense;
import com.lawfirm.finance.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Tag(name = "支出记录管理", description = "支出记录的增删改查接口")
@RestController
@RequestMapping("/api/v1/finance/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @Operation(summary = "创建支出记录", description = "创建一条新的支出记录")
    @PostMapping
    public ResponseEntity<Expense> createExpense(
            @Parameter(description = "支出记录信息", required = true) 
            @Valid @RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.createExpense(expense));
    }

    @Operation(summary = "更新支出记录", description = "根据ID更新支出记录信息")
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(
            @Parameter(description = "支出记录ID", required = true) @PathVariable Long id,
            @Parameter(description = "支出记录信息", required = true) 
            @Valid @RequestBody Expense expense) {
        expense.setId(id);
        return ResponseEntity.ok(expenseService.updateExpense(expense));
    }

    @Operation(summary = "获取支出记录", description = "根据ID获取支出记录详情")
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpense(
            @Parameter(description = "支出记录ID", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpense(id));
    }

    @Operation(summary = "删除支出记录", description = "根据ID删除支出记录")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(
            @Parameter(description = "支出记录ID", required = true) @PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "分页查询支出记录", description = "分页查询所有支出记录")
    @GetMapping
    public ResponseEntity<Page<Expense>> getExpenses(
            @Parameter(description = "分页参数") Pageable pageable) {
        return ResponseEntity.ok(expenseService.findExpenses(pageable));
    }

    @Operation(summary = "查询律所支出记录", description = "根据律所ID查询相关的支出记录")
    @GetMapping("/law-firm/{lawFirmId}")
    public ResponseEntity<List<Expense>> getExpensesByLawFirmId(
            @Parameter(description = "律所ID", required = true) @PathVariable Long lawFirmId) {
        return ResponseEntity.ok(expenseService.findExpensesByLawFirmId(lawFirmId));
    }

    @Operation(summary = "查询部门支出记录", description = "根据部门ID查询相关的支出记录")
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Expense>> getExpensesByDepartmentId(
            @Parameter(description = "部门ID", required = true) @PathVariable Long departmentId) {
        return ResponseEntity.ok(expenseService.findExpensesByDepartmentId(departmentId));
    }

    @Operation(summary = "更新支出状态", description = "更新支出记录的状态")
    @PutMapping("/{id}/status")
    public ResponseEntity<Expense> updateExpenseStatus(
            @Parameter(description = "支出记录ID", required = true) @PathVariable Long id,
            @Parameter(description = "支出状态", required = true) @RequestParam String status) {
        return ResponseEntity.ok(expenseService.updateExpenseStatus(id, status));
    }

    @Operation(summary = "统计总支出", description = "统计律所的总支出金额")
    @GetMapping("/statistics/total")
    public ResponseEntity<BigDecimal> getTotalExpense(
            @Parameter(description = "律所ID", required = true) @RequestParam Long lawFirmId) {
        return ResponseEntity.ok(expenseService.calculateTotalExpense(lawFirmId));
    }

    @Operation(summary = "按类型统计支出", description = "按类型统计律所的支出金额")
    @GetMapping("/statistics/by-type")
    public ResponseEntity<BigDecimal> getExpenseByType(
            @Parameter(description = "律所ID", required = true) @RequestParam Long lawFirmId,
            @Parameter(description = "支出类型", required = true) @RequestParam String expenseType) {
        return ResponseEntity.ok(expenseService.calculateExpenseByType(lawFirmId, expenseType));
    }
} 