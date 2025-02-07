package com.lawfirm.staff.client.finance;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.finance.fallback.AssetClientFallback;
import com.lawfirm.staff.model.request.finance.asset.*;
import com.lawfirm.staff.model.response.finance.asset.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资产管理Feign客户端
 */
@FeignClient(
    name = "law-firm-finance",
    contextId = "assetClient",
    path = "/asset",
    fallback = AssetClientFallback.class
)
public interface AssetClient {
    
    @GetMapping("/page")
    Result<PageResult<AssetResponse>> page(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "orderType", required = false) String orderType);
    
    @PostMapping
    Result<Void> create(@RequestBody AssetCreateRequest request);
    
    @PutMapping
    Result<Void> update(@RequestBody AssetUpdateRequest request);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    Result<AssetResponse> get(@PathVariable("id") Long id);
    
    @PostMapping("/batch/delete")
    Result<Void> batchDelete(@RequestBody List<Long> ids);
    
    @GetMapping("/statistics")
    Result<AssetStatisticsResponse> getStatistics();
    
    @PostMapping("/transfer/{id}")
    Result<Void> transfer(@PathVariable("id") Long id, @RequestBody AssetTransferRequest request);
    
    @PostMapping("/maintenance/{id}")
    Result<Void> maintenance(@PathVariable("id") Long id, @RequestBody AssetMaintenanceRequest request);
    
    @PostMapping("/scrap/{id}")
    Result<Void> scrap(@PathVariable("id") Long id, @RequestBody AssetScrapRequest request);
    
    @GetMapping("/categories")
    Result<List<AssetCategoryResponse>> getCategories();
    
    @GetMapping("/locations")
    Result<List<AssetLocationResponse>> getLocations();
    
    @GetMapping("/export")
    void export(@SpringQueryMap AssetExportRequest request);
    
    @GetMapping("/template/download")
    void downloadTemplate();
    
    @PostMapping("/import")
    Result<AssetImportResponse> importAssets(@RequestBody AssetImportRequest request);
    
    @GetMapping("/inventory/records")
    Result<PageResult<AssetInventoryResponse>> getInventoryRecords(@SpringQueryMap AssetInventoryPageRequest request);
    
    @PostMapping("/inventory/start")
    Result<Void> startInventory(@RequestBody AssetInventoryStartRequest request);
    
    @PostMapping("/inventory/complete")
    Result<Void> completeInventory(@RequestBody AssetInventoryCompleteRequest request);
} 