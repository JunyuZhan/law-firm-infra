package com.lawfirm.archive.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.archive.dto.ArchiveSyncRecordDTO;
import com.lawfirm.model.archive.dto.SyncConfigDTO;
import com.lawfirm.model.archive.service.ArchiveSyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.lawfirm.archive.constant.ArchiveBusinessConstants;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;

/**
 * 档案同步控制器
 */
@RestController("archiveSyncController")
@RequestMapping(ArchiveBusinessConstants.Controller.API_SYNC_PREFIX)
@RequiredArgsConstructor
@Tag(name = "档案同步", description = "档案同步相关接口")
@Slf4j
public class ArchiveSyncController {

    private final ArchiveSyncService archiveSyncService;
    
    /**
     * 同步单个档案
     */
    @PostMapping("/single")
    @Operation(summary = "同步单个档案", description = "同步单个档案到外部系统")
    @PreAuthorize("hasAuthority('" + ARCHIVE_EDIT + "')")
    public CommonResult<Boolean> syncArchive(
            @Parameter(description = "档案ID") @RequestParam String archiveId,
            @Parameter(description = "外部系统编码") @RequestParam String systemCode) {
        
        boolean result = archiveSyncService.syncArchive(archiveId, systemCode);
        return CommonResult.success(result);
    }
    
    /**
     * 批量同步档案
     */
    @PostMapping("/batch")
    @Operation(summary = "批量同步档案", description = "批量同步档案到外部系统")
    @PreAuthorize("hasAuthority('" + ARCHIVE_EDIT + "')")
    public CommonResult<Map<String, Boolean>> batchSyncArchives(
            @Parameter(description = "档案ID列表") @RequestBody List<String> archiveIds,
            @Parameter(description = "外部系统编码") @RequestParam String systemCode) {
        
        Map<String, Boolean> results = archiveSyncService.batchSyncArchives(archiveIds, systemCode);
        return CommonResult.success(results);
    }
    
    /**
     * 同步所有未同步档案
     */
    @PostMapping("/pending")
    @Operation(summary = "同步所有未同步档案", description = "同步所有未同步的档案到外部系统")
    @PreAuthorize("hasAuthority('" + ARCHIVE_EDIT + "')")
    public CommonResult<Map<String, Boolean>> syncPendingArchives(
            @Parameter(description = "外部系统编码") @RequestParam String systemCode) {
        
        Map<String, Boolean> results = archiveSyncService.syncPendingArchives(systemCode);
        return CommonResult.success(results);
    }
    
    /**
     * 添加或更新同步配置
     */
    @PostMapping("/config")
    @Operation(summary = "添加或更新同步配置", description = "添加或更新外部系统同步配置")
    @PreAuthorize("hasAuthority('" + ARCHIVE_EDIT + "')")
    public CommonResult<Boolean> saveOrUpdateSyncConfig(@RequestBody SyncConfigDTO syncConfigDTO) {
        
        boolean result = archiveSyncService.saveOrUpdateSyncConfig(syncConfigDTO);
        return CommonResult.success(result);
    }
    
    /**
     * 获取同步配置详情
     */
    @GetMapping("/config/{systemCode}")
    @Operation(summary = "获取同步配置详情", description = "获取外部系统同步配置详情")
    @PreAuthorize("hasAuthority('" + ARCHIVE_VIEW + "')")
    public CommonResult<SyncConfigDTO> getSyncConfig(@PathVariable String systemCode) {
        
        SyncConfigDTO config = archiveSyncService.getSyncConfig(systemCode);
        return CommonResult.success(config);
    }
    
    /**
     * 获取所有同步配置
     */
    @GetMapping("/config/list")
    @Operation(summary = "获取所有同步配置", description = "获取所有外部系统同步配置")
    @PreAuthorize("hasAuthority('" + ARCHIVE_VIEW + "')")
    public CommonResult<List<SyncConfigDTO>> listAllSyncConfigs() {
        
        List<SyncConfigDTO> configs = archiveSyncService.listAllSyncConfigs();
        return CommonResult.success(configs);
    }
    
    /**
     * 删除同步配置
     */
    @DeleteMapping("/config/{systemCode}")
    @Operation(summary = "删除同步配置", description = "删除外部系统同步配置")
    @PreAuthorize("hasAuthority('" + ARCHIVE_DELETE + "')")
    public CommonResult<Boolean> deleteSyncConfig(@PathVariable String systemCode) {
        
        boolean result = archiveSyncService.deleteSyncConfig(systemCode);
        return CommonResult.success(result);
    }
    
    /**
     * 启用同步配置
     */
    @PutMapping("/config/{systemCode}/enable")
    @Operation(summary = "启用同步配置", description = "启用外部系统同步配置")
    @PreAuthorize("hasAuthority('" + ARCHIVE_EDIT + "')")
    public CommonResult<Boolean> enableSyncConfig(@PathVariable String systemCode) {
        
        boolean result = archiveSyncService.enableSyncConfig(systemCode);
        return CommonResult.success(result);
    }
    
    /**
     * 禁用同步配置
     */
    @PutMapping("/config/{systemCode}/disable")
    @Operation(summary = "禁用同步配置", description = "禁用外部系统同步配置")
    @PreAuthorize("hasAuthority('" + ARCHIVE_EDIT + "')")
    public CommonResult<Boolean> disableSyncConfig(@PathVariable String systemCode) {
        
        boolean result = archiveSyncService.disableSyncConfig(systemCode);
        return CommonResult.success(result);
    }
    
    /**
     * 查询同步记录
     */
    @GetMapping("/records")
    @Operation(summary = "查询同步记录", description = "分页查询档案同步记录")
    @PreAuthorize("hasAuthority('" + ARCHIVE_VIEW + "')")
    public CommonResult<Page<ArchiveSyncRecordDTO>> pageSyncRecords(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "档案ID") @RequestParam(required = false) String archiveId,
            @Parameter(description = "外部系统编码") @RequestParam(required = false) String systemCode) {
        
        Page<ArchiveSyncRecordDTO> page = archiveSyncService.pageSyncRecords(new Page<>(current, size), archiveId, systemCode);
        return CommonResult.success(page);
    }
} 