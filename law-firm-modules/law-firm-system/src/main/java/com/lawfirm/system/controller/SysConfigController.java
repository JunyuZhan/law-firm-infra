package com.lawfirm.system.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.model.system.entity.SysConfig;
import com.lawfirm.system.model.dto.SysConfigDTO;
import com.lawfirm.system.service.SysConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统参数配置控制器
 */
@Tag(name = "参数配置")
@RestController
@RequestMapping("/system/config")
@RequiredArgsConstructor
public class SysConfigController {

    private final SysConfigService configService;

    @Operation(summary = "创建参数配置")
    @PostMapping
    public R<Void> createConfig(@Validated @RequestBody SysConfigDTO config) {
        configService.createConfig(config);
        return R.ok();
    }

    @Operation(summary = "更新参数配置")
    @PutMapping
    public R<Void> updateConfig(@Validated @RequestBody SysConfigDTO config) {
        configService.updateConfig(config);
        return R.ok();
    }

    @Operation(summary = "删除参数配置")
    @DeleteMapping("/{id}")
    public R<Void> deleteConfig(@PathVariable Long id) {
        configService.deleteConfig(id);
        return R.ok();
    }

    @Operation(summary = "根据参数键名查询参数配置")
    @GetMapping("/key/{configKey}")
    public R<SysConfigDTO> getByKey(@PathVariable String configKey) {
        return R.ok(configService.getByKey(configKey));
    }

    @Operation(summary = "根据参数分组查询参数配置列表")
    @GetMapping("/group/{groupName}")
    public R<List<SysConfigDTO>> listByGroup(@PathVariable String groupName) {
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