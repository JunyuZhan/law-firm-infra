package com.lawfirm.admin.controller.system;

import com.lawfirm.admin.model.ApiResult;
import com.lawfirm.admin.model.response.system.monitor.OnlineUserResponse;
import com.lawfirm.admin.model.response.system.monitor.ServerInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统监控Controller
 */
@Tag(name = "系统监控")
@RestController
@RequestMapping("/monitor")
@RequiredArgsConstructor
public class SysMonitorController {

    @Operation(summary = "获取服务器信息")
    @GetMapping("/server")
    @PreAuthorize("hasAuthority('monitor:server:query')")
    public ApiResult<ServerInfoResponse> getServerInfo() {
        return ApiResult.success();
    }

    @Operation(summary = "获取在线用户列表")
    @GetMapping("/online")
    @PreAuthorize("hasAuthority('monitor:online:query')")
    public ApiResult<List<OnlineUserResponse>> getOnlineUserList(
            @RequestParam(required = false) String ipaddr,
            @RequestParam(required = false) String username) {
        return ApiResult.success();
    }

    @Operation(summary = "强退用户")
    @DeleteMapping("/online/{tokenId}")
    @PreAuthorize("hasAuthority('monitor:online:logout')")
    public ApiResult<Void> forceLogout(@PathVariable String tokenId) {
        return ApiResult.success();
    }

    @Operation(summary = "获取JVM信息")
    @GetMapping("/jvm")
    @PreAuthorize("hasAuthority('monitor:jvm:query')")
    public ApiResult<Map<String, Object>> getJvmInfo() {
        return ApiResult.success();
    }

    @Operation(summary = "获取Redis信息")
    @GetMapping("/redis")
    @PreAuthorize("hasAuthority('monitor:redis:query')")
    public ApiResult<Map<String, Object>> getRedisInfo() {
        return ApiResult.success();
    }

    @Operation(summary = "获取数据库信息")
    @GetMapping("/database")
    @PreAuthorize("hasAuthority('monitor:database:query')")
    public ApiResult<Map<String, Object>> getDatabaseInfo() {
        return ApiResult.success();
    }

    @Operation(summary = "获取缓存信息")
    @GetMapping("/cache")
    @PreAuthorize("hasAuthority('monitor:cache:query')")
    public ApiResult<Map<String, Object>> getCacheInfo() {
        return ApiResult.success();
    }

    @Operation(summary = "清理缓存")
    @DeleteMapping("/cache")
    @PreAuthorize("hasAuthority('monitor:cache:clear')")
    public ApiResult<Void> clearCache() {
        return ApiResult.success();
    }
} 
import com.lawfirm.model.base.enums.BaseEnum  
