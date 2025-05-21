package com.lawfirm.system.controller.monitor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.model.log.dto.BaseLogDTO;
import com.lawfirm.model.log.dto.LogExportDTO;
import com.lawfirm.model.log.dto.LogQueryDTO;
import com.lawfirm.model.log.service.LogAnalysisService;
import com.lawfirm.model.log.service.LogService;
import com.lawfirm.model.log.vo.LogStatVO;
import com.lawfirm.system.constant.SystemConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 日志管理控制器
 */
@Tag(name = "日志管理", description = "提供系统日志查询、统计和管理功能")
@RestController("logController")
@RequestMapping(SystemConstants.API_MONITOR_PREFIX + "/logs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Slf4j
public class LogController extends BaseController {

    private final LogService<BaseLogDTO> logService;
    private final LogAnalysisService logAnalysisService;

    /**
     * 分页查询日志
     */
    @Operation(
        summary = "分页查询日志",
        description = "根据条件分页查询系统日志"
    )
    @GetMapping("/page")
    @RequiresPermissions("system:log:query")
    public CommonResult<Page<BaseLogDTO>> page(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页记录数") @RequestParam(defaultValue = "10") Integer size,
            @Valid LogQueryDTO queryDTO) {
        log.info("分页查询日志: current={}, size={}, queryDTO={}", current, size, queryDTO);
        Page<BaseLogDTO> page = logService.page(new Page<>(current, size), queryDTO);
        return success(page);
    }

    /**
     * 获取日志详情
     */
    @Operation(
        summary = "获取日志详情",
        description = "根据日志ID获取详细信息"
    )
    @GetMapping("/{id}")
    @RequiresPermissions("system:log:query")
    public CommonResult<BaseLogDTO> getById(
            @Parameter(description = "日志ID") @PathVariable Long id) {
        log.info("获取日志详情: id={}", id);
        BaseLogDTO logDTO = logService.getById(id);
        return success(logDTO);
    }

    /**
     * 获取日志统计信息
     */
    @Operation(
        summary = "获取日志统计信息",
        description = "获取系统日志的统计数据，如总数、类型分布等"
    )
    @GetMapping("/stats")
    @RequiresPermissions("system:log:stats")
    public CommonResult<LogStatVO> getLogStats(@Valid LogQueryDTO queryDTO) {
        log.info("获取日志统计信息: queryDTO={}", queryDTO);
        LogStatVO statVO = logAnalysisService.getLogStats(queryDTO);
        return success(statVO);
    }

    /**
     * 获取日志趋势
     */
    @Operation(
        summary = "获取日志趋势",
        description = "获取指定天数内的日志趋势数据"
    )
    @GetMapping("/trend")
    @RequiresPermissions("system:log:stats")
    public CommonResult<LogStatVO> getLogTrend(
            @Parameter(description = "统计天数") @RequestParam(defaultValue = "7") Integer days) {
        log.info("获取日志趋势: days={}", days);
        LogStatVO trendVO = logAnalysisService.getLogTrend(days);
        return success(trendVO);
    }

    /**
     * 获取异常日志分析
     */
    @Operation(
        summary = "获取异常日志分析",
        description = "获取系统异常日志的分析数据"
    )
    @GetMapping("/error/analysis")
    @RequiresPermissions("system:log:stats")
    public CommonResult<LogStatVO> getErrorLogAnalysis(@Valid LogQueryDTO queryDTO) {
        log.info("获取异常日志分析: queryDTO={}", queryDTO);
        LogStatVO analysisVO = logAnalysisService.getErrorLogAnalysis(queryDTO);
        return success(analysisVO);
    }

    /**
     * 获取用户操作分析
     */
    @Operation(
        summary = "获取用户操作分析",
        description = "获取用户操作行为的分析数据"
    )
    @GetMapping("/user/analysis")
    @RequiresPermissions("system:log:stats")
    public CommonResult<LogStatVO> getUserOperationAnalysis(@Valid LogQueryDTO queryDTO) {
        log.info("获取用户操作分析: queryDTO={}", queryDTO);
        LogStatVO analysisVO = logAnalysisService.getUserOperationAnalysis(queryDTO);
        return success(analysisVO);
    }

    /**
     * 导出日志
     */
    @Operation(
        summary = "导出日志",
        description = "根据条件导出系统日志"
    )
    @PostMapping("/export")
    @RequiresPermissions("system:log:export")
    @Log(title = "导出日志")
    public CommonResult<String> exportLogs(@Valid @RequestBody LogExportDTO exportDTO) {
        log.info("导出日志: exportDTO={}", exportDTO);
        String filePath = logService.exportLogs(exportDTO);
        return success(filePath);
    }

    /**
     * 清理过期日志
     */
    @Operation(
        summary = "清理过期日志",
        description = "清理指定天数之前的系统日志"
    )
    @DeleteMapping("/clean")
    @RequiresPermissions("system:log:clean")
    @Log(title = "清理过期日志")
    public CommonResult<Integer> cleanExpiredLogs(
            @Parameter(description = "保留天数") @RequestParam(defaultValue = "30") Integer days) {
        log.info("清理过期日志: days={}", days);
        Integer count = logService.cleanExpiredLogs(days);
        return success(count);
    }
} 