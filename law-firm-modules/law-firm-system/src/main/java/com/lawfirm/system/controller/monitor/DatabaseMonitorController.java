package com.lawfirm.system.controller.monitor;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.model.system.entity.monitor.SysDbMonitor;
import com.lawfirm.system.constant.SystemConstants;
import com.lawfirm.system.service.impl.monitor.DbMonitorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据库监控控制器
 */
@Tag(name = "数据库监控", description = "提供数据库性能监控和管理功能")
@RestController("databaseMonitorController")
@RequestMapping(SystemConstants.API_DB_MONITOR_PREFIX)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Slf4j
public class DatabaseMonitorController extends BaseController {

    private final DbMonitorServiceImpl dbMonitorService;

    /**
     * 获取数据库监控列表
     */
    @Operation(
        summary = "获取数据库监控列表",
        description = "获取系统中所有数据库的监控数据列表"
    )
    @GetMapping
    @RequiresPermissions("system:monitor:db")
    public CommonResult<List<SysDbMonitor>> getDbList() {
        log.info("获取数据库监控列表");
        List<SysDbMonitor> dbList = dbMonitorService.getDbList();
        return success(dbList);
    }

    /**
     * 获取数据库最新监控数据
     */
    @Operation(
        summary = "获取数据库最新监控数据",
        description = "获取指定数据库的最新监控数据"
    )
    @GetMapping("/{dbName}")
    @RequiresPermissions("system:monitor:db")
    public CommonResult<SysDbMonitor> getLatestDbInfo(
            @Parameter(description = "数据库名称") @PathVariable String dbName) {
        log.info("获取数据库最新监控数据: dbName={}", dbName);
        SysDbMonitor dbInfo = dbMonitorService.getLatestDbInfo(dbName);
        return success(dbInfo);
    }

    /**
     * 获取性能较差的数据库列表
     */
    @Operation(
        summary = "获取性能较差的数据库列表",
        description = "获取系统中性能较差的数据库列表"
    )
    @GetMapping("/poor-performance")
    @RequiresPermissions("system:monitor:db")
    public CommonResult<List<String>> getPoorPerformanceDatabases() {
        log.info("获取性能较差的数据库列表");
        List<String> poorPerformanceDbs = dbMonitorService.getPoorPerformanceDatabases();
        return success(poorPerformanceDbs);
    }

    /**
     * 触发数据库监控数据收集
     */
    @Operation(
        summary = "触发数据库监控数据收集",
        description = "手动触发数据库监控数据收集"
    )
    @PostMapping("/collect")
    @RequiresPermissions("system:monitor:db:collect")
    public CommonResult<Boolean> collectDbMetrics() {
        log.info("触发数据库监控数据收集");
        try {
            dbMonitorService.collectDbMetrics();
            return success(true);
        } catch (Exception e) {
            log.error("触发数据库监控数据收集失败", e);
            return success(false);
        }
    }
} 