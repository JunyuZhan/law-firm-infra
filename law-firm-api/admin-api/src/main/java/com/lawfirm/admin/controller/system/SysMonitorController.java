package com.lawfirm.admin.controller.system;

import com.lawfirm.common.core.model.ApiResult;
import com.lawfirm.system.model.vo.ServerInfoVO;
import com.lawfirm.system.model.vo.OnlineUserVO;
import com.lawfirm.system.service.SysMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统监控Controller
 */
@Api(tags = "系统监控")
@RestController
@RequestMapping("/system/monitor")
@RequiredArgsConstructor
public class SysMonitorController {

    private final SysMonitorService monitorService;

    @ApiOperation("获取服务器信息")
    @GetMapping("/server")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<ServerInfoVO> getServerInfo() {
        return ApiResult.ok(monitorService.getServerInfo());
    }

    @ApiOperation("获取在线用户")
    @GetMapping("/online")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<OnlineUserVO>> getOnlineUsers() {
        return ApiResult.ok(monitorService.getOnlineUsers());
    }

    @ApiOperation("强制下线用户")
    @DeleteMapping("/online/{token}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> forceLogout(@PathVariable String token) {
        monitorService.forceLogout(token);
        return ApiResult.ok();
    }

    @ApiOperation("获取JVM信息")
    @GetMapping("/jvm")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Map<String, Object>> getJvmInfo() {
        return ApiResult.ok(monitorService.getJvmInfo());
    }

    @ApiOperation("获取Redis信息")
    @GetMapping("/redis")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Map<String, Object>> getRedisInfo() {
        return ApiResult.ok(monitorService.getRedisInfo());
    }

    @ApiOperation("获取数据库信息")
    @GetMapping("/database")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Map<String, Object>> getDatabaseInfo() {
        return ApiResult.ok(monitorService.getDatabaseInfo());
    }

    @ApiOperation("获取缓存信息")
    @GetMapping("/cache")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Map<String, Object>> getCacheInfo() {
        return ApiResult.ok(monitorService.getCacheInfo());
    }

    @ApiOperation("清理缓存")
    @DeleteMapping("/cache")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> clearCache() {
        monitorService.clearCache();
        return ApiResult.ok();
    }
} 