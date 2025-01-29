package com.lawfirm.admin.controller.system;

import com.lawfirm.common.core.model.ApiResult;
import com.lawfirm.system.model.dto.SysConfigDTO;
import com.lawfirm.system.model.vo.SysConfigVO;
import com.lawfirm.system.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 系统参数配置Controller
 */
@Api(tags = "系统参数配置")
@RestController
@RequestMapping("/system/config")
@RequiredArgsConstructor
public class SysConfigController {

    private final SysConfigService configService;

    @ApiOperation("创建系统参数")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> createConfig(@Valid @RequestBody SysConfigDTO dto) {
        configService.createConfig(dto);
        return ApiResult.ok();
    }

    @ApiOperation("更新系统参数")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> updateConfig(@PathVariable Long id, @Valid @RequestBody SysConfigDTO dto) {
        configService.updateConfig(id, dto);
        return ApiResult.ok();
    }

    @ApiOperation("删除系统参数")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> deleteConfig(@PathVariable Long id) {
        configService.deleteConfig(id);
        return ApiResult.ok();
    }

    @ApiOperation("获取系统参数详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<SysConfigVO> getConfig(@PathVariable Long id) {
        return ApiResult.ok(configService.getConfig(id));
    }

    @ApiOperation("分页查询系统参数")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<SysConfigVO>> pageConfigs(@RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(required = false) String configKey,
                                                   @RequestParam(required = false) String configName) {
        return ApiResult.ok(configService.pageConfigs(pageNum, pageSize, configKey, configName));
    }

    @ApiOperation("根据参数键获取参数值")
    @GetMapping("/value/{key}")
    public ApiResult<String> getConfigValue(@PathVariable String key) {
        return ApiResult.ok(configService.getConfigValue(key));
    }

    @ApiOperation("刷新系统参数缓存")
    @PostMapping("/refresh")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> refreshConfigCache() {
        configService.refreshConfigCache();
        return ApiResult.ok();
    }

    @ApiOperation("检查参数键是否存在")
    @GetMapping("/check/{key}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Boolean> checkConfigKeyExists(@PathVariable String key) {
        return ApiResult.ok(configService.checkConfigKeyExists(key));
    }
} 