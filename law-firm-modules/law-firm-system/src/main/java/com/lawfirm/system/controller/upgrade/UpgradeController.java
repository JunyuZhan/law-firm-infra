package com.lawfirm.system.controller.upgrade;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.system.dto.upgrade.PatchCreateDTO;
import com.lawfirm.model.system.dto.upgrade.PatchQueryDTO;
import com.lawfirm.model.system.dto.upgrade.UpgradeCreateDTO;
import com.lawfirm.model.system.dto.upgrade.UpgradeQueryDTO;
import com.lawfirm.model.system.dto.upgrade.UpgradeUpdateDTO;
import com.lawfirm.model.system.service.UpgradeService;
import com.lawfirm.model.system.vo.upgrade.PatchVO;
import com.lawfirm.model.system.vo.upgrade.UpgradeVO;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 系统升级控制器
 */
@Tag(name = "系统升级管理", description = "管理系统升级，包括版本升级、脚本执行等操作")
@RestController("upgradeController")
@RequestMapping("/system/upgrade")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UpgradeController extends BaseController {

    private final UpgradeService upgradeService;

    /**
     * 获取升级记录列表
     */
    @Operation(
        summary = "获取升级记录列表",
        description = "获取系统升级记录列表，支持按版本号、状态等条件筛选"
    )
    @GetMapping("/list")
    public CommonResult<List<UpgradeVO>> list(
            @Parameter(description = "查询参数") UpgradeQueryDTO queryDTO) {
        List<UpgradeVO> list = upgradeService.selectUpgradeList(queryDTO);
        return CommonResult.success(list);
    }

    /**
     * 获取升级记录详情
     */
    @Operation(
        summary = "获取升级记录详情",
        description = "根据ID获取升级记录的详细信息，包括升级内容、状态等"
    )
    @GetMapping("/{id}")
    public CommonResult<UpgradeVO> getById(
            @Parameter(description = "升级记录ID") @PathVariable Long id) {
        UpgradeVO upgrade = upgradeService.selectUpgradeById(id);
        return CommonResult.success(upgrade);
    }

    /**
     * 新增升级记录
     */
    @Operation(
        summary = "新增升级记录",
        description = "创建新的系统升级记录，包括版本号、升级内容等信息"
    )
    @PostMapping
    @Log(title = "系统升级", businessType = "INSERT")
    public CommonResult<Void> add(
            @Parameter(description = "创建参数") @RequestBody @Validated UpgradeCreateDTO createDTO) {
        upgradeService.insertUpgrade(createDTO);
        return CommonResult.success();
    }

    /**
     * 修改升级记录
     */
    @Operation(
        summary = "修改升级记录",
        description = "更新已存在的升级记录信息，包括版本号、升级内容等"
    )
    @PutMapping("/{id}")
    @Log(title = "系统升级", businessType = "UPDATE")
    public CommonResult<Void> update(
            @Parameter(description = "升级记录ID") @PathVariable Long id,
            @Parameter(description = "更新参数") @RequestBody @Validated UpgradeUpdateDTO updateDTO) {
        updateDTO.setId(id);
        upgradeService.updateUpgrade(updateDTO);
        return CommonResult.success();
    }

    /**
     * 删除升级记录
     */
    @Operation(
        summary = "删除升级记录",
        description = "根据ID删除升级记录，同时删除相关的补丁文件"
    )
    @DeleteMapping("/{id}")
    @Log(title = "系统升级", businessType = "DELETE")
    public CommonResult<Void> delete(
            @Parameter(description = "升级记录ID") @PathVariable Long id) {
        upgradeService.deleteUpgradeById(id);
        return CommonResult.success();
    }

    /**
     * 执行系统升级
     */
    @Operation(
        summary = "执行系统升级",
        description = "执行指定的系统升级，包括执行升级脚本和补丁"
    )
    @PostMapping("/{id}/execute")
    @Log(title = "系统升级", businessType = "UPDATE")
    public CommonResult<Void> executeUpgrade(
            @Parameter(description = "升级记录ID") @PathVariable Long id) {
        upgradeService.executeUpgrade(id);
        return CommonResult.success();
    }

    /**
     * 回滚系统升级
     */
    @Operation(
        summary = "回滚系统升级",
        description = "回滚指定的系统升级，恢复到升级前的状态"
    )
    @PostMapping("/{id}/rollback")
    @Log(title = "系统升级", businessType = "UPDATE")
    public CommonResult<Void> rollbackUpgrade(
            @Parameter(description = "升级记录ID") @PathVariable Long id) {
        upgradeService.rollbackUpgrade(id);
        return CommonResult.success();
    }

    /**
     * 获取补丁列表
     */
    @Operation(
        summary = "获取补丁列表",
        description = "获取指定升级记录下的补丁列表，支持按状态等条件筛选"
    )
    @GetMapping("/{upgradeId}/patches")
    public CommonResult<List<PatchVO>> listPatches(
            @Parameter(description = "升级记录ID") @PathVariable Long upgradeId,
            @Parameter(description = "查询参数") PatchQueryDTO queryDTO) {
        queryDTO.setUpgradeId(upgradeId);
        List<PatchVO> list = upgradeService.selectPatchList(queryDTO);
        return CommonResult.success(list);
    }

    /**
     * 上传补丁文件
     */
    @Operation(
        summary = "上传补丁文件",
        description = "上传补丁文件到指定的升级记录，支持多种文件格式"
    )
    @PostMapping("/{upgradeId}/patches/upload")
    @Log(title = "补丁管理", businessType = "INSERT")
    public CommonResult<String> uploadPatchFile(
            @Parameter(description = "升级记录ID") @PathVariable Long upgradeId,
            @Parameter(description = "补丁文件") @RequestParam("file") MultipartFile file) {
        String filePath = upgradeService.uploadPatchFile(upgradeId, file);
        return CommonResult.success(filePath);
    }

    /**
     * 新增补丁
     */
    @Operation(
        summary = "新增补丁",
        description = "创建新的补丁记录，包括补丁名称、描述、文件路径等信息"
    )
    @PostMapping("/{upgradeId}/patches")
    @Log(title = "补丁管理", businessType = "INSERT")
    public CommonResult<Void> addPatch(
            @Parameter(description = "升级记录ID") @PathVariable Long upgradeId,
            @Parameter(description = "创建参数") @RequestBody @Validated PatchCreateDTO createDTO) {
        createDTO.setUpgradeId(upgradeId);
        upgradeService.insertPatch(createDTO);
        return CommonResult.success();
    }

    /**
     * 删除补丁
     */
    @Operation(
        summary = "删除补丁",
        description = "删除指定的补丁记录，同时删除补丁文件"
    )
    @DeleteMapping("/{upgradeId}/patches/{patchId}")
    @Log(title = "补丁管理", businessType = "DELETE")
    public CommonResult<Void> deletePatch(
            @Parameter(description = "升级记录ID") @PathVariable Long upgradeId,
            @Parameter(description = "补丁ID") @PathVariable Long patchId) {
        upgradeService.deletePatchById(patchId);
        return CommonResult.success();
    }

    /**
     * 执行补丁
     */
    @Operation(
        summary = "执行补丁",
        description = "执行指定的补丁，应用补丁中的更改"
    )
    @PostMapping("/{upgradeId}/patches/{patchId}/execute")
    @Log(title = "补丁管理", businessType = "UPDATE")
    public CommonResult<Void> executePatch(
            @Parameter(description = "升级记录ID") @PathVariable Long upgradeId,
            @Parameter(description = "补丁ID") @PathVariable Long patchId) {
        upgradeService.executePatch(patchId);
        return CommonResult.success();
    }

    /**
     * 回滚补丁
     */
    @Operation(
        summary = "回滚补丁",
        description = "回滚指定的补丁，恢复到应用补丁前的状态"
    )
    @PostMapping("/{upgradeId}/patches/{patchId}/rollback")
    @Log(title = "补丁管理", businessType = "UPDATE")
    public CommonResult<Void> rollbackPatch(
            @Parameter(description = "升级记录ID") @PathVariable Long upgradeId,
            @Parameter(description = "补丁ID") @PathVariable Long patchId) {
        upgradeService.rollbackPatch(patchId);
        return CommonResult.success();
    }

    /**
     * 检查系统更新
     */
    @Operation(
        summary = "检查系统更新",
        description = "检查是否有可用的系统更新，返回是否有更新的标志"
    )
    @GetMapping("/check")
    public CommonResult<Boolean> checkUpdate() {
        boolean hasUpdate = upgradeService.checkUpdate();
        return CommonResult.success(hasUpdate);
    }
} 