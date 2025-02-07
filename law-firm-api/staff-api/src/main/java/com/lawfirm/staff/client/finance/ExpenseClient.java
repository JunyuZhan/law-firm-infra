package com.lawfirm.staff.client.finance;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.finance.fallback.ExpenseClientFallback;
import com.lawfirm.staff.model.request.finance.expense.*;
import com.lawfirm.staff.model.response.finance.expense.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 费用管理Feign客户端
 */
@FeignClient(
    name = "law-firm-finance",
    contextId = "expenseClient",
    path = "/expense",
    fallback = ExpenseClientFallback.class
)
public interface ExpenseClient {
    
    @GetMapping("/page")
    Result<PageResult<ExpenseResponse>> page(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "orderType", required = false) String orderType);
    
    @PostMapping
    Result<Void> create(@RequestBody ExpenseCreateRequest request);
    
    @PutMapping
    Result<Void> update(@RequestBody ExpenseUpdateRequest request);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    Result<ExpenseResponse> get(@PathVariable("id") Long id);
    
    @PostMapping("/batch/delete")
    Result<Void> batchDelete(@RequestBody List<Long> ids);
    
    @GetMapping("/statistics")
    Result<ExpenseStatisticsResponse> getStatistics();
    
    @PostMapping("/approve/{id}")
    Result<Void> approve(@PathVariable("id") Long id, @RequestBody ExpenseApproveRequest request);
    
    @PostMapping("/reject/{id}")
    Result<Void> reject(@PathVariable("id") Long id, @RequestBody ExpenseRejectRequest request);
    
    @PostMapping("/upload/receipt")
    Result<String> uploadReceipt(@RequestParam("file") MultipartFile file);
    
    @GetMapping("/download/receipt/{id}")
    void downloadReceipt(@PathVariable("id") Long id);
    
    @GetMapping("/export")
    void export(@SpringQueryMap ExpenseExportRequest request);
    
    @GetMapping("/template/download")
    void downloadTemplate();
    
    @PostMapping("/import")
    Result<ExpenseImportResponse> importExpenses(@RequestBody ExpenseImportRequest request);
    
    @GetMapping("/categories")
    Result<List<ExpenseCategoryResponse>> getCategories();
    
    @GetMapping("/budgets")
    Result<List<ExpenseBudgetResponse>> getBudgets();
} 