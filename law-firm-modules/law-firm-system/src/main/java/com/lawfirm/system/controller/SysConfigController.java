package com.lawfirm.system.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.model.system.entity.SysConfig;
import com.lawfirm.model.system.dto.SysConfigDTO;
import com.lawfirm.model.system.vo.SysConfigVO;
import com.lawfirm.system.service.SysConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.lawfirm.common.web.controller.BaseController;

import java.util.List;

/**
 * 系统参数配置控制器
 */
@Tag(name = "系统配置管理")
@RestController
@RequestMapping("/system/config")
@RequiredArgsConstructor
public class SysConfigController extends BaseController {

    private final SysConfigService configService;

    @Operation(summary = "创建参数配置")
    @PostMapping
    public R<SysConfigVO> create(@Validated @RequestBody SysConfigDTO config) {
        return R.ok(configService.create(config));
    }

    @Operation(summary = "更新参数配置")
    @PutMapping
    public R<SysConfigVO> update(@Validated @RequestBody SysConfigDTO config) {
        return R.ok(configService.update(config));
    }

    @Operation(summary = "删除参数配置")
    @DeleteMapping("/{id}")
    public R<Void> deleteConfig(@PathVariable Long id) {
        configService.deleteConfig(id);
        return R.ok();
    }

    @Operation(summary = "根据参数键名查询参数配置")
    @GetMapping("/key/{configKey}")
    public R<SysConfigVO> getByKey(@PathVariable String configKey) {
        return R.ok(configService.getByKey(configKey));
    }

    @Operation(summary = "根据参数分组查询参数配置列表")
    @GetMapping("/group/{groupName}")
    public R<List<SysConfigVO>> listByGroup(@PathVariable String groupName) {
        return R.ok(configService.listByGroup(groupName));
    }

    @Operation(summary = "查询所有参数分组")
    @GetMapping("/groups")
    public R<List<String>> listAllGroups() {
        return R.ok(configService.listAllGroups());
    }

    @Operation(summary = "刷新参数缓存")
    @PostMapping("/refresh")
    public R<Void> refreshCache() {
        configService.refreshCache();
        return R.ok();
    }
} 