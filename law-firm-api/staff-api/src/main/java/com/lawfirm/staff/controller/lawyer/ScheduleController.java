package com.lawfirm.staff.controller.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.annotation.OperationLog;
import com.lawfirm.staff.annotation.OperationType;
import com.lawfirm.staff.client.ScheduleClient;
import com.lawfirm.schedule.model.dto.ScheduleDTO;
import com.lawfirm.schedule.model.query.ScheduleQuery;
import com.lawfirm.schedule.model.vo.ScheduleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "律师-日程管理")
@RestController
@RequestMapping("/staff/lawyer/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleClient scheduleClient;

    @Operation(summary = "分页查询日程")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('lawyer:schedule:query')")
    @OperationLog(value = "分页查询日程", type = OperationType.QUERY)
    public Result<PageResult<ScheduleVO>> page(ScheduleQuery query) {
        return scheduleClient.page(query);
    }

    @Operation(summary = "创建日程")
    @PostMapping
    @PreAuthorize("hasAuthority('lawyer:schedule:add')")
    @OperationLog(value = "创建日程", type = OperationType.CREATE)
    public Result<Void> create(@RequestBody @Validated ScheduleDTO scheduleDTO) {
        return scheduleClient.add(scheduleDTO);
    }

    @Operation(summary = "修改日程")
    @PutMapping
    @PreAuthorize("hasAuthority('lawyer:schedule:update')")
    @OperationLog(value = "修改日程", type = OperationType.UPDATE)
    public Result<Void> update(@RequestBody @Validated ScheduleDTO scheduleDTO) {
        return scheduleClient.update(scheduleDTO);
    }

    @Operation(summary = "删除日程")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('lawyer:schedule:delete')")
    @OperationLog(value = "删除日程", type = OperationType.DELETE)
    public Result<Void> delete(@PathVariable Long id) {
        return scheduleClient.delete(id);
    }

    @Operation(summary = "获取日程详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('lawyer:schedule:query')")
    @OperationLog(value = "获取日程详情", type = OperationType.QUERY)
    public Result<ScheduleVO> get(@PathVariable Long id) {
        return scheduleClient.get(id);
    }

    @Operation(summary = "获取我的日程")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('lawyer:schedule:query')")
    @OperationLog(value = "获取我的日程", type = OperationType.QUERY)
    public Result<PageResult<ScheduleVO>> getMySchedules(ScheduleQuery query) {
        return scheduleClient.getMySchedules(query);
    }

    @Operation(summary = "导出日程")
    @GetMapping("/export")
    @PreAuthorize("hasAuthority('lawyer:schedule:export')")
    @OperationLog(value = "导出日程", type = OperationType.EXPORT)
    public void export(ScheduleQuery query) {
        scheduleClient.export(query);
    }
} 