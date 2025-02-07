package com.lawfirm.staff.client.finance.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.finance.AssetClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import com.lawfirm.staff.model.request.finance.asset.*;
import com.lawfirm.staff.model.response.finance.asset.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Component
public class AssetClientFallback extends FeignFallbackConfig implements AssetClient {

    private static final String SERVICE_NAME = "资产服务";

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<AssetResponse>> page(Integer pageNum, Integer pageSize, String keyword, String orderBy, String orderType) {
        Type type = createParameterizedType(Result.class, createParameterizedType(PageResult.class, AssetResponse.class));
        return handleFallback(SERVICE_NAME, "page", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> create(AssetCreateRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "create", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> update(AssetUpdateRequest request) {
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
    public Result<AssetResponse> get(Long id) {
        Type type = createParameterizedType(Result.class, AssetResponse.class);
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
    public Result<AssetStatisticsResponse> getStatistics() {
        Type type = createParameterizedType(Result.class, AssetStatisticsResponse.class);
        return handleFallback(SERVICE_NAME, "getStatistics", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> transfer(Long id, AssetTransferRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "transfer", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> maintenance(Long id, AssetMaintenanceRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "maintenance", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> scrap(Long id, AssetScrapRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "scrap", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<AssetCategoryResponse>> getCategories() {
        Type type = createParameterizedType(Result.class, createParameterizedType(List.class, AssetCategoryResponse.class));
        return handleFallback(SERVICE_NAME, "getCategories", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<AssetLocationResponse>> getLocations() {
        Type type = createParameterizedType(Result.class, createParameterizedType(List.class, AssetLocationResponse.class));
        return handleFallback(SERVICE_NAME, "getLocations", type);
    }

    @Override
    public void export(AssetExportRequest request) {
        log.error("{}服务的export方法调用失败", SERVICE_NAME);
    }

    @Override
    public void downloadTemplate() {
        log.error("{}服务的downloadTemplate方法调用失败", SERVICE_NAME);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<AssetImportResponse> importAssets(AssetImportRequest request) {
        Type type = createParameterizedType(Result.class, AssetImportResponse.class);
        return handleFallback(SERVICE_NAME, "importAssets", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<AssetInventoryResponse>> getInventoryRecords(AssetInventoryPageRequest request) {
        Type type = createParameterizedType(Result.class, createParameterizedType(PageResult.class, AssetInventoryResponse.class));
        return handleFallback(SERVICE_NAME, "getInventoryRecords", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> startInventory(AssetInventoryStartRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "startInventory", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> completeInventory(AssetInventoryCompleteRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "completeInventory", type);
    }
} 