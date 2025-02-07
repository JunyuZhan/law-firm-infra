package com.lawfirm.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.domain.R;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.domain.OperationLogDO;
import com.lawfirm.system.service.SysLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import com.lawfirm.common.web.controller.BaseController;

/**
 * 系统日志控制器
 */
@Tag(name = "系统日志管理")
@RestController
@RequestMapping("/system/log")
@RequiredArgsConstructor
public class SysLogController extends BaseController {

    private final SysLogService logService;

    @Operation(summary = "分页查询系统日志")
    @GetMapping("/page")
    public R<Page<OperationLogDO>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            OperationLogDO log) {
        Page<OperationLogDO> page = new Page<>(current, size);
        return R.ok(logService.page(page, new QueryWrapper<>(log)));
    }

    @Operation(summary = "查询系统日志详情")
    @GetMapping("/{id}")
    public R<OperationLogDO> getById(@PathVariable Long id) {
        return R.ok(logService.getById(id));
    }

    @Operation(summary = "导出系统日志")
    @GetMapping("/export")
    public R<Void> export() {
        logService.export();
        return R.ok();
    }

    @Operation(summary = "清空系统日志")
    @DeleteMapping("/clean")
    public R<Void> clean() {
        logService.clean();
        return R.ok();
    }

    @Operation(summary = "根据用户ID查询操作日志")
    @GetMapping("/user/{userId}")
    public R<List<OperationLogDO>> listByUserId(@PathVariable Long userId) {
        return R.ok(logService.listByUserId(userId));
    }

    @Operation(summary = "根据模块查询操作日志")
    @GetMapping("/module/{module}")
    public R<List<OperationLogDO>> listByModule(@PathVariable String module) {
        return R.ok(logService.listByModule(module));
    }

    @Operation(summary = "根据时间范围查询操作日志")
    @GetMapping("/time")
    public R<List<OperationLogDO>> listByTimeRange(
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        return R.ok(logService.listByTimeRange(startTime, endTime));
    }

    @Operation(summary = "删除系统日志")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        logService.delete(id);
        return R.ok();
    }

    /**
     * 创建日志
     */
    @PostMapping
    public R<Void> create(@RequestBody OperationLogDO log) {
        logService.createLog(log);
        return R.ok();
    }

    /**
     * 更新日志
     */
    @PutMapping
    public R<Void> update(@RequestBody OperationLogDO log) {
        logService.updateLog(log);
        return R.ok();
    }
} 