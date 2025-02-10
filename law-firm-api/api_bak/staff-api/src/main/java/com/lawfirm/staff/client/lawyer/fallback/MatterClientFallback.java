package com.lawfirm.staff.client.lawyer.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.lawyer.MatterClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import com.lawfirm.staff.model.request.lawyer.matter.*;
import com.lawfirm.staff.model.response.lawyer.matter.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Component
public class MatterClientFallback extends FeignFallbackConfig implements MatterClient {

    private static final String SERVICE_NAME = "事务服务";

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<MatterResponse>> page(Integer pageNum, Integer pageSize, String keyword) {
        Type type = createParameterizedType(Result.class, createParameterizedType(PageResult.class, MatterResponse.class));
        return handleFallback(SERVICE_NAME, "page", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> create(MatterCreateRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "create", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> update(MatterUpdateRequest request) {
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
    public Result<MatterResponse> get(Long id) {
        Type type = createParameterizedType(Result.class, MatterResponse.class);
        return handleFallback(SERVICE_NAME, "get", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<MatterStatisticsResponse> getStatistics() {
        Type type = createParameterizedType(Result.class, MatterStatisticsResponse.class);
        return handleFallback(SERVICE_NAME, "getStatistics", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<MatterResponse>> getMyMatters() {
        Type type = createParameterizedType(Result.class, createParameterizedType(List.class, MatterResponse.class));
        return handleFallback(SERVICE_NAME, "getMyMatters", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<MatterResponse>> getDepartmentMatters() {
        Type type = createParameterizedType(Result.class, createParameterizedType(List.class, MatterResponse.class));
        return handleFallback(SERVICE_NAME, "getDepartmentMatters", type);
    }
} 