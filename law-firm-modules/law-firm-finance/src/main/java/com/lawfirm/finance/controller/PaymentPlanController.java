package com.lawfirm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.response.ResponseResult;
import com.lawfirm.model.finance.entity.PaymentPlan;
import com.lawfirm.model.finance.enums.PaymentPlanStatusEnum;
import com.lawfirm.model.finance.service.PaymentPlanService;
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
 * 付款计划管理控制器
 */
@Slf4j
@RestController("paymentPlanController")
@RequiredArgsConstructor
@RequestMapping("/api/finance/payment-plan")
@Tag(name = "付款计划管理", description = "付款计划相关接口")
public class PaymentPlanController {

    private final PaymentPlanService paymentPlanService;

    @PostMapping
    @Operation(summary = "创建付款计划")
    public ResponseResult<Long> createPaymentPlan(@Valid @RequestBody PaymentPlan paymentPlan) {
        return ResponseResult.success(paymentPlanService.createPaymentPlan(paymentPlan));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新付款计划")
    public ResponseResult<Boolean> updatePaymentPlan(@PathVariable("id") Long id, 
                                                   @Valid @RequestBody PaymentPlan paymentPlan) {
        paymentPlan.setId(id);
        return ResponseResult.success(paymentPlanService.updatePaymentPlan(paymentPlan));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除付款计划")
    public ResponseResult<Boolean> deletePaymentPlan(@PathVariable("id") Long id) {
        return ResponseResult.success(paymentPlanService.deletePaymentPlan(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取付款计划详情")
    public ResponseResult<PaymentPlan> getPaymentPlan(@PathVariable("id") Long id) {
        return ResponseResult.success(paymentPlanService.getPaymentPlanById(id));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新付款计划状态")
    public ResponseResult<Boolean> updatePaymentPlanStatus(
            @PathVariable("id") Long id,
            @Parameter(description = "状态") @RequestParam PaymentPlanStatusEnum status,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return ResponseResult.success(paymentPlanService.updatePaymentPlanStatus(id, status, remark));
    }

    @PostMapping("/{id}/confirm")
    @Operation(summary = "确认付款")
    public ResponseResult<Boolean> confirmPayment(
            @PathVariable("id") Long id,
            @Parameter(description = "实际付款金额") @RequestParam BigDecimal actualAmount,
            @Parameter(description = "付款时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime paymentTime,
            @Parameter(description = "操作人ID") @RequestParam Long operatorId,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return ResponseResult.success(paymentPlanService.confirmPayment(id, actualAmount, paymentTime, operatorId, remark));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "取消付款计划")
    public ResponseResult<Boolean> cancelPaymentPlan(
            @PathVariable("id") Long id,
            @Parameter(description = "取消原因") @RequestParam String reason) {
        return ResponseResult.success(paymentPlanService.cancelPaymentPlan(id, reason));
    }

    @GetMapping("/list")
    @Operation(summary = "查询付款计划列表")
    public ResponseResult<List<PaymentPlan>> listPaymentPlans(
            @Parameter(description = "状态") @RequestParam(required = false) PaymentPlanStatusEnum status,
            @Parameter(description = "合同ID") @RequestParam(required = false) Long contractId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(paymentPlanService.listPaymentPlans(status, contractId, startTime, endTime));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询付款计划")
    public ResponseResult<IPage<PaymentPlan>> pagePaymentPlans(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "状态") @RequestParam(required = false) PaymentPlanStatusEnum status,
            @Parameter(description = "合同ID") @RequestParam(required = false) Long contractId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Page<PaymentPlan> page = new Page<>(current, size);
        return ResponseResult.success(paymentPlanService.pagePaymentPlans(page, status, contractId, startTime, endTime));
    }

    @GetMapping("/contract/{contractId}")
    @Operation(summary = "按合同查询付款计划")
    public ResponseResult<List<PaymentPlan>> listPaymentPlansByContract(@PathVariable("contractId") Long contractId) {
        return ResponseResult.success(paymentPlanService.listPaymentPlansByContract(contractId));
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "按客户查询付款计划")
    public ResponseResult<List<PaymentPlan>> listPaymentPlansByClient(
            @PathVariable("clientId") Long clientId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(paymentPlanService.listPaymentPlansByClient(clientId, startTime, endTime));
    }

    @GetMapping("/sum")
    @Operation(summary = "统计付款计划金额")
    public ResponseResult<BigDecimal> sumPaymentPlanAmount(
            @Parameter(description = "状态") @RequestParam(required = false) PaymentPlanStatusEnum status,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(paymentPlanService.sumPaymentPlanAmount(status, startTime, endTime));
    }

    @GetMapping("/sum/contract/{contractId}")
    @Operation(summary = "统计合同付款计划金额")
    public ResponseResult<BigDecimal> sumContractPaymentPlanAmount(
            @PathVariable("contractId") Long contractId,
            @Parameter(description = "状态") @RequestParam(required = false) PaymentPlanStatusEnum status) {
        return ResponseResult.success(paymentPlanService.sumContractPaymentPlanAmount(contractId, status));
    }

    @PostMapping("/export")
    @Operation(summary = "导出付款计划数据")
    public ResponseResult<String> exportPaymentPlans(
            @Parameter(description = "付款计划ID列表") @RequestBody List<Long> paymentPlanIds) {
        return ResponseResult.success(paymentPlanService.exportPaymentPlans(paymentPlanIds));
    }
}
