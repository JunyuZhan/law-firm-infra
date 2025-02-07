package com.lawfirm.staff.client.lawyer.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.lawyer.ContractClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import com.lawfirm.staff.model.request.lawyer.contract.ContractCreateRequest;
import com.lawfirm.staff.model.request.lawyer.contract.ContractPageRequest;
import com.lawfirm.staff.model.request.lawyer.contract.ContractUpdateRequest;
import com.lawfirm.staff.model.response.lawyer.contract.ContractResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Slf4j
@Component
public class ContractClientFallback extends FeignFallbackConfig implements ContractClient {

    private static final String SERVICE_NAME = "合同服务";

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<ContractResponse>> page(ContractPageRequest request) {
        Type type = createParameterizedType(Result.class, createParameterizedType(PageResult.class, ContractResponse.class));
        return handleFallback(SERVICE_NAME, "page", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> create(ContractCreateRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "create", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> update(Long id, ContractUpdateRequest request) {
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
    public Result<ContractResponse> get(Long id) {
        Type type = createParameterizedType(Result.class, ContractResponse.class);
        return handleFallback(SERVICE_NAME, "get", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> submit(Long id) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "submit", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> approve(Long id) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "approve", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> reject(Long id, String reason) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "reject", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> terminate(Long id, String reason) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "terminate", type);
    }
} 