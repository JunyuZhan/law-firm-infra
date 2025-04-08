package com.lawfirm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.finance.entity.Invoice;
import com.lawfirm.model.finance.enums.InvoiceStatusEnum;
import com.lawfirm.model.finance.enums.InvoiceTypeEnum;
import com.lawfirm.model.finance.service.InvoiceService;
import com.lawfirm.finance.constant.FinanceConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController("invoiceController")
@RequiredArgsConstructor
@RequestMapping(FinanceConstants.API_INVOICE_PREFIX)
@Tag(name = "发票管理", description = "发票相关接口")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    @Operation(summary = "创建发票")
    public CommonResult<Long> createInvoice(@Valid @RequestBody Invoice invoice) {
        return CommonResult.success(invoiceService.createInvoice(invoice));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新发票")
    public CommonResult<Boolean> updateInvoice(@PathVariable("id") Long id, 
                                            @Valid @RequestBody Invoice invoice) {
        invoice.setId(id);
        return CommonResult.success(invoiceService.updateInvoice(invoice));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除发票")
    public CommonResult<Boolean> deleteInvoice(@PathVariable("id") Long id) {
        return CommonResult.success(invoiceService.deleteInvoice(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取发票详情")
    public CommonResult<Invoice> getInvoice(@PathVariable("id") Long id) {
        return CommonResult.success(invoiceService.getInvoiceById(id));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新发票状态")
    public CommonResult<Boolean> updateInvoiceStatus(
            @PathVariable("id") Long id,
            @Parameter(description = "发票状态") @RequestParam InvoiceStatusEnum status,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return CommonResult.success(invoiceService.updateInvoiceStatus(id, status, remark));
    }

    @PutMapping("/{id}/confirm")
    @Operation(summary = "确认开票")
    public CommonResult<Boolean> confirmInvoice(
            @PathVariable("id") Long id,
            @Parameter(description = "发票号码") @RequestParam String invoiceNo,
            @Parameter(description = "开票日期") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime invoiceDate,
            @Parameter(description = "操作人ID") @RequestParam Long operatorId,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return CommonResult.success(invoiceService.confirmInvoice(id, invoiceNo, invoiceDate, operatorId, remark));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "取消发票")
    public CommonResult<Boolean> cancelInvoice(
            @PathVariable("id") Long id,
            @Parameter(description = "取消原因") @RequestParam String reason) {
        return CommonResult.success(invoiceService.cancelInvoice(id, reason));
    }

    @GetMapping("/list")
    @Operation(summary = "查询发票列表")
    public CommonResult<List<Invoice>> listInvoices(
            @Parameter(description = "发票类型") @RequestParam(required = false) InvoiceTypeEnum invoiceType,
            @Parameter(description = "发票状态") @RequestParam(required = false) InvoiceStatusEnum status,
            @Parameter(description = "合同ID") @RequestParam(required = false) Long contractId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(invoiceService.listInvoices(invoiceType, status, contractId, startTime, endTime));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询发票")
    public CommonResult<IPage<Invoice>> pageInvoices(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "发票类型") @RequestParam(required = false) InvoiceTypeEnum invoiceType,
            @Parameter(description = "发票状态") @RequestParam(required = false) InvoiceStatusEnum status,
            @Parameter(description = "合同ID") @RequestParam(required = false) Long contractId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Page<Invoice> page = new Page<>(current, size);
        return CommonResult.success(invoiceService.pageInvoices(page, invoiceType, status, contractId, startTime, endTime));
    }

    @GetMapping("/contract/{contractId}")
    @Operation(summary = "按合同查询发票")
    public CommonResult<List<Invoice>> listInvoicesByContract(@PathVariable("contractId") Long contractId) {
        return CommonResult.success(invoiceService.listInvoicesByContract(contractId));
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "按客户查询发票")
    public CommonResult<List<Invoice>> listInvoicesByClient(
            @PathVariable("clientId") Long clientId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(invoiceService.listInvoicesByClient(clientId, startTime, endTime));
    }

    @GetMapping("/export")
    @Operation(summary = "导出发票")
    public CommonResult<String> exportInvoices(@Parameter(description = "发票ID列表") @RequestParam List<Long> invoiceIds) {
        return CommonResult.success(invoiceService.exportInvoices(invoiceIds));
    }
}
