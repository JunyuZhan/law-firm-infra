package com.lawfirm.staff.controller.finance;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.staff.client.finance.InvoiceClient;
import com.lawfirm.staff.model.request.finance.invoice.InvoiceCreateRequest;
import com.lawfirm.staff.model.request.finance.invoice.InvoicePageRequest;
import com.lawfirm.staff.model.request.finance.invoice.InvoiceUpdateRequest;
import com.lawfirm.staff.model.response.finance.invoice.InvoiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "发票管理")
@RestController
@RequestMapping("/finance/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceClient invoiceClient;

    @Operation(summary = "分页查询发票")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('finance:invoice:query')")
    @OperationLog(description = "分页查询发票", operationType = "QUERY")
    public Result<PageResult<InvoiceResponse>> page(@Validated InvoicePageRequest request) {
        return invoiceClient.page(request);
    }

    @Operation(summary = "创建发票")
    @PostMapping
    @PreAuthorize("hasAuthority('finance:invoice:create')")
    @OperationLog(description = "创建发票", operationType = "CREATE")
    public Result<Void> create(@RequestBody @Validated InvoiceCreateRequest request) {
        return invoiceClient.create(request);
    }

    @Operation(summary = "修改发票")
    @PutMapping
    @PreAuthorize("hasAuthority('finance:invoice:update')")
    @OperationLog(description = "修改发票", operationType = "UPDATE")
    public Result<Void> update(@RequestBody @Validated InvoiceUpdateRequest request) {
        return invoiceClient.update(request);
    }

    @Operation(summary = "删除发票")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('finance:invoice:delete')")
    @OperationLog(description = "删除发票", operationType = "DELETE")
    public Result<Void> delete(@PathVariable Long id) {
        return invoiceClient.delete(id);
    }

    @Operation(summary = "获取发票详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('finance:invoice:query')")
    @OperationLog(description = "获取发票详情", operationType = "QUERY")
    public Result<InvoiceResponse> get(@PathVariable Long id) {
        return invoiceClient.get(id);
    }

    @Operation(summary = "审核发票")
    @PutMapping("/audit/{id}")
    @PreAuthorize("hasAuthority('finance:invoice:audit')")
    @OperationLog(description = "审核发票", operationType = "AUDIT")
    public Result<Void> audit(@PathVariable Long id, @RequestParam Integer status, @RequestParam String comment) {
        return invoiceClient.audit(id, status, comment);
    }

    @Operation(summary = "导出发票")
    @GetMapping("/export")
    @PreAuthorize("hasAuthority('finance:invoice:export')")
    @OperationLog(description = "导出发票", operationType = "EXPORT")
    public void export(@Validated InvoicePageRequest request) {
        invoiceClient.export(request);
    }

    @Operation(summary = "统计发票")
    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('finance:invoice:query')")
    @OperationLog(description = "统计发票", operationType = "QUERY")
    public Result<Map<String, Object>> getStats(@Validated InvoicePageRequest request) {
        return invoiceClient.getStats(request);
    }
} 