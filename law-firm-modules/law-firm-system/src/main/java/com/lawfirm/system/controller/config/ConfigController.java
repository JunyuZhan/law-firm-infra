package com.lawfirm.system.controller.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.model.system.dto.config.ConfigCreateDTO;
import com.lawfirm.model.system.dto.config.ConfigUpdateDTO;
import com.lawfirm.model.system.entity.SysConfig;
import com.lawfirm.model.system.service.ConfigService;
import com.lawfirm.model.system.vo.ConfigVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统配置控制器
 */
@Tag(name = "系统配置管理", description = "管理系统配置数据，包括配置的增删改查、缓存刷新等操作")
@RestController
@RequestMapping("/system/config")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ConfigController extends BaseController {

    private final ConfigService configService;

    /**
     * 获取配置列表
     */
    @Operation(
        summary = "获取配置列表",
        description = "分页获取配置列表，支持按配置名称、键名、类型等条件筛选"
    )
    @GetMapping("/list")
    @RequiresPermissions("system:config:list")
    public CommonResult<Page<ConfigVO>> list(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "配置名称") @RequestParam(required = false) String configName,
            @Parameter(description = "配置键名") @RequestParam(required = false) String configKey,
            @Parameter(description = "配置类型") @RequestParam(required = false) String configType) {
        
        Page<SysConfig> page = new Page<>(pageNum, pageSize);
        SysConfig config = new SysConfig();
        config.setConfigName(configName);
        config.setConfigKey(configKey);
        config.setConfigType(configType);
        
        Page<ConfigVO> result = configService.pageConfigs(page, config);
        return success(result);
    }

    /**
     * 获取配置详情
     */
    @Operation(
        summary = "获取配置详情",
        description = "根据配置ID获取配置的详细信息"
    )
    @GetMapping("/{id}")
    @RequiresPermissions("system:config:query")
    public CommonResult<ConfigVO> getInfo(@Parameter(description = "配置ID") @PathVariable Long id) {
        ConfigVO config = configService.getConfigById(id);
        return success(config);
    }

    /**
     * 根据键名获取配置
     */
    @Operation(
        summary = "根据键名获取配置",
        description = "根据配置键名获取配置信息，键名是配置的唯一业务标识"
    )
    @GetMapping("/key/{configKey}")
    public CommonResult<ConfigVO> getConfigByKey(@Parameter(description = "配置键名") @PathVariable String configKey) {
        ConfigVO config = configService.getConfigByKey(configKey);
        return success(config);
    }

    /**
     * 新增配置
     */
    @Operation(
        summary = "新增配置",
        description = "创建新的系统配置，包括配置名称、键名、值、类型等信息"
    )
    @PostMapping
    @RequiresPermissions("system:config:add")
    @Log(title = "系统配置", businessType = "INSERT")
    public CommonResult<Long> add(@Valid @RequestBody ConfigCreateDTO config) {
        Long id = configService.createConfig(config);
        return success(id);
    }

    /**
     * 修改配置
     */
    @Operation(
        summary = "修改配置",
        description = "更新已存在的系统配置信息，包括配置名称、键名、值、类型等"
    )
    @PutMapping("/{id}")
    @RequiresPermissions("system:config:edit")
    @Log(title = "系统配置", businessType = "UPDATE")
    public CommonResult<Void> edit(
            @Parameter(description = "配置ID") @PathVariable Long id,
            @Valid @RequestBody ConfigUpdateDTO config) {
        configService.updateConfig(id, config);
        return success();
    }

    /**
     * 删除配置
     */
    @Operation(
        summary = "删除配置",
        description = "根据ID删除单个系统配置"
    )
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:config:remove")
    @Log(title = "系统配置", businessType = "DELETE")
    public CommonResult<Void> remove(@Parameter(description = "配置ID") @PathVariable Long id) {
        configService.deleteConfig(id);
        return success();
    }

    /**
     * 批量删除配置
     */
    @Operation(
        summary = "批量删除配置",
        description = "批量删除多个系统配置"
    )
    @DeleteMapping("/batch")
    @RequiresPermissions("system:config:remove")
    @Log(title = "系统配置", businessType = "DELETE")
    public CommonResult<Void> batchRemove(@Parameter(description = "配置ID列表") @RequestBody List<Long> ids) {
        configService.deleteConfigs(ids);
        return success();
    }

    /**
     * 刷新配置缓存
     */
    @Operation(
        summary = "刷新配置缓存",
        description = "刷新系统中的配置缓存数据，确保配置的实时性"
    )
    @PostMapping("/refreshCache")
    @RequiresPermissions("system:config:refresh")
    @Log(title = "系统配置", businessType = "CLEAN")
    public CommonResult<Void> refreshCache() {
        configService.refreshCache();
        return success();
    }
} 