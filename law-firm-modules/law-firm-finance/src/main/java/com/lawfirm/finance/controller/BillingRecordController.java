package com.lawfirm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.finance.entity.BillingRecord;
import com.lawfirm.model.finance.enums.BillingStatusEnum;
import com.lawfirm.model.finance.service.BillingRecordService;
import com.lawfirm.finance.constant.FinanceConstants;
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
 * 账单记录管理控制器
 */
@Slf4j
@Tag(name = "账单管理", description = "账单管理接口")
@RestController("billingRecordController")
@RequestMapping(FinanceConstants.API_BILLING_PREFIX)
@RequiredArgsConstructor
public class BillingRecordController {

    private final BillingRecordService billingRecordService;

    @PostMapping
    @Operation(summary = "创建账单记录")
    public CommonResult<Long> createBillingRecord(@Valid @RequestBody BillingRecord billingRecord) {
        return CommonResult.success(billingRecordService.createBillingRecord(billingRecord));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新账单记录")
    public CommonResult<Boolean> updateBillingRecord(@PathVariable("id") Long id, 
                                                     @Valid @RequestBody BillingRecord billingRecord) {
        billingRecord.setId(id);
        return CommonResult.success(billingRecordService.updateBillingRecord(billingRecord));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除账单记录")
    public CommonResult<Boolean> deleteBillingRecord(@PathVariable("id") Long id) {
        return CommonResult.success(billingRecordService.deleteBillingRecord(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取账单记录详情")
    public CommonResult<BillingRecord> getBillingRecord(@PathVariable("id") Long id) {
        return CommonResult.success(billingRecordService.getBillingRecordById(id));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新账单记录状态")
    public CommonResult<Boolean> updateBillingRecordStatus(
            @PathVariable("id") Long id,
            @Parameter(description = "状态") @RequestParam BillingStatusEnum status,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return CommonResult.success(billingRecordService.updateBillingRecordStatus(id, status, remark));
    }

    @PostMapping("/{id}/confirm")
    @Operation(summary = "确认账单")
    public CommonResult<Boolean> confirmBillingRecord(
            @PathVariable("id") Long id,
            @Parameter(description = "操作人ID") @RequestParam Long operatorId,
            @Parameter(description = "确认意见") @RequestParam(required = false) String remark) {
        return CommonResult.success(billingRecordService.confirmBillingRecord(id, operatorId, remark));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "取消账单")
    public CommonResult<Boolean> cancelBillingRecord(
            @PathVariable("id") Long id,
            @Parameter(description = "取消原因") @RequestParam String reason) {
        return CommonResult.success(billingRecordService.cancelBillingRecord(id, reason));
    }

    @GetMapping("/list")
    @Operation(summary = "查询账单记录列表")
    public CommonResult<List<BillingRecord>> listBillingRecords(
            @Parameter(description = "状态") @RequestParam(required = false) BillingStatusEnum status,
            @Parameter(description = "客户ID") @RequestParam(required = false) Long clientId,
            @Parameter(description = "案件ID") @RequestParam(required = false) Long caseId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(billingRecordService.listBillingRecords(status, clientId, caseId, startTime, endTime));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询账单记录")
    public CommonResult<IPage<BillingRecord>> pageBillingRecords(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "状态") @RequestParam(required = false) BillingStatusEnum status,
            @Parameter(description = "客户ID") @RequestParam(required = false) Long clientId,
            @Parameter(description = "案件ID") @RequestParam(required = false) Long caseId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Page<BillingRecord> page = new Page<>(current, size);
        return CommonResult.success(billingRecordService.pageBillingRecords(page, status, clientId, caseId, startTime, endTime));
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "按客户查询账单记录")
    public CommonResult<List<BillingRecord>> listBillingRecordsByClient(
            @PathVariable("clientId") Long clientId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(billingRecordService.listBillingRecordsByClient(clientId, startTime, endTime));
    }

    @GetMapping("/case/{caseId}")
    @Operation(summary = "按案件查询账单记录")
    public CommonResult<List<BillingRecord>> listBillingRecordsByCase(@PathVariable("caseId") Long caseId) {
        return CommonResult.success(billingRecordService.listBillingRecordsByCase(caseId));
    }

    @GetMapping("/sum")
    @Operation(summary = "统计账单金额")
    public CommonResult<BigDecimal> sumBillingAmount(
            @Parameter(description = "状态") @RequestParam(required = false) BillingStatusEnum status,
            @Parameter(description = "客户ID") @RequestParam(required = false) Long clientId,
            @Parameter(description = "案件ID") @RequestParam(required = false) Long caseId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(billingRecordService.sumBillingAmount(status, clientId, caseId, startTime, endTime));
    }

    @GetMapping("/sum/client/{clientId}")
    @Operation(summary = "统计客户账单金额")
    public CommonResult<BigDecimal> sumClientBillingAmount(
            @PathVariable("clientId") Long clientId,
            @Parameter(description = "状态") @RequestParam(required = false) BillingStatusEnum status,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(billingRecordService.sumClientBillingAmount(clientId, status, startTime, endTime));
    }

    @GetMapping("/sum/case/{caseId}")
    @Operation(summary = "统计案件账单金额")
    public CommonResult<BigDecimal> sumCaseBillingAmount(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "状态") @RequestParam(required = false) BillingStatusEnum status) {
        return CommonResult.success(billingRecordService.sumCaseBillingAmount(caseId, status));
    }

    @PostMapping("/export")
    @Operation(summary = "导出账单记录数据")
    public CommonResult<String> exportBillingRecords(
            @Parameter(description = "账单记录ID列表") @RequestBody List<Long> billingRecordIds) {
        return CommonResult.success(billingRecordService.exportBillingRecords(billingRecordIds));
    }
}
