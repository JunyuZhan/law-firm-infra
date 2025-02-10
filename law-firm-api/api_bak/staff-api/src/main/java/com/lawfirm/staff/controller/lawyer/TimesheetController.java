package com.lawfirm.staff.controller.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.staff.model.request.lawyer.CreateTimesheetRequest;
import com.lawfirm.staff.model.request.lawyer.TimesheetPageRequest;
import com.lawfirm.staff.model.request.lawyer.UpdateTimesheetRequest;
import com.lawfirm.staff.model.response.lawyer.TimesheetResponse;
import com.lawfirm.staff.service.lawyer.TimesheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "工时管理")
@RestController
@RequestMapping("/lawyer/timesheet")
@RequiredArgsConstructor
public class TimesheetController {

    private final TimesheetService timesheetService;

    @Operation(summary = "分页查询工时")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('lawyer:timesheet:query')")
    public Result<PageResult<TimesheetResponse>> page(TimesheetPageRequest request) {
        return Result.ok(timesheetService.page(request));
    }

    @Operation(summary = "添加工时")
    @PostMapping
    @PreAuthorize("hasAuthority('lawyer:timesheet:add')")
    @OperationLog(description = "添加工时")
    public Result<Void> add(@RequestBody @Validated CreateTimesheetRequest request) {
        timesheetService.add(request);
        return Result.ok();
    }

    @Operation(summary = "修改工时")
    @PutMapping
    @PreAuthorize("hasAuthority('lawyer:timesheet:update')")
    @OperationLog(description = "修改工时")
    public Result<Void> update(@RequestBody @Validated UpdateTimesheetRequest request) {
        timesheetService.update(request);
        return Result.ok();
    }

    @Operation(summary = "删除工时")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('lawyer:timesheet:delete')")
    @OperationLog(description = "删除工时")
    public Result<Void> delete(@PathVariable Long id) {
        timesheetService.delete(id);
        return Result.ok();
    }

    @Operation(summary = "获取工时详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('lawyer:timesheet:query')")
    public Result<TimesheetResponse> get(@PathVariable Long id) {
        return Result.ok(timesheetService.get(id));
    }

    @Operation(summary = "获取我的工时")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('lawyer:timesheet:query')")
    public Result<List<TimesheetResponse>> getMyTimesheets() {
        return Result.ok(timesheetService.getMyTimesheets());
    }

    @Operation(summary = "获取部门工时")
    @GetMapping("/department")
    @PreAuthorize("hasAuthority('lawyer:timesheet:query')")
    public Result<List<TimesheetResponse>> getDepartmentTimesheets() {
        return Result.ok(timesheetService.getDepartmentTimesheets());
    }

    @Operation(summary = "获取团队工时")
    @GetMapping("/team")
    @PreAuthorize("hasAuthority('lawyer:timesheet:query')")
    public Result<List<TimesheetResponse>> getTeamTimesheets(TimesheetPageRequest request) {
        return Result.ok(timesheetService.getTeamTimesheets(request));
    }
} 