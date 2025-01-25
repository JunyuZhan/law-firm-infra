package com.lawfirm.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.model.ApiResult;
import com.lawfirm.system.model.dto.UpgradePackageDTO;
import com.lawfirm.system.model.vo.UpgradeLogVO;
import com.lawfirm.system.model.vo.UpgradePackageVO;
import com.lawfirm.system.service.SysUpgradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * 系统升级Controller
 */
@Api(tags = "系统升级管理")
@RestController
@RequestMapping("/api/system/upgrade")
@RequiredArgsConstructor
public class SysUpgradeController {
    
    private final SysUpgradeService upgradeService;
    
    @PostMapping("/upload")
    @ApiOperation("上传升级包")
    public ApiResult<UpgradePackageVO> uploadPackage(
        @RequestParam("file") MultipartFile file,
        @Valid @RequestPart("dto") UpgradePackageDTO dto) {
        return ApiResult.success(upgradeService.uploadPackage(file, dto));
    }
    
    @GetMapping("/packages")
    @ApiOperation("获取升级包列表")
    public ApiResult<Page<UpgradePackageVO>> getPackages(
        @RequestParam(defaultValue = "1") long current,
        @RequestParam(defaultValue = "10") long size) {
        return ApiResult.success(upgradeService.getPackages(new Page<>(current, size)));
    }
    
    @PostMapping("/execute/{packageId}")
    @ApiOperation("执行升级")
    public ApiResult<Void> executeUpgrade(@PathVariable Long packageId) {
        upgradeService.executeUpgrade(packageId);
        return ApiResult.success();
    }
    
    @PostMapping("/rollback/{packageId}")
    @ApiOperation("回滚升级")
    public ApiResult<Void> rollbackUpgrade(@PathVariable Long packageId) {
        upgradeService.rollbackUpgrade(packageId);
        return ApiResult.success();
    }
    
    @GetMapping("/logs/{packageId}")
    @ApiOperation("获取升级日志")
    public ApiResult<List<UpgradeLogVO>> getLogs(@PathVariable Long packageId) {
        return ApiResult.success(upgradeService.getLogs(packageId));
    }
} 