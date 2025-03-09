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

/**
 * 系统升级控制器
 */
@RestController
@RequestMapping("/system/upgrade")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UpgradeController extends BaseController {

    private final UpgradeService upgradeService;

    /**
     * 获取升级记录列表
     */
    @GetMapping("/list")
    public CommonResult<List<UpgradeVO>> list(UpgradeQueryDTO queryDTO) {
        List<UpgradeVO> list = upgradeService.selectUpgradeList(queryDTO);
        return CommonResult.success(list);
    }

    /**
     * 获取升级记录详情
     */
    @GetMapping("/{id}")
    public CommonResult<UpgradeVO> getById(@PathVariable Long id) {
        UpgradeVO upgrade = upgradeService.selectUpgradeById(id);
        return CommonResult.success(upgrade);
    }

    /**
     * 新增升级记录
     */
    @PostMapping
    @Log(title = "系统升级", businessType = "INSERT")
    public CommonResult<Void> add(@RequestBody @Validated UpgradeCreateDTO createDTO) {
        upgradeService.insertUpgrade(createDTO);
        return CommonResult.success();
    }

    /**
     * 修改升级记录
     */
    @PutMapping("/{id}")
    @Log(title = "系统升级", businessType = "UPDATE")
    public CommonResult<Void> update(@PathVariable Long id, @RequestBody @Validated UpgradeUpdateDTO updateDTO) {
        updateDTO.setId(id);
        upgradeService.updateUpgrade(updateDTO);
        return CommonResult.success();
    }

    /**
     * 删除升级记录
     */
    @DeleteMapping("/{id}")
    @Log(title = "系统升级", businessType = "DELETE")
    public CommonResult<Void> delete(@PathVariable Long id) {
        upgradeService.deleteUpgradeById(id);
        return CommonResult.success();
    }

    /**
     * 执行系统升级
     */
    @PostMapping("/{id}/execute")
    @Log(title = "系统升级", businessType = "UPDATE")
    public CommonResult<Void> executeUpgrade(@PathVariable Long id) {
        upgradeService.executeUpgrade(id);
        return CommonResult.success();
    }

    /**
     * 回滚系统升级
     */
    @PostMapping("/{id}/rollback")
    @Log(title = "系统升级", businessType = "UPDATE")
    public CommonResult<Void> rollbackUpgrade(@PathVariable Long id) {
        upgradeService.rollbackUpgrade(id);
        return CommonResult.success();
    }

    /**
     * 获取补丁列表
     */
    @GetMapping("/{upgradeId}/patches")
    public CommonResult<List<PatchVO>> listPatches(@PathVariable Long upgradeId, PatchQueryDTO queryDTO) {
        queryDTO.setUpgradeId(upgradeId);
        List<PatchVO> list = upgradeService.selectPatchList(queryDTO);
        return CommonResult.success(list);
    }

    /**
     * 上传补丁文件
     */
    @PostMapping("/{upgradeId}/patches/upload")
    @Log(title = "补丁管理", businessType = "INSERT")
    public CommonResult<String> uploadPatchFile(@PathVariable Long upgradeId, @RequestParam("file") MultipartFile file) {
        String filePath = upgradeService.uploadPatchFile(upgradeId, file);
        return CommonResult.success(filePath);
    }

    /**
     * 新增补丁
     */
    @PostMapping("/{upgradeId}/patches")
    @Log(title = "补丁管理", businessType = "INSERT")
    public CommonResult<Void> addPatch(@PathVariable Long upgradeId, @RequestBody @Validated PatchCreateDTO createDTO) {
        createDTO.setUpgradeId(upgradeId);
        upgradeService.insertPatch(createDTO);
        return CommonResult.success();
    }

    /**
     * 删除补丁
     */
    @DeleteMapping("/{upgradeId}/patches/{patchId}")
    @Log(title = "补丁管理", businessType = "DELETE")
    public CommonResult<Void> deletePatch(@PathVariable Long upgradeId, @PathVariable Long patchId) {
        upgradeService.deletePatchById(patchId);
        return CommonResult.success();
    }

    /**
     * 执行补丁
     */
    @PostMapping("/{upgradeId}/patches/{patchId}/execute")
    @Log(title = "补丁管理", businessType = "UPDATE")
    public CommonResult<Void> executePatch(@PathVariable Long upgradeId, @PathVariable Long patchId) {
        upgradeService.executePatch(patchId);
        return CommonResult.success();
    }

    /**
     * 回滚补丁
     */
    @PostMapping("/{upgradeId}/patches/{patchId}/rollback")
    @Log(title = "补丁管理", businessType = "UPDATE")
    public CommonResult<Void> rollbackPatch(@PathVariable Long upgradeId, @PathVariable Long patchId) {
        upgradeService.rollbackPatch(patchId);
        return CommonResult.success();
    }

    /**
     * 检查系统更新
     */
    @GetMapping("/check")
    public CommonResult<Boolean> checkUpdate() {
        boolean hasUpdate = upgradeService.checkUpdate();
        return CommonResult.success(hasUpdate);
    }
} 