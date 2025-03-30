package com.lawfirm.finance.controller;

import com.lawfirm.common.core.response.ResponseResult;
import com.lawfirm.model.finance.entity.Income;
import com.lawfirm.model.finance.entity.Invoice;
import com.lawfirm.model.finance.entity.PaymentPlan;
import com.lawfirm.model.finance.entity.Receivable;
import com.lawfirm.model.finance.service.ContractFinanceService;
import com.lawfirm.model.finance.vo.contract.ContractFinanceVO;
import com.lawfirm.model.finance.vo.receivable.ReceivableDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 合同财务管理控制器
 */
@Slf4j
@Tag(name = "合同财务管理", description = "合同财务管理接口")
@RestController("contractFinanceController")
@RequestMapping("/finance/contract")
@RequiredArgsConstructor
public class ContractFinanceController {

    private final ContractFinanceService contractFinanceService;

    @GetMapping("/{contractId}/finance-info")
    @Operation(summary = "获取合同财务信息")
    public ResponseResult<ContractFinanceVO> getContractFinanceInfo(@PathVariable("contractId") Long contractId) {
        return ResponseResult.success(contractFinanceService.getContractFinanceInfo(contractId));
    }

    @PutMapping("/{contractId}/amount")
    @Operation(summary = "更新合同实际金额")
    public ResponseResult<Boolean> updateContractAmount(
            @PathVariable("contractId") Long contractId,
            @Parameter(description = "实际金额") @RequestParam BigDecimal actualAmount,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return ResponseResult.success(contractFinanceService.updateContractAmount(contractId, actualAmount, remark));
    }

    @PutMapping("/{contractId}/tax-rate")
    @Operation(summary = "更新合同税率")
    public ResponseResult<Boolean> updateContractTaxRate(
            @PathVariable("contractId") Long contractId,
            @Parameter(description = "税率") @RequestParam BigDecimal taxRate,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return ResponseResult.success(contractFinanceService.updateContractTaxRate(contractId, taxRate, remark));
    }

    @GetMapping("/{contractId}/payment-plan")
    @Operation(summary = "获取合同收款计划")
    public ResponseResult<Object> getContractPaymentPlan(@PathVariable("contractId") Long contractId) {
        return ResponseResult.success(contractFinanceService.getContractPaymentPlan(contractId));
    }

    @PutMapping("/{contractId}/payment-plan")
    @Operation(summary = "更新合同收款计划")
    public ResponseResult<Boolean> updateContractPaymentPlan(
            @PathVariable("contractId") Long contractId,
            @Parameter(description = "计划数据") @RequestBody Object planData) {
        return ResponseResult.success(contractFinanceService.updateContractPaymentPlan(contractId, planData));
    }

    @GetMapping("/{contractId}/invoices")
    @Operation(summary = "获取合同发票列表")
    public ResponseResult<List<Invoice>> getContractInvoices(@PathVariable("contractId") Long contractId) {
        return ResponseResult.success(contractFinanceService.getContractInvoiceObjects(contractId));
    }

    @GetMapping("/{contractId}/audit-logs")
    @Operation(summary = "获取合同财务审计记录")
    public ResponseResult<List<Object>> getContractFinanceAuditLogs(
            @PathVariable("contractId") Long contractId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        return ResponseResult.success(contractFinanceService.getContractFinanceAuditLogs(contractId, startDateTime, endDateTime));
    }

    @PostMapping("/{contractId}/export")
    @Operation(summary = "导出合同财务报表")
    public ResponseResult<String> exportContractFinanceReport(@PathVariable("contractId") Long contractId) {
        return ResponseResult.success(contractFinanceService.exportContractFinanceReport(contractId));
    }

    @PostMapping("/{contractId}/receivables")
    @Operation(summary = "创建合同应收账款")
    public ResponseResult<Long> createContractReceivable(
            @PathVariable("contractId") Long contractId,
            @Parameter(description = "合同编号") @RequestParam String contractNo,
            @Parameter(description = "合同金额") @RequestParam BigDecimal amount,
            @Parameter(description = "客户ID") @RequestParam Long clientId) {
        return ResponseResult.success(contractFinanceService.createContractReceivable(contractId, contractNo, amount, clientId));
    }

    @PutMapping("/{contractId}/receivables")
    @Operation(summary = "更新合同应收账款")
    public ResponseResult<Boolean> updateContractReceivable(
            @PathVariable("contractId") Long contractId,
            @Parameter(description = "合同编号") @RequestParam String contractNo,
            @Parameter(description = "更新金额") @RequestParam BigDecimal updateAmount) {
        return ResponseResult.success(contractFinanceService.updateContractReceivable(contractId, contractNo, updateAmount));
    }

    @PostMapping("/{contractId}/payment-plans")
    @Operation(summary = "创建合同收款计划")
    public ResponseResult<Long> createPaymentPlan(
            @PathVariable("contractId") Long contractId,
            @Parameter(description = "合同编号") @RequestParam String contractNo,
            @Parameter(description = "计划名称") @RequestParam String planName,
            @Parameter(description = "总金额") @RequestParam BigDecimal totalAmount,
            @Parameter(description = "期数") @RequestParam Integer installments,
            @Parameter(description = "客户ID") @RequestParam Long clientId) {
        return ResponseResult.success(contractFinanceService.createPaymentPlan(contractId, contractNo, planName, totalAmount, installments, clientId));
    }

    @PostMapping("/{contractId}/incomes")
    @Operation(summary = "记录合同收款")
    public ResponseResult<Long> recordContractIncome(
            @PathVariable("contractId") Long contractId,
            @Parameter(description = "合同编号") @RequestParam String contractNo,
            @Parameter(description = "收款金额") @RequestParam BigDecimal amount,
            @Parameter(description = "收款账户ID") @RequestParam Long accountId,
            @Parameter(description = "说明") @RequestParam(required = false) String description) {
        return ResponseResult.success(contractFinanceService.recordContractIncome(contractId, contractNo, amount, accountId, description));
    }

    @PostMapping("/{contractId}/invoices")
    @Operation(summary = "处理合同发票申请")
    public ResponseResult<Long> processContractInvoice(
            @PathVariable("contractId") Long contractId,
            @Parameter(description = "合同编号") @RequestParam String contractNo,
            @Parameter(description = "开票金额") @RequestParam BigDecimal amount,
            @Parameter(description = "说明") @RequestParam(required = false) String description) {
        return ResponseResult.success(contractFinanceService.processContractInvoice(contractId, contractNo, amount, description));
    }

    @GetMapping("/{contractId}/receivables")
    @Operation(summary = "获取合同应收账款")
    public ResponseResult<List<ReceivableDetailVO>> getContractReceivables(@PathVariable("contractId") Long contractId) {
        return ResponseResult.success(contractFinanceService.getContractReceivables(contractId));
    }

    @GetMapping("/{contractId}/incomes")
    @Operation(summary = "获取合同收款记录")
    public ResponseResult<List<Income>> getContractIncomes(@PathVariable("contractId") Long contractId) {
        return ResponseResult.success(contractFinanceService.getContractIncomes(contractId));
    }

    @GetMapping("/{contractId}/payment-plans")
    @Operation(summary = "获取合同收款计划")
    public ResponseResult<List<PaymentPlan>> getContractPaymentPlans(@PathVariable("contractId") Long contractId) {
        return ResponseResult.success(contractFinanceService.getContractPaymentPlans(contractId));
    }

    @GetMapping("/{contractId}/summary")
    @Operation(summary = "统计合同财务状况")
    public ResponseResult<ContractFinanceService.ContractFinanceSummary> getContractFinanceSummary(
            @PathVariable("contractId") Long contractId) {
        return ResponseResult.success(contractFinanceService.getContractFinanceSummary(contractId));
    }
}
