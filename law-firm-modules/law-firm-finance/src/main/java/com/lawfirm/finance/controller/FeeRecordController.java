package com.lawfirm.finance.controller;

import com.lawfirm.finance.entity.FeeRecord;
import com.lawfirm.finance.service.FeeRecordService;
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

@Tag(name = "收费记录管理", description = "收费记录的增删改查接口")
@RestController
@RequestMapping("/api/v1/finance/fees")
@RequiredArgsConstructor
public class FeeRecordController {

    private final FeeRecordService feeRecordService;

    @Operation(summary = "创建收费记录", description = "创建一条新的收费记录")
    @PostMapping
    public ResponseEntity<FeeRecord> createFeeRecord(
            @Parameter(description = "收费记录信息", required = true) 
            @Valid @RequestBody FeeRecord feeRecord) {
        return ResponseEntity.ok(feeRecordService.createFeeRecord(feeRecord));
    }

    @Operation(summary = "更新收费记录", description = "根据ID更新收费记录信息")
    @PutMapping("/{id}")
    public ResponseEntity<FeeRecord> updateFeeRecord(
            @Parameter(description = "收费记录ID", required = true) @PathVariable Long id,
            @Parameter(description = "收费记录信息", required = true) 
            @Valid @RequestBody FeeRecord feeRecord) {
        feeRecord.setId(id);
        return ResponseEntity.ok(feeRecordService.updateFeeRecord(feeRecord));
    }

    @Operation(summary = "获取收费记录", description = "根据ID获取收费记录详情")
    @GetMapping("/{id}")
    public ResponseEntity<FeeRecord> getFeeRecord(
            @Parameter(description = "收费记录ID", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(feeRecordService.getFeeRecord(id));
    }

    @Operation(summary = "删除收费记录", description = "根据ID删除收费记录")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeeRecord(
            @Parameter(description = "收费记录ID", required = true) @PathVariable Long id) {
        feeRecordService.deleteFeeRecord(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "分页查询收费记录", description = "分页查询所有收费记录")
    @GetMapping
    public ResponseEntity<Page<FeeRecord>> getFeeRecords(
            @Parameter(description = "分页参数") Pageable pageable) {
        return ResponseEntity.ok(feeRecordService.findFeeRecords(pageable));
    }

    @Operation(summary = "查询案件收费记录", description = "根据案件ID查询相关的收费记录")
    @GetMapping("/case/{caseId}")
    public ResponseEntity<List<FeeRecord>> getFeeRecordsByCaseId(
            @Parameter(description = "案件ID", required = true) @PathVariable Long caseId) {
        return ResponseEntity.ok(feeRecordService.findFeeRecordsByCaseId(caseId));
    }

    @Operation(summary = "查询客户收费记录", description = "根据客户ID查询相关的收费记录")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<FeeRecord>> getFeeRecordsByClientId(
            @Parameter(description = "客户ID", required = true) @PathVariable Long clientId) {
        return ResponseEntity.ok(feeRecordService.findFeeRecordsByClientId(clientId));
    }

    @Operation(summary = "更新支付状态", description = "更新收费记录的支付状态和已支付金额")
    @PutMapping("/{id}/payment")
    public ResponseEntity<FeeRecord> updatePaymentStatus(
            @Parameter(description = "收费记录ID", required = true) @PathVariable Long id,
            @Parameter(description = "支付状态", required = true) @RequestParam String status,
            @Parameter(description = "已支付金额", required = true) @RequestParam BigDecimal paidAmount) {
        return ResponseEntity.ok(feeRecordService.updatePaymentStatus(id, status, paidAmount));
    }

    @Operation(summary = "统计总收费金额", description = "统计律所的总收费金额")
    @GetMapping("/statistics/total-amount")
    public ResponseEntity<BigDecimal> getTotalAmount(
            @Parameter(description = "律所ID", required = true) @RequestParam Long lawFirmId) {
        return ResponseEntity.ok(feeRecordService.calculateTotalAmount(lawFirmId));
    }

    @Operation(summary = "统计已收费金额", description = "统计律所的已收费金额")
    @GetMapping("/statistics/total-paid-amount")
    public ResponseEntity<BigDecimal> getTotalPaidAmount(
            @Parameter(description = "律所ID", required = true) @RequestParam Long lawFirmId) {
        return ResponseEntity.ok(feeRecordService.calculateTotalPaidAmount(lawFirmId));
    }
} 