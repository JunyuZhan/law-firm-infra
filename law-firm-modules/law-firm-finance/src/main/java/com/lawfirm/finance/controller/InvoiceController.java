package com.lawfirm.finance.controller;

import com.lawfirm.finance.entity.Invoice;
import com.lawfirm.finance.service.InvoiceService;
import com.lawfirm.common.core.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "发票管理", description = "提供发票的开具、作废、查询等功能")
@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Operation(summary = "开具发票",
            description = "创建并开具一张新的发票")
    @ApiResponse(responseCode = "200", description = "开具成功",
            content = @Content(schema = @Schema(implementation = Invoice.class)))
    @PostMapping
    public BaseResponse<Invoice> createInvoice(
            @Parameter(description = "发票信息", required = true)
            @Validated @RequestBody Invoice invoice) {
        return BaseResponse.success(invoiceService.createInvoice(invoice));
    }

    @Operation(summary = "作废发票",
            description = "作废指定的发票")
    @ApiResponse(responseCode = "200", description = "作废成功")
    @PostMapping("/{id}/void")
    public BaseResponse<Void> voidInvoice(
            @Parameter(description = "发票ID", required = true)
            @PathVariable Long id) {
        invoiceService.voidInvoice(id);
        return BaseResponse.success();
    }

    @Operation(summary = "获取事项发票",
            description = "获取指定事项的所有发票")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = Invoice.class)))
    @GetMapping("/matter/{matterId}")
    public BaseResponse<List<Invoice>> getInvoicesByMatterId(
            @Parameter(description = "事项ID", required = true)
            @PathVariable Long matterId) {
        return BaseResponse.success(invoiceService.getInvoicesByMatterId(matterId));
    }

    @Operation(summary = "计算发票总额",
            description = "计算指定事项的有效发票总金额")
    @ApiResponse(responseCode = "200", description = "计算成功")
    @GetMapping("/matter/{matterId}/total")
    public BaseResponse<BigDecimal> calculateTotalInvoiceAmount(
            @Parameter(description = "事项ID", required = true)
            @PathVariable Long matterId) {
        return BaseResponse.success(invoiceService.calculateTotalInvoiceAmount(matterId));
    }

    @Operation(summary = "获取发票详情",
            description = "根据发票号码获取发票详情")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = Invoice.class)))
    @GetMapping("/number/{invoiceNo}")
    public BaseResponse<Invoice> getInvoiceByNumber(
            @Parameter(description = "发票号码", required = true)
            @PathVariable String invoiceNo) {
        return BaseResponse.success(invoiceService.getInvoiceByNumber(invoiceNo));
    }

    @Operation(summary = "批量查询发票",
            description = "根据多个条件批量查询发票")
    @ApiResponse(responseCode = "200", description = "查询成功",
            content = @Content(schema = @Schema(implementation = Invoice.class)))
    @GetMapping("/search")
    public BaseResponse<List<Invoice>> searchInvoices(
            @Parameter(description = "开始日期") @RequestParam(required = false) String startDate,
            @Parameter(description = "结束日期") @RequestParam(required = false) String endDate,
            @Parameter(description = "发票类型") @RequestParam(required = false) Integer type,
            @Parameter(description = "最小金额") @RequestParam(required = false) BigDecimal minAmount,
            @Parameter(description = "最大金额") @RequestParam(required = false) BigDecimal maxAmount) {
        return BaseResponse.success(invoiceService.searchInvoices(startDate, endDate, type, minAmount, maxAmount));
    }
} 