package com.lawfirm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.response.ResponseResult;
import com.lawfirm.model.finance.entity.Receivable;
import com.lawfirm.model.finance.enums.ReceivableStatusEnum;
import com.lawfirm.model.finance.service.ReceivableService;
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
import java.util.Map;

/**
 * 应收账款管理控制器
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/finance/receivables")
@Tag(name = "应收账款管理", description = "应收账款相关接口")
public class ReceivableController {

    private final ReceivableService receivableService;

    @PostMapping
    @Operation(summary = "创建应收账款")
    public ResponseResult<Long> createReceivable(@Valid @RequestBody Receivable receivable) {
        return ResponseResult.success(receivableService.createReceivable(receivable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新应收账款")
    public ResponseResult<Boolean> updateReceivable(@PathVariable("id") Long id, 
                                                  @Valid @RequestBody Receivable receivable) {
        receivable.setId(id);
        return ResponseResult.success(receivableService.updateReceivable(receivable));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除应收账款")
    public ResponseResult<Boolean> deleteReceivable(@PathVariable("id") Long id) {
        return ResponseResult.success(receivableService.deleteReceivable(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取应收账款详情")
    public ResponseResult<Receivable> getReceivable(@PathVariable("id") Long id) {
        return ResponseResult.success(receivableService.getReceivableById(id));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新应收账款状态")
    public ResponseResult<Boolean> updateReceivableStatus(
            @PathVariable("id") Long id,
            @Parameter(description = "状态") @RequestParam ReceivableStatusEnum status,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return ResponseResult.success(receivableService.updateReceivableStatus(id, status, remark));
    }

    @PostMapping("/{id}/receipt")
    @Operation(summary = "记录收款")
    public ResponseResult<Long> recordReceipt(
            @PathVariable("id") Long id,
            @Parameter(description = "收款金额") @RequestParam BigDecimal amount,
            @Parameter(description = "收款账户ID") @RequestParam Long accountId,
            @Parameter(description = "收款日期") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime receiveDate,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return ResponseResult.success(receivableService.recordReceipt(id, amount, accountId, receiveDate, remark));
    }

    @GetMapping("/list")
    @Operation(summary = "查询应收账款列表")
    public ResponseResult<List<Receivable>> listReceivables(
            @Parameter(description = "合同ID") @RequestParam(required = false) Long contractId,
            @Parameter(description = "客户ID") @RequestParam(required = false) Long clientId,
            @Parameter(description = "状态") @RequestParam(required = false) ReceivableStatusEnum status,
            @Parameter(description = "逾期天数") @RequestParam(required = false) Integer overdueDays) {
        return ResponseResult.success(receivableService.listReceivables(contractId, clientId, status, overdueDays));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询应收账款")
    public ResponseResult<IPage<Receivable>> pageReceivables(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "合同ID") @RequestParam(required = false) Long contractId,
            @Parameter(description = "客户ID") @RequestParam(required = false) Long clientId,
            @Parameter(description = "状态") @RequestParam(required = false) ReceivableStatusEnum status,
            @Parameter(description = "逾期天数") @RequestParam(required = false) Integer overdueDays) {
        Page<Receivable> page = new Page<>(current, size);
        return ResponseResult.success(receivableService.pageReceivables(page, contractId, clientId, status, overdueDays));
    }

    @GetMapping("/contract/{contractId}")
    @Operation(summary = "按合同查询应收账款")
    public ResponseResult<List<Receivable>> listReceivablesByContract(@PathVariable("contractId") Long contractId) {
        return ResponseResult.success(receivableService.listReceivablesByContract(contractId));
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "按客户查询应收账款")
    public ResponseResult<List<Receivable>> listReceivablesByClient(@PathVariable("clientId") Long clientId) {
        return ResponseResult.success(receivableService.listReceivablesByClient(clientId));
    }

    @GetMapping("/sum")
    @Operation(summary = "统计应收账款总额")
    public ResponseResult<BigDecimal> sumReceivableAmount(
            @Parameter(description = "合同ID") @RequestParam(required = false) Long contractId,
            @Parameter(description = "客户ID") @RequestParam(required = false) Long clientId,
            @Parameter(description = "状态") @RequestParam(required = false) ReceivableStatusEnum status) {
        return ResponseResult.success(receivableService.sumReceivableAmount(contractId, clientId, status));
    }

    @GetMapping("/sum/received")
    @Operation(summary = "统计已收款总额")
    public ResponseResult<BigDecimal> sumReceivedAmount(
            @Parameter(description = "合同ID") @RequestParam(required = false) Long contractId,
            @Parameter(description = "客户ID") @RequestParam(required = false) Long clientId) {
        return ResponseResult.success(receivableService.sumReceivedAmount(contractId, clientId));
    }

    @GetMapping("/sum/unreceived")
    @Operation(summary = "统计未收款总额")
    public ResponseResult<BigDecimal> sumUnreceivedAmount(
            @Parameter(description = "合同ID") @RequestParam(required = false) Long contractId,
            @Parameter(description = "客户ID") @RequestParam(required = false) Long clientId) {
        return ResponseResult.success(receivableService.sumUnreceivedAmount(contractId, clientId));
    }

    @GetMapping("/aging")
    @Operation(summary = "账龄分析")
    public ResponseResult<Map<String, BigDecimal>> agingAnalysis(
            @Parameter(description = "客户ID") @RequestParam(required = false) Long clientId) {
        return ResponseResult.success(receivableService.agingAnalysis(clientId));
    }

    @PostMapping("/update-overdue")
    @Operation(summary = "更新应收账款逾期状态")
    public ResponseResult<Integer> updateOverdueStatus() {
        return ResponseResult.success(receivableService.updateOverdueStatus());
    }
}
