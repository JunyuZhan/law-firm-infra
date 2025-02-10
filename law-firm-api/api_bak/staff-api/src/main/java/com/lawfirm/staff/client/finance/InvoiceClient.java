package com.lawfirm.staff.client.finance;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.finance.fallback.InvoiceClientFallback;
import com.lawfirm.staff.model.request.finance.invoice.*;
import com.lawfirm.staff.model.response.finance.invoice.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 发票管理Feign客户端
 */
@FeignClient(
    name = "law-firm-finance",
    contextId = "invoiceClient",
    path = "/invoice",
    fallback = InvoiceClientFallback.class
)
public interface InvoiceClient {
    
    @GetMapping("/page")
    Result<PageResult<InvoiceResponse>> page(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "orderType", required = false) String orderType);
    
    @PostMapping
    Result<Void> create(@RequestBody InvoiceCreateRequest request);
    
    @PutMapping
    Result<Void> update(@RequestBody InvoiceUpdateRequest request);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    Result<InvoiceResponse> get(@PathVariable("id") Long id);
    
    @PostMapping("/batch/delete")
    Result<Void> batchDelete(@RequestBody List<Long> ids);
    
    @GetMapping("/statistics")
    Result<InvoiceStatisticsResponse> getStatistics();
    
    @PostMapping("/approve/{id}")
    Result<Void> approve(@PathVariable("id") Long id, @RequestBody InvoiceApproveRequest request);
    
    @PostMapping("/reject/{id}")
    Result<Void> reject(@PathVariable("id") Long id, @RequestBody InvoiceRejectRequest request);
    
    @GetMapping("/export")
    void export(@SpringQueryMap InvoiceExportRequest request);
    
    @GetMapping("/template/download")
    void downloadTemplate();
    
    @PostMapping("/import")
    Result<InvoiceImportResponse> importInvoices(@RequestBody InvoiceImportRequest request);
} 