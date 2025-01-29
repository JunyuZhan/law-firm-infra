package com.lawfirm.admin.controller.system;

import com.lawfirm.common.core.model.ApiResult;
import com.lawfirm.system.model.vo.SysLogVO;
import com.lawfirm.system.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统日志Controller
 */
@Api(tags = "系统日志")
@RestController
@RequestMapping("/system/log")
@RequiredArgsConstructor
public class SysLogController {

    private final SysLogService logService;

    @ApiOperation("删除日志")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> deleteLog(@PathVariable Long id) {
        logService.deleteLog(id);
        return ApiResult.ok();
    }

    @ApiOperation("批量删除日志")
    @DeleteMapping("/batch")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> batchDeleteLogs(@RequestBody List<Long> ids) {
        logService.batchDeleteLogs(ids);
        return ApiResult.ok();
    }

    @ApiOperation("清空日志")
    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> clearLogs() {
        logService.clearLogs();
        return ApiResult.ok();
    }

    @ApiOperation("获取日志详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<SysLogVO> getLog(@PathVariable Long id) {
        return ApiResult.ok(logService.getLog(id));
    }

    @ApiOperation("分页查询日志")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<SysLogVO>> pageLogs(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(required = false) String username,
                                             @RequestParam(required = false) String operation,
                                             @RequestParam(required = false) String startTime,
                                             @RequestParam(required = false) String endTime) {
        return ApiResult.ok(logService.pageLogs(pageNum, pageSize, username, operation, startTime, endTime));
    }

    @ApiOperation("导出日志")
    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportLogs() {
        logService.exportLogs();
    }
} 