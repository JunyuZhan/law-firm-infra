package com.lawfirm.system.controller.monitor;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.model.system.service.MonitorService;
import com.lawfirm.model.system.vo.monitor.ServerInfoVO;
import com.lawfirm.model.system.vo.monitor.SystemInfoVO;
import com.lawfirm.model.system.vo.monitor.JvmInfoVO;
import com.lawfirm.model.system.vo.monitor.MemoryInfoVO;
import com.lawfirm.model.system.vo.monitor.CpuInfoVO;
import com.lawfirm.model.system.vo.monitor.DiskInfoVO;
import com.lawfirm.model.system.vo.monitor.NetworkInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统监控控制器
 */
@Tag(name = "系统监控", description = "提供系统监控指标和相关接口")
@RestController("monitorController")
@RequestMapping("/api/system/monitor")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class MonitorController extends BaseController {

    private final MonitorService monitorService;

    /**
     * 获取服务器信息
     */
    @Operation(
        summary = "获取服务器信息",
        description = "获取服务器的基本信息，包括主机名、操作系统、IP地址等"
    )
    @GetMapping("/server")
    @RequiresPermissions("system:monitor:server")
    public CommonResult<ServerInfoVO> getServerInfo() {
        ServerInfoVO serverInfo = monitorService.getServerInfo();
        return success(serverInfo);
    }

    /**
     * 获取系统信息
     */
    @Operation(
        summary = "获取系统信息",
        description = "获取系统运行信息，包括系统负载、运行时间、进程数等"
    )
    @GetMapping("/system")
    @RequiresPermissions("system:monitor:system")
    public CommonResult<SystemInfoVO> getSystemInfo() {
        SystemInfoVO systemInfo = monitorService.getSystemInfo();
        return success(systemInfo);
    }

    /**
     * 获取JVM信息
     */
    @Operation(
        summary = "获取JVM信息",
        description = "获取Java虚拟机运行信息，包括内存使用、垃圾回收、线程等"
    )
    @GetMapping("/jvm")
    @RequiresPermissions("system:monitor:jvm")
    public CommonResult<JvmInfoVO> getJvmInfo() {
        JvmInfoVO jvmInfo = monitorService.getJvmInfo();
        return success(jvmInfo);
    }

    /**
     * 获取内存信息
     */
    @Operation(
        summary = "获取内存信息",
        description = "获取系统内存使用情况，包括物理内存和交换内存的使用率"
    )
    @GetMapping("/memory")
    @RequiresPermissions("system:monitor:memory")
    public CommonResult<MemoryInfoVO> getMemoryInfo() {
        MemoryInfoVO memoryInfo = monitorService.getMemoryInfo();
        return success(memoryInfo);
    }

    /**
     * 获取CPU信息
     */
    @Operation(
        summary = "获取CPU信息",
        description = "获取CPU使用情况，包括使用率、核心数、频率等信息"
    )
    @GetMapping("/cpu")
    @RequiresPermissions("system:monitor:cpu")
    public CommonResult<CpuInfoVO> getCpuInfo() {
        CpuInfoVO cpuInfo = monitorService.getCpuInfo();
        return success(cpuInfo);
    }

    /**
     * 获取磁盘信息
     */
    @Operation(
        summary = "获取磁盘信息",
        description = "获取磁盘使用情况，包括各分区的总容量、已用空间、剩余空间等"
    )
    @GetMapping("/disk")
    @RequiresPermissions("system:monitor:disk")
    public CommonResult<DiskInfoVO> getDiskInfo() {
        DiskInfoVO diskInfo = monitorService.getDiskInfo();
        return success(diskInfo);
    }

    /**
     * 获取网络信息
     */
    @Operation(
        summary = "获取网络信息",
        description = "获取网络接口信息，包括网卡流量、连接状态等"
    )
    @GetMapping("/network")
    @RequiresPermissions("system:monitor:network")
    public CommonResult<NetworkInfoVO> getNetworkInfo() {
        NetworkInfoVO networkInfo = monitorService.getNetworkInfo();
        return success(networkInfo);
    }
} 