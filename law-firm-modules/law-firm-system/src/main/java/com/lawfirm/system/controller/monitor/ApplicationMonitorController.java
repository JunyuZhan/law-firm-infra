package com.lawfirm.system.controller.monitor;

import com.lawfirm.common.core.api.CommonResult;

import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.model.system.entity.monitor.SysAppMonitor;
import com.lawfirm.system.constant.SystemConstants;
import com.lawfirm.system.service.impl.monitor.AppMonitorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应用监控控制器
 */
@Tag(name = "应用监控", description = "提供应用性能监控和管理功能")
@RestController("applicationMonitorController")
@RequestMapping(SystemConstants.API_APP_MONITOR_PREFIX)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Slf4j
public class ApplicationMonitorController extends BaseController {

    private final AppMonitorServiceImpl appMonitorService;

    /**
     * 获取应用监控列表
     */
    @Operation(
        summary = "获取应用监控列表",
        description = "获取系统中所有应用的监控数据列表"
    )
    @GetMapping
    @PreAuthorize("hasAuthority('system:monitor:app')")
    public CommonResult<List<SysAppMonitor>> getAppList() {
        log.info("获取应用监控列表");
        List<SysAppMonitor> appList = appMonitorService.getAppList();
        return success(appList);
    }

    /**
     * 获取应用最新监控数据
     */
    @Operation(
        summary = "获取应用最新监控数据",
        description = "获取指定应用的最新监控数据"
    )
    @GetMapping("/{appName}")
    @PreAuthorize("hasAuthority('system:monitor:app')")
    public CommonResult<SysAppMonitor> getLatestAppInfo(
            @Parameter(description = "应用名称") @PathVariable String appName,
            @Parameter(description = "实例ID") @RequestParam(required = false) String instanceId) {
        log.info("获取应用最新监控数据: appName={}, instanceId={}", appName, instanceId);
        SysAppMonitor appInfo = appMonitorService.getLatestAppInfo(appName, instanceId != null ? instanceId : "default");
        return success(appInfo);
    }

    /**
     * 触发应用监控数据收集
     */
    @Operation(
        summary = "触发应用监控数据收集",
        description = "手动触发应用监控数据收集"
    )
    @PostMapping("/collect")
    @PreAuthorize("hasAuthority('system:monitor:app:collect')")
    public CommonResult<Boolean> collectAppMetrics() {
        log.info("触发应用监控数据收集");
        try {
            appMonitorService.collectAppMetrics();
            return success(true);
        } catch (Exception e) {
            log.error("触发应用监控数据收集失败", e);
            return success(false);
        }
    }
}