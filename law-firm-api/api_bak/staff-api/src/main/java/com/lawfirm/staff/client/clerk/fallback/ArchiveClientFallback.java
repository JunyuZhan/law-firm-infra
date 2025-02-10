package com.lawfirm.staff.client.clerk.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.clerk.ArchiveClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import com.lawfirm.staff.model.request.clerk.archive.*;
import com.lawfirm.staff.model.response.clerk.archive.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 档案管理服务降级实现
 */
@Slf4j
@Component
public class ArchiveClientFallback extends FeignFallbackConfig implements ArchiveClient {

    private static final String SERVICE_NAME = "档案管理服务";

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<ArchiveResponse>> page(Integer pageNum, Integer pageSize, String keyword, String orderBy, String orderType) {
        Type type = createParameterizedType(PageResult.class, ArchiveResponse.class);
        return (Result<PageResult<ArchiveResponse>>) handleFallback(SERVICE_NAME, "page", 
            String.format("参数：pageNum=%d, pageSize=%d, keyword=%s", pageNum, pageSize, keyword),
            type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> create(ArchiveCreateRequest request) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "create", 
            String.format("参数：request=%s", request),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> update(ArchiveUpdateRequest request) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "update", 
            String.format("参数：request=%s", request),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> delete(Long id) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "delete", 
            String.format("参数：id=%d", id),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<ArchiveResponse> get(Long id) {
        return (Result<ArchiveResponse>) handleFallback(SERVICE_NAME, "get", 
            String.format("参数：id=%d", id),
            ArchiveResponse.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<String> upload(MultipartFile file) {
        return (Result<String>) handleFallback(SERVICE_NAME, "upload", 
            String.format("文件名：%s", file.getOriginalFilename()),
            String.class);
    }

    @Override
    public void download(Long fileId) {
        log.error("{}服务的download方法调用失败，参数：fileId={}", SERVICE_NAME, fileId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<ArchiveCategoryResponse>> getCategories() {
        Type type = createParameterizedType(List.class, ArchiveCategoryResponse.class);
        return (Result<List<ArchiveCategoryResponse>>) handleFallback(SERVICE_NAME, "getCategories", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> batchDelete(List<Long> ids) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "batchDelete", 
            String.format("参数：ids=%s", ids),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<ArchiveStatisticsResponse> getStatistics() {
        return (Result<ArchiveStatisticsResponse>) handleFallback(SERVICE_NAME, "getStatistics", 
            ArchiveStatisticsResponse.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> borrow(ArchiveBorrowRequest request) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "borrow", 
            String.format("参数：request=%s", request),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> returnArchive(Long id) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "returnArchive", 
            String.format("参数：id=%d", id),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<ArchiveBorrowResponse>> getBorrowRecords(ArchiveBorrowPageRequest request) {
        Type type = createParameterizedType(PageResult.class, ArchiveBorrowResponse.class);
        return (Result<PageResult<ArchiveBorrowResponse>>) handleFallback(SERVICE_NAME, "getBorrowRecords", 
            String.format("参数：request=%s", request),
            type);
    }
} 