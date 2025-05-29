package com.lawfirm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.finance.constant.FinanceConstants;
import com.lawfirm.model.finance.dto.ContractIncomeStat;
import com.lawfirm.model.finance.entity.Income;
import com.lawfirm.model.finance.enums.IncomeTypeEnum;
import com.lawfirm.model.finance.service.IncomeService;
import com.lawfirm.model.finance.service.IncomeService.IncomeStat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController("incomeController")
@RequiredArgsConstructor
@RequestMapping(FinanceConstants.API_INCOME_PREFIX)
@Tag(name = "收入管理", description = "收入相关接口")
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping
    @Operation(summary = "记录收入")
    @PreAuthorize("hasAuthority('" + FINANCE_CREATE + "')")
    public CommonResult<Long> recordIncome(@Valid @RequestBody Income income) {
        return CommonResult.success(incomeService.recordIncome(income));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新收入")
    @PreAuthorize("hasAuthority('" + FINANCE_EDIT + "')")
    public CommonResult<Boolean> updateIncome(@PathVariable("id") Long id, 
                                              @Valid @RequestBody Income income) {
        income.setId(id);
        return CommonResult.success(incomeService.updateIncome(income));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除收入")
    @PreAuthorize("hasAuthority('" + FINANCE_DELETE + "')")
    public CommonResult<Boolean> deleteIncome(@PathVariable("id") Long id) {
        return CommonResult.success(incomeService.deleteIncome(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取收入详情")
    @PreAuthorize("hasAuthority('" + FINANCE_VIEW + "')")
    public CommonResult<Income> getIncome(@PathVariable("id") Long id) {
        return CommonResult.success(incomeService.getIncomeById(id));
    }

    @PutMapping("/{id}/confirm")
    @Operation(summary = "确认收入")
    @PreAuthorize("hasAuthority('" + FINANCE_EDIT + "')")
    public CommonResult<Boolean> confirmIncome(
            @PathVariable("id") Long id,
            @Parameter(description = "确认人ID") @RequestParam Long confirmerId,
            @Parameter(description = "确认备注") @RequestParam(required = false) String remark) {
        return CommonResult.success(incomeService.confirmIncome(id, confirmerId, remark));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "取消收入")
    @PreAuthorize("hasAuthority('" + FINANCE_EDIT + "')")
    public CommonResult<Boolean> cancelIncome(
            @PathVariable("id") Long id,
            @Parameter(description = "取消原因") @RequestParam String reason) {
        return CommonResult.success(incomeService.cancelIncome(id, reason));
    }

    @GetMapping("/list")
    @Operation(summary = "查询收入列表")
    @PreAuthorize("hasAuthority('" + FINANCE_VIEW + "')")
    public CommonResult<List<Income>> listIncomes(
            @Parameter(description = "收入类型") @RequestParam(required = false) Integer incomeType,
            @Parameter(description = "合同ID") @RequestParam(required = false) Long contractId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(incomeService.listIncomes(incomeType, contractId, startTime, endTime));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询收入")
    @PreAuthorize("hasAuthority('" + FINANCE_VIEW + "')")
    public CommonResult<IPage<Income>> pageIncomes(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "收入类型") @RequestParam(required = false) Integer incomeType,
            @Parameter(description = "合同ID") @RequestParam(required = false) Long contractId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Page<Income> page = new Page<>(current, size);
        return CommonResult.success(incomeService.pageIncomes(page, incomeType, contractId, startTime, endTime));
    }

    @GetMapping("/contract/{contractId}")
    @Operation(summary = "按合同查询收入")
    @PreAuthorize("hasAuthority('" + FINANCE_VIEW + "')")
    public CommonResult<List<Income>> listIncomesByContract(@PathVariable("contractId") Long contractId) {
        return CommonResult.success(incomeService.listIncomesByContract(contractId));
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "按客户查询收入")
    @PreAuthorize("hasAuthority('" + FINANCE_VIEW + "')")
    public CommonResult<List<Income>> listIncomesByClient(
            @PathVariable("clientId") Long clientId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(incomeService.listIncomesByClient(clientId, startTime, endTime));
    }

    @GetMapping("/sum")
    @Operation(summary = "统计收入总额")
    @PreAuthorize("hasAuthority('" + FINANCE_VIEW + "')")
    public CommonResult<BigDecimal> sumIncomeAmount(
            @Parameter(description = "收入类型") @RequestParam(required = false) Integer incomeType,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(incomeService.sumIncomeAmount(incomeType, startTime, endTime));
    }

    @GetMapping("/sum/contract/{contractId}")
    @Operation(summary = "统计合同收入总额")
    @PreAuthorize("hasAuthority('" + FINANCE_VIEW + "')")
    public CommonResult<BigDecimal> sumContractIncomeAmount(@PathVariable("contractId") Long contractId) {
        return CommonResult.success(incomeService.sumContractIncomeAmount(contractId));
    }

    @GetMapping("/statistics/month")
    @Operation(summary = "按月统计收入")
    @PreAuthorize("hasAuthority('" + FINANCE_VIEW + "')")
    public CommonResult<List<IncomeStat>> statisticIncomeByMonth(
            @Parameter(description = "年份") @RequestParam Integer year,
            @Parameter(description = "月份") @RequestParam(required = false) Integer month) {
        return CommonResult.success(incomeService.statisticIncomeByMonth(year, month));
    }

    @GetMapping("/statistics/contract")
    @Operation(summary = "按合同统计收入")
    @PreAuthorize("hasAuthority('" + FINANCE_VIEW + "')")
    public CommonResult<List<ContractIncomeStat>> statisticIncomeByContract() {
        return CommonResult.success(incomeService.statisticIncomeByContract());
    }
}
