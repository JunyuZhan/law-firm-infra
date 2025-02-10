package com.lawfirm.staff.controller.finance;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.staff.model.request.finance.asset.AssetCreateRequest;
import com.lawfirm.staff.model.request.finance.asset.AssetPageRequest;
import com.lawfirm.staff.model.request.finance.asset.AssetUpdateRequest;
import com.lawfirm.staff.model.response.finance.asset.AssetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "资产管理")
@RestController
@RequestMapping("/finance/asset")
@RequiredArgsConstructor
public class AssetController {

    @Operation(summary = "分页查询资产")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('finance:asset:query')")
    public Result<PageResult<AssetResponse>> page(AssetPageRequest request) {
        // TODO: 调用资产服务
        return Result.ok();
    }

    @Operation(summary = "创建资产")
    @PostMapping
    @PreAuthorize("hasAuthority('finance:asset:create')")
    @OperationLog(value = "创建资产")
    public Result<AssetResponse> create(@RequestBody @Validated AssetCreateRequest request) {
        // TODO: 调用资产服务
        return Result.ok();
    }

    @Operation(summary = "修改资产")
    @PutMapping
    @PreAuthorize("hasAuthority('finance:asset:update')")
    @OperationLog(value = "修改资产")
    public Result<Void> update(@RequestBody @Validated AssetUpdateRequest request) {
        // TODO: 调用资产服务
        return Result.ok();
    }

    @Operation(summary = "删除资产")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('finance:asset:delete')")
    @OperationLog(value = "删除资产")
    public Result<Void> delete(@PathVariable Long id) {
        // TODO: 调用资产服务
        return Result.ok();
    }

    @Operation(summary = "获取资产详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('finance:asset:query')")
    public Result<AssetResponse> get(@PathVariable Long id) {
        // TODO: 调用资产服务
        return Result.ok();
    }

    @Operation(summary = "资产折旧")
    @PutMapping("/depreciate/{id}")
    @PreAuthorize("hasAuthority('finance:asset:depreciate')")
    @OperationLog(value = "资产折旧")
    public Result<Void> depreciate(@PathVariable Long id) {
        // TODO: 调用资产服务
        return Result.ok();
    }

    @Operation(summary = "资产报废")
    @PutMapping("/scrap/{id}")
    @PreAuthorize("hasAuthority('finance:asset:scrap')")
    @OperationLog(value = "资产报废")
    public Result<Void> scrap(@PathVariable Long id) {
        // TODO: 调用资产服务
        return Result.ok();
    }

    @Operation(summary = "导出资产")
    @GetMapping("/export")
    @PreAuthorize("hasAuthority('finance:asset:export')")
    @OperationLog(value = "导出资产")
    public void export(AssetPageRequest request) {
        // TODO: 调用资产服务
    }
} 