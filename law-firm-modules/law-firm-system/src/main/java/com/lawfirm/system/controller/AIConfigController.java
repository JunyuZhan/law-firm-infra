package com.lawfirm.system.controller;

import com.lawfirm.model.ai.entity.AIModelConfig;
import com.lawfirm.model.ai.service.AIModelConfigService;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.system.constant.SystemConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "AI服务配置", description = "AI大模型密钥与参数配置")
@RestController
@RequestMapping(SystemConstants.API_AI_CONFIG_PREFIX)
public class AIConfigController {

    @Autowired
    private AIModelConfigService aiModelConfigService;

    @Operation(summary = "获取所有AI模型配置")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:config:view')")
    public CommonResult<List<AIModelConfig>> listConfigs() {
        List<AIModelConfig> configs = aiModelConfigService.listAll();
        return CommonResult.success(configs);
    }

    @Operation(summary = "新增AI模型配置")
    @PostMapping
    @PreAuthorize("hasAuthority('system:config:create')")
    public CommonResult<Void> addConfig(@RequestBody AIModelConfig config) {
        aiModelConfigService.add(config);
        return CommonResult.success();
    }

    @Operation(summary = "更新AI模型配置")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('system:config:edit')")
    public CommonResult<Void> updateConfig(@PathVariable Long id, @RequestBody AIModelConfig config) {
        config.setId(id);
        aiModelConfigService.update(config);
        return CommonResult.success();
    }

    @Operation(summary = "删除AI模型配置")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:config:delete')")
    public CommonResult<Void> deleteConfig(@PathVariable Long id) {
        aiModelConfigService.delete(id);
        return CommonResult.success();
    }

    @Operation(summary = "切换默认AI模型")
    @PostMapping("/switch/{id}")
    @PreAuthorize("hasAuthority('system:config:edit')")
    public CommonResult<Void> switchDefaultModel(@PathVariable Long id) {
        aiModelConfigService.setDefault(id);
        return CommonResult.success();
    }

    @Operation(summary = "获取当前默认AI模型配置")
    @GetMapping("/default")
    @PreAuthorize("hasAuthority('system:config:view')")
    public CommonResult<AIModelConfig> getDefaultConfig() {
        AIModelConfig config = aiModelConfigService.getDefault();
        return CommonResult.success(config);
    }
} 