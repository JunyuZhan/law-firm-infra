package com.lawfirm.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.web.controller.BaseController;
import com.lawfirm.common.web.response.R;
import com.lawfirm.model.system.dto.SysUpgradeDTO;
import com.lawfirm.model.system.dto.UpgradePackageDTO;
import com.lawfirm.model.system.entity.SysUpgrade;
import com.lawfirm.model.system.vo.SysUpgradeVO;
import com.lawfirm.system.service.SysUpgradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "系统升级管理")
@RestController
@RequestMapping("/system/upgrade")
@RequiredArgsConstructor
public class SysUpgradeController extends BaseController {

    private final SysUpgradeService upgradeService;

    @PostMapping("/upload")
    @Operation(summary = "上传升级包")
    public R<SysUpgradeVO> upload(@RequestBody UpgradePackageDTO packageDTO) {
        return R.ok(upgradeService.upload(packageDTO));
    }

    @Operation(summary = "分页查询升级记录")
    @GetMapping("/page")
    public R<PageResult<SysUpgradeVO>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            SysUpgradeDTO query) {
        QueryWrapper<SysUpgrade> wrapper = new QueryWrapper<>();
        // 构建查询条件
        if (StringUtils.hasText(query.getVersion())) {
            wrapper.like("version", query.getVersion());
        }
        if (query.getStatus() != null) {
            wrapper.eq("status", query.getStatus());
        }
        return R.ok(upgradeService.pageVO(buildPage(), wrapper));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取升级详情")
    public R<SysUpgradeVO> getById(@PathVariable Long id) {
        return R.ok(upgradeService.findById(id));
    }

    @PostMapping("/{id}/execute")
    @Operation(summary = "执行升级")
    public R<Void> execute(@PathVariable Long id) {
        upgradeService.execute(id);
        return R.ok();
    }

    @GetMapping("/log")
    @Operation(summary = "分页查询升级日志")
    public R<PageResult<SysUpgradeVO>> log() {
        QueryWrapper<SysUpgrade> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("upgrade_time");
        return R.ok(upgradeService.pageVO(buildPage(), wrapper));
    }

    @Operation(summary = "查询升级记录列表")
    @GetMapping("/list")
    public R<List<SysUpgradeVO>> list(SysUpgradeDTO query) {
        QueryWrapper<SysUpgrade> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(query.getVersion())) {
            wrapper.like("version", query.getVersion());
        }
        if (query.getStatus() != null) {
            wrapper.eq("status", query.getStatus());
        }
        return R.ok(upgradeService.listVO(wrapper));
    }
} 