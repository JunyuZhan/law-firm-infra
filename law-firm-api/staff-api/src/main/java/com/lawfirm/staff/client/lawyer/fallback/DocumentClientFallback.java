package com.lawfirm.staff.client.lawyer.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.lawyer.DocumentClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import com.lawfirm.staff.model.request.lawyer.document.*;
import com.lawfirm.staff.model.response.lawyer.document.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Component
public class DocumentClientFallback extends FeignFallbackConfig implements DocumentClient {

    private static final String SERVICE_NAME = "文档服务";

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<DocumentResponse>> page(Integer pageNum, Integer pageSize, String keyword) {
        Type type = createParameterizedType(Result.class, createParameterizedType(PageResult.class, DocumentResponse.class));
        return handleFallback(SERVICE_NAME, "page", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<DocumentResponse> upload(MultipartFile file) {
        Type type = createParameterizedType(Result.class, DocumentResponse.class);
        return handleFallback(SERVICE_NAME, "upload", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> delete(Long id) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "delete", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<DocumentResponse> get(Long id) {
        Type type = createParameterizedType(Result.class, DocumentResponse.class);
        return handleFallback(SERVICE_NAME, "get", type);
    }

    @Override
    public void download(Long id) {
        log.error("{}服务的download方法调用失败", SERVICE_NAME);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<DocumentCategoryResponse>> getCategories() {
        Type type = createParameterizedType(Result.class, createParameterizedType(List.class, DocumentCategoryResponse.class));
        return handleFallback(SERVICE_NAME, "getCategories", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<DocumentStatisticsResponse> getStatistics() {
        Type type = createParameterizedType(Result.class, DocumentStatisticsResponse.class);
        return handleFallback(SERVICE_NAME, "getStatistics", type);
    }
} 