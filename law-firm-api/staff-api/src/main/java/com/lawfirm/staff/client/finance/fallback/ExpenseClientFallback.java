package com.lawfirm.staff.client.finance.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.finance.ExpenseClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import com.lawfirm.staff.model.request.finance.expense.*;
import com.lawfirm.staff.model.response.finance.expense.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Component
public class ExpenseClientFallback extends FeignFallbackConfig implements ExpenseClient {

    private static final String SERVICE_NAME = "费用服务";

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<ExpenseResponse>> page(Integer pageNum, Integer pageSize, String keyword, String orderBy, String orderType) {
        Type type = createParameterizedType(Result.class, createParameterizedType(PageResult.class, ExpenseResponse.class));
        return handleFallback(SERVICE_NAME, "page", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> create(ExpenseCreateRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "create", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> update(ExpenseUpdateRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "update", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> delete(Long id) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "delete", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<ExpenseResponse> get(Long id) {
        Type type = createParameterizedType(Result.class, ExpenseResponse.class);
        return handleFallback(SERVICE_NAME, "get", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> batchDelete(List<Long> ids) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "batchDelete", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<ExpenseStatisticsResponse> getStatistics() {
        Type type = createParameterizedType(Result.class, ExpenseStatisticsResponse.class);
        return handleFallback(SERVICE_NAME, "getStatistics", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> approve(Long id, ExpenseApproveRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "approve", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> reject(Long id, ExpenseRejectRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "reject", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<String> uploadReceipt(MultipartFile file) {
        Type type = createParameterizedType(Result.class, String.class);
        return handleFallback(SERVICE_NAME, "uploadReceipt", type);
    }

    @Override
    public void downloadReceipt(Long id) {
        log.error("{}服务的downloadReceipt方法调用失败", SERVICE_NAME);
    }

    @Override
    public void export(ExpenseExportRequest request) {
        log.error("{}服务的export方法调用失败", SERVICE_NAME);
    }

    @Override
    public void downloadTemplate() {
        log.error("{}服务的downloadTemplate方法调用失败", SERVICE_NAME);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<ExpenseImportResponse> importExpenses(ExpenseImportRequest request) {
        Type type = createParameterizedType(Result.class, ExpenseImportResponse.class);
        return handleFallback(SERVICE_NAME, "importExpenses", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<ExpenseCategoryResponse>> getCategories() {
        Type type = createParameterizedType(Result.class, createParameterizedType(List.class, ExpenseCategoryResponse.class));
        return handleFallback(SERVICE_NAME, "getCategories", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<ExpenseBudgetResponse>> getBudgets() {
        Type type = createParameterizedType(Result.class, createParameterizedType(List.class, ExpenseBudgetResponse.class));
        return handleFallback(SERVICE_NAME, "getBudgets", type);
    }
} 