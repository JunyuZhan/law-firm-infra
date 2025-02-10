package com.lawfirm.staff.client.lawyer.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.lawyer.ConflictClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import com.lawfirm.staff.model.request.lawyer.conflict.ConflictCheckRequest;
import com.lawfirm.staff.model.request.lawyer.conflict.ConflictPageRequest;
import com.lawfirm.staff.model.response.lawyer.conflict.ConflictResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Slf4j
@Component
public class ConflictClientFallback extends FeignFallbackConfig implements ConflictClient {

    private static final String SERVICE_NAME = "冲突检查服务";

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<ConflictResponse>> page(ConflictPageRequest request) {
        Type type = createParameterizedType(Result.class, createParameterizedType(PageResult.class, ConflictResponse.class));
        return handleFallback(SERVICE_NAME, "page", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Boolean> check(ConflictCheckRequest request) {
        Type type = createParameterizedType(Result.class, Boolean.class);
        return handleFallback(SERVICE_NAME, "check", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<ConflictResponse>> getHistory(ConflictPageRequest request) {
        Type type = createParameterizedType(Result.class, createParameterizedType(PageResult.class, ConflictResponse.class));
        return handleFallback(SERVICE_NAME, "getHistory", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<ConflictResponse> get(Long id) {
        Type type = createParameterizedType(Result.class, ConflictResponse.class);
        return handleFallback(SERVICE_NAME, "get", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> resolve(ConflictCheckRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "resolve", type);
    }
} 