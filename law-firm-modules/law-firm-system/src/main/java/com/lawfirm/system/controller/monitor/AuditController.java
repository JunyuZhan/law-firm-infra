package com.lawfirm.system.controller.monitor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.log.annotation.Log;

import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.dto.AuditRecordDTO;
import com.lawfirm.model.log.dto.AuditLogQuery;
import com.lawfirm.model.log.service.AuditQueryService;
import com.lawfirm.model.log.service.AuditService;
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
 * 审计管理控制器
 */
@Tag(name = "审计管理", description = "提供系统审计记录查询和管理功能")
@RestController("auditController")
@RequestMapping(SystemConstants.API_MONITOR_PREFIX + "/audits")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Slf4j
public class AuditController extends BaseController {

    @Qualifier("auditQueryServiceImpl")
    private final AuditQueryService auditQueryService;
    
    @Qualifier("auditServiceImpl")
    private final AuditService auditService;

    /**
     * 分页查询审计日志
     */
    @Operation(
        summary = "分页查询审计日志",
        description = "根据条件分页查询系统审计日志"
    )
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('system:audit:query')")
    public CommonResult<Page<AuditLogDTO>> page(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页记录数") @RequestParam(defaultValue = "10") Integer size,
            @Valid AuditLogQuery query) {
        log.info("分页查询审计日志: current={}, size={}, query={}", current, size, query);
        query.setPageNum(current);
        query.setPageSize(size);
        Page<AuditLogDTO> page = auditQueryService.queryAuditLogs(query);
        return success(page);
    }

    /**
     * 获取审计日志详情
     */
    @Operation(
        summary = "获取审计日志详情",
        description = "根据审计日志ID获取详细信息"
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:audit:query')")
    public CommonResult<AuditLogDTO> getById(
            @Parameter(description = "审计日志ID") @PathVariable Long id) {
        log.info("获取审计日志详情: id={}", id);
        AuditLogDTO auditLogDTO = auditQueryService.getAuditLog(id);
        return success(auditLogDTO);
    }

    /**
     * 查询指定目标的审计记录
     */
    @Operation(
        summary = "查询指定目标的审计记录",
        description = "查询特定业务对象的审计记录"
    )
    @GetMapping("/records")
    @PreAuthorize("hasAuthority('system:audit:query')")
    public CommonResult<List<AuditRecordDTO>> getAuditRecords(
            @Parameter(description = "目标ID") @RequestParam Long targetId,
            @Parameter(description = "目标类型") @RequestParam String targetType) {
        log.info("查询指定目标的审计记录: targetId={}, targetType={}", targetId, targetType);
        List<AuditRecordDTO> records = auditQueryService.queryAuditRecords(targetId, targetType);
        return success(records);
    }

    /**
     * 获取审计记录详情
     */
    @Operation(
        summary = "获取审计记录详情",
        description = "根据审计记录ID获取详细信息"
    )
    @GetMapping("/records/{id}")
    @PreAuthorize("hasAuthority('system:audit:query')")
    public CommonResult<AuditRecordDTO> getRecordById(
            @Parameter(description = "审计记录ID") @PathVariable Long id) {
        log.info("获取审计记录详情: id={}", id);
        AuditRecordDTO recordDTO = auditQueryService.getAuditRecord(id);
        return success(recordDTO);
    }

    /**
     * 导出审计日志
     */
    @Operation(
        summary = "导出审计日志",
        description = "根据条件导出系统审计日志"
    )
    @PostMapping("/export")
    @PreAuthorize("hasAuthority('system:audit:export')")
    @Log(title = "导出审计日志")
    public CommonResult<String> exportAuditLogs(@Valid @RequestBody AuditLogQuery query) {
        log.info("导出审计日志: query={}", query);
        // 注意：这里需要审计服务提供导出功能，如果没有可以扩展实现
        // String filePath = auditService.exportAuditLogs(query);
        // 临时返回结果，实际实现应当调用相应的导出服务
        return success("审计日志导出功能待实现");
    }
}