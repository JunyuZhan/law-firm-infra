package com.lawfirm.system.controller.monitor;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.model.system.entity.monitor.SysServerMonitor;
import com.lawfirm.system.constant.SystemConstants;
import com.lawfirm.system.service.impl.monitor.ServerMonitorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务器监控控制器
 */
@Tag(name = "服务器监控", description = "提供服务器性能监控和管理功能")
@RestController("serverMonitorController")
@RequestMapping(SystemConstants.API_MONITOR_PREFIX + "/servers")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Slf4j
public class ServerMonitorController extends BaseController {

    private final ServerMonitorServiceImpl serverMonitorService;

    /**
     * 获取服务器监控列表
     */
    @Operation(
        summary = "获取服务器监控列表",
        description = "获取所有服务器的监控数据列表"
    )
    @GetMapping
    @RequiresPermissions("system:monitor:server")
    public CommonResult<List<SysServerMonitor>> getServerList() {
        log.info("获取服务器监控列表");
        List<SysServerMonitor> serverList = serverMonitorService.getServerList();
        return success(serverList);
    }

    /**
     * 获取服务器最新监控数据
     */
    @Operation(
        summary = "获取服务器最新监控数据",
        description = "获取指定服务器的最新监控数据"
    )
    @GetMapping("/{serverIp}")
    @RequiresPermissions("system:monitor:server")
    public CommonResult<SysServerMonitor> getLatestServerInfo(
            @Parameter(description = "服务器IP") @PathVariable String serverIp) {
        log.info("获取服务器最新监控数据: serverIp={}", serverIp);
        SysServerMonitor serverInfo = serverMonitorService.getLatestServerInfo(serverIp);
        return success(serverInfo);
    }

    /**
     * 触发服务器监控数据收集
     */
    @Operation(
        summary = "触发服务器监控数据收集",
        description = "手动触发服务器监控数据收集"
    )
    @PostMapping("/collect")
    @RequiresPermissions("system:monitor:server:collect")
    public CommonResult<Boolean> collectServerMetrics() {
        log.info("触发服务器监控数据收集");
        try {
            serverMonitorService.collectServerMetrics();
            return success(true);
        } catch (Exception e) {
            log.error("触发服务器监控数据收集失败", e);
            return success(false);
        }
    }
} 