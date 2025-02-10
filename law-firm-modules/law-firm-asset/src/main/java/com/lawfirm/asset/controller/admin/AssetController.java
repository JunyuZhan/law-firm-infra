package com.lawfirm.asset.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.asset.dto.request.AssetAddRequest;
import com.lawfirm.asset.dto.request.AssetQueryRequest;
import com.lawfirm.asset.dto.request.AssetUpdateRequest;
import com.lawfirm.asset.dto.response.AssetResponse;
import com.lawfirm.asset.service.IAssetService;
import com.lawfirm.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 资产管理接口
 */
@Tag(name = "资产管理接口")
@RestController
@RequestMapping("/admin/asset")
@RequiredArgsConstructor
public class AssetController {

    private final IAssetService assetService;

    @Operation(summary = "添加资产")
    @PostMapping
    public Result<Long> addAsset(@Validated @RequestBody AssetAddRequest request) {
        return Result.success(assetService.addAsset(request));
    }

    @Operation(summary = "更新资产")
    @PutMapping
    public Result<Void> updateAsset(@Validated @RequestBody AssetUpdateRequest request) {
        assetService.updateAsset(request);
        return Result.success();
    }

    @Operation(summary = "删除资产")
    @DeleteMapping("/{id}")
    public Result<Void> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return Result.success();
    }

    @Operation(summary = "分页查询资产")
    @GetMapping("/page")
    public Result<IPage<AssetResponse>> pageAssets(@Validated AssetQueryRequest request) {
        return Result.success(assetService.pageAssets(request));
    }

    @Operation(summary = "获取资产详情")
    @GetMapping("/{id}")
    public Result<AssetResponse> getAsset(@PathVariable Long id) {
        return Result.success(assetService.getAssetById(id));
    }
}
