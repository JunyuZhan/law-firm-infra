package com.lawfirm.staff.client.finance.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.finance.ChargeClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import com.lawfirm.staff.model.request.finance.charge.ChargeCreateRequest;
import com.lawfirm.staff.model.request.finance.charge.ChargePageRequest;
import com.lawfirm.staff.model.request.finance.charge.ChargeUpdateRequest;
import com.lawfirm.staff.model.response.finance.charge.ChargeResponse;
import com.lawfirm.staff.model.response.finance.charge.ChargeStatisticsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Slf4j
@Component
public class ChargeClientFallback extends FeignFallbackConfig implements ChargeClient {

    private static final String SERVICE_NAME = "收费服务";

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<ChargeResponse>> page(ChargePageRequest request) {
        Type type = createParameterizedType(Result.class, createParameterizedType(PageResult.class, ChargeResponse.class));
        return handleFallback(SERVICE_NAME, "page", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> create(ChargeCreateRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "create", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> update(Long id, ChargeUpdateRequest request) {
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
    public Result<ChargeResponse> get(Long id) {
        Type type = createParameterizedType(Result.class, ChargeResponse.class);
        return handleFallback(SERVICE_NAME, "get", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> confirm(Long id) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "confirm", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> cancel(Long id, String reason) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "cancel", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> audit(Long id, Integer status, String remark) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "audit", type);
    }

    @Override
    public void export(ChargePageRequest request) {
        log.error("{}服务的export方法调用失败", SERVICE_NAME);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<ChargeStatisticsResponse> getStats(ChargePageRequest request) {
        Type type = createParameterizedType(Result.class, ChargeStatisticsResponse.class);
        return handleFallback(SERVICE_NAME, "getStats", type);
    }
} 