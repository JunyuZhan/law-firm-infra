package com.lawfirm.staff.client.finance.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.finance.InvoiceClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import com.lawfirm.staff.model.request.finance.invoice.*;
import com.lawfirm.staff.model.response.finance.invoice.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Component
public class InvoiceClientFallback extends FeignFallbackConfig implements InvoiceClient {

    private static final String SERVICE_NAME = "发票服务";

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<InvoiceResponse>> page(Integer pageNum, Integer pageSize, String keyword, String orderBy, String orderType) {
        Type type = createParameterizedType(Result.class, createParameterizedType(PageResult.class, InvoiceResponse.class));
        return handleFallback(SERVICE_NAME, "page", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> create(InvoiceCreateRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "create", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> update(InvoiceUpdateRequest request) {
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
    public Result<InvoiceResponse> get(Long id) {
        Type type = createParameterizedType(Result.class, InvoiceResponse.class);
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
    public Result<InvoiceStatisticsResponse> getStatistics() {
        Type type = createParameterizedType(Result.class, InvoiceStatisticsResponse.class);
        return handleFallback(SERVICE_NAME, "getStatistics", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> approve(Long id, InvoiceApproveRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "approve", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> reject(Long id, InvoiceRejectRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "reject", type);
    }

    @Override
    public void export(InvoiceExportRequest request) {
        log.error("{}服务的export方法调用失败", SERVICE_NAME);
    }

    @Override
    public void downloadTemplate() {
        log.error("{}服务的downloadTemplate方法调用失败", SERVICE_NAME);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<InvoiceImportResponse> importInvoices(InvoiceImportRequest request) {
        Type type = createParameterizedType(Result.class, InvoiceImportResponse.class);
        return handleFallback(SERVICE_NAME, "importInvoices", type);
    }
} 