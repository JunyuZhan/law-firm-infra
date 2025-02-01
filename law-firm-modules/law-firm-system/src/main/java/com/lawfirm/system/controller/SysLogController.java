package com.lawfirm.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.domain.R;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.model.system.entity.SysLog;
import com.lawfirm.system.model.dto.SysLogDTO;
import com.lawfirm.system.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统操作日志控制器
 */
@Api(tags = "系统日志")
@RestController
@RequestMapping("/system/log")
@RequiredArgsConstructor
public class SysLogController {

    private final SysLogService logService;

    @ApiOperation("分页查询")
    @GetMapping("/page")
    public R<PageResult<SysLogDTO>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            SysLog log) {
        Page<SysLog> page = new Page<>(current, size);
        return R.ok(logService.page(page, new QueryWrapper<>(log)));
    }

    @ApiOperation("查询列表")
    @GetMapping("/list")
    public R<List<SysLogDTO>> list(SysLog log) {
        return R.ok(logService.list(new QueryWrapper<>(log)));
    }

    @ApiOperation("根据ID查询")
    @GetMapping("/{id}")
    public R<SysLogDTO> getById(@PathVariable Long id) {
        return R.ok(logService.findById(id));
    }

    @ApiOperation("清理日志")
    @DeleteMapping("/clean")
    public R<Void> clean(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime before) {
        logService.cleanLogs(before);
        return R.ok();
    }

    @ApiOperation("根据用户ID查询操作日志")
    @GetMapping("/user/{userId}")
    public R<List<SysLog>> listByUserId(@PathVariable Long userId) {
        return R.ok(logService.listByUserId(userId));
    }

    @ApiOperation("根据模块查询操作日志")
    @GetMapping("/module/{module}")
    public R<List<SysLog>> listByModule(@PathVariable String module) {
        return R.ok(logService.listByModule(module));
    }

    @ApiOperation("根据时间范围查询操作日志")
    @GetMapping("/time")
    public R<List<SysLog>> listByTimeRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return R.ok(logService.listByTimeRange(startTime, endTime));
    }

    @ApiOperation("导出操作日志")
    @GetMapping("/export")
    public R<Void> export(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        logService.exportLogs(startTime, endTime);
        return R.ok();
    }
} 