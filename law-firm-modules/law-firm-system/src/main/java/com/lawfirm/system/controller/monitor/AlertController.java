package com.lawfirm.system.controller.monitor;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.model.system.dto.monitor.MonitorAlertDTO;
import com.lawfirm.model.system.dto.monitor.MonitorQueryDTO;
import com.lawfirm.model.system.service.AlertService;
import com.lawfirm.model.system.service.MonitorService;
import com.lawfirm.system.constant.SystemConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 系统告警控制器
 */
@Tag(name = "系统告警", description = "提供系统告警查询和管理功能")
@RestController("alertController")
@RequestMapping(SystemConstants.API_ALERT_PREFIX)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Slf4j
public class AlertController extends BaseController {

    @Qualifier("systemAlertServiceImpl")
    private final AlertService alertService;
    
    @Qualifier("systemMonitorServiceImpl")
    private final MonitorService monitorService;

    /**
     * 获取告警列表
     */
    @Operation(
        summary = "获取告警列表",
        description = "根据条件查询系统告警列表"
    )
    @GetMapping
    @RequiresPermissions("system:alert:query")
    public CommonResult<List<MonitorAlertDTO>> getAlerts(
            @Valid MonitorQueryDTO queryDTO) {
        log.info("获取告警列表: queryDTO={}", queryDTO);
        List<MonitorAlertDTO> alerts = monitorService.getAlerts(queryDTO);
        return success(alerts);
    }

    /**
     * 发送告警
     */
    @Operation(
        summary = "发送告警",
        description = "发送系统告警信息"
    )
    @PostMapping
    @RequiresPermissions("system:alert:create")
    @Log(title = "发送告警")
    public CommonResult<String> sendAlert(
            @Parameter(description = "告警类型") @RequestParam String type,
            @Parameter(description = "告警级别") @RequestParam String level,
            @Parameter(description = "告警消息") @RequestParam String message) {
        log.info("发送告警: type={}, level={}, message={}", type, level, message);
        String alertId = alertService.sendAlert(type, level, message);
        return success(alertId);
    }

    /**
     * 发送数据库告警
     */
    @Operation(
        summary = "发送数据库告警",
        description = "发送数据库相关的告警信息"
    )
    @PostMapping("/database")
    @RequiresPermissions("system:alert:create")
    @Log(title = "发送数据库告警")
    public CommonResult<String> sendDbAlert(
            @Parameter(description = "数据库名称") @RequestParam String dbName,
            @Parameter(description = "告警级别") @RequestParam String level,
            @Parameter(description = "告警消息") @RequestParam String message) {
        log.info("发送数据库告警: dbName={}, level={}, message={}", dbName, level, message);
        String alertId = alertService.sendDbAlert(dbName, level, message);
        return success(alertId);
    }

    /**
     * 发送服务器告警
     */
    @Operation(
        summary = "发送服务器告警",
        description = "发送服务器相关的告警信息"
    )
    @PostMapping("/server")
    @RequiresPermissions("system:alert:create")
    @Log(title = "发送服务器告警")
    public CommonResult<String> sendServerAlert(
            @Parameter(description = "服务器名称") @RequestParam String serverName,
            @Parameter(description = "告警级别") @RequestParam String level,
            @Parameter(description = "告警消息") @RequestParam String message) {
        log.info("发送服务器告警: serverName={}, level={}, message={}", serverName, level, message);
        String alertId = alertService.sendServerAlert(serverName, level, message);
        return success(alertId);
    }

    /**
     * 处理告警
     */
    @Operation(
        summary = "处理告警",
        description = "处理指定的系统告警"
    )
    @PutMapping("/{alertId}/handle")
    @RequiresPermissions("system:alert:handle")
    @Log(title = "处理告警")
    public CommonResult<Boolean> handleAlert(
            @Parameter(description = "告警ID") @PathVariable String alertId,
            @Parameter(description = "处理人") @RequestParam String handler,
            @Parameter(description = "处理结果") @RequestParam String result) {
        log.info("处理告警: alertId={}, handler={}, result={}", alertId, handler, result);
        boolean success = monitorService.handleAlert(alertId, handler, result);
        return success(success);
    }

    /**
     * 关闭告警
     */
    @Operation(
        summary = "关闭告警",
        description = "关闭指定的系统告警"
    )
    @PutMapping("/{alertId}/close")
    @RequiresPermissions("system:alert:close")
    @Log(title = "关闭告警")
    public CommonResult<Boolean> closeAlert(
            @Parameter(description = "告警ID") @PathVariable String alertId) {
        log.info("关闭告警: alertId={}", alertId);
        boolean success = alertService.closeAlert(alertId);
        return success(success);
    }
} 