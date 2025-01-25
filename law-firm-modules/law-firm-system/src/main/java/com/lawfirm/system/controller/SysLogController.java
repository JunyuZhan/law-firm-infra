package com.lawfirm.system.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.model.system.entity.SysLog;
import com.lawfirm.system.service.SysLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统操作日志控制器
 */
@Tag(name = "操作日志")
@RestController
@RequestMapping("/system/log")
@RequiredArgsConstructor
public class SysLogController {

    private final SysLogService logService;

    @Operation(summary = "根据用户ID查询操作日志")
    @GetMapping("/user/{userId}")
    public R<List<SysLog>> listByUserId(@PathVariable Long userId) {
        return R.ok(logService.listByUserId(userId));
    }

    @Operation(summary = "根据模块查询操作日志")
    @GetMapping("/module/{module}")
    public R<List<SysLog>> listByModule(@PathVariable String module) {
        return R.ok(logService.listByModule(module));
    }

    @Operation(summary = "根据时间范围查询操作日志")
    @GetMapping("/time")
    public R<List<SysLog>> listByTimeRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return R.ok(logService.listByTimeRange(startTime, endTime));
    }

    @Operation(summary = "清理指定时间之前的操作日志")
    @DeleteMapping("/clean")
    public R<Void> cleanBefore(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time) {
        logService.cleanBefore(time);
        return R.ok();
    }

    @Operation(summary = "导出操作日志")
    @GetMapping("/export")
    public R<String> export(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return R.ok(logService.export(startTime, endTime));
    }
} 