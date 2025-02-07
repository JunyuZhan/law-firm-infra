package com.lawfirm.mobile.controller.schedule;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.schedule.model.dto.ScheduleDTO;
import com.lawfirm.schedule.model.query.ScheduleQuery;
import com.lawfirm.schedule.model.vo.ScheduleVO;
import com.lawfirm.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 移动端-日程管理
 */
@Tag(name = "移动端-日程管理")
@RestController
@RequestMapping("/mobile/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "创建日程")
    @PostMapping
    @PreAuthorize("hasAuthority('mobile:schedule:add')")
    @OperationLog("创建日程")
    public Result<Void> add(@RequestBody @Validated ScheduleDTO dto) {
        scheduleService.add(dto);
        return Result.ok();
    }

    @Operation(summary = "更新日程")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('mobile:schedule:update')")
    @OperationLog("更新日程")
    public Result<Void> update(@PathVariable Long id, @RequestBody @Validated ScheduleDTO dto) {
        dto.setId(id);
        scheduleService.update(dto);
        return Result.ok();
    }

    @Operation(summary = "删除日程")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('mobile:schedule:delete')")
    @OperationLog("删除日程")
    public Result<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return Result.ok();
    }

    @Operation(summary = "获取日程详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('mobile:schedule:query')")
    public Result<ScheduleVO> get(@PathVariable Long id) {
        return Result.ok(scheduleService.get(id));
    }

    @Operation(summary = "获取我的日程")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('mobile:schedule:query')")
    public Result<List<ScheduleVO>> getMySchedules() {
        return Result.ok(scheduleService.getMySchedules());
    }

    @Operation(summary = "获取日程提醒")
    @GetMapping("/reminders")
    @PreAuthorize("hasAuthority('mobile:schedule:query')")
    public Result<List<ScheduleVO>> getReminders() {
        return Result.ok(scheduleService.getReminders());
    }

    @Operation(summary = "获取庭审日程")
    @GetMapping("/court")
    @PreAuthorize("hasAuthority('mobile:schedule:query')")
    public Result<List<ScheduleVO>> getCourtSchedules(ScheduleQuery query) {
        query.setType("COURT");
        return Result.ok(scheduleService.list(query));
    }

    @Operation(summary = "获取会见日程")
    @GetMapping("/meeting")
    @PreAuthorize("hasAuthority('mobile:schedule:query')")
    public Result<List<ScheduleVO>> getMeetingSchedules(ScheduleQuery query) {
        query.setType("MEETING");
        return Result.ok(scheduleService.list(query));
    }

    @Operation(summary = "获取日程统计")
    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('mobile:schedule:query')")
    public Result<Map<String, Object>> getStats() {
        return Result.ok(scheduleService.getStats());
    }
} 