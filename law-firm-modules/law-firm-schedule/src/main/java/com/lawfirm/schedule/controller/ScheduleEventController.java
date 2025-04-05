package com.lawfirm.schedule.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.schedule.dto.ScheduleEventDTO;
import com.lawfirm.model.schedule.entity.ScheduleEvent;
import com.lawfirm.model.schedule.service.ScheduleEventService;
import com.lawfirm.model.schedule.vo.ScheduleEventVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 日程事件控制器
 */
@Tag(name = "日程事件")
@RestController("scheduleEventController")
@RequestMapping("/schedule/event")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ScheduleEventController {
    
    private final ScheduleEventService eventService;
    
    @Operation(summary = "创建事件")
    @PostMapping
    @PreAuthorize("hasAuthority('schedule:event:create')")
    public CommonResult<Long> createEvent(@Valid @RequestBody ScheduleEventDTO eventDTO) {
        log.info("创建日程事件，日程ID：{}", eventDTO.getScheduleId());
        ScheduleEvent event = new ScheduleEvent();
        event.setScheduleId(eventDTO.getScheduleId());
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setEventType(eventDTO.getEventType());
        event.setStartTime(eventDTO.getStartTime());
        event.setEndTime(eventDTO.getEndTime());
        event.setLocation(eventDTO.getLocation());
        event.setCreatedBy(SecurityUtils.getUserId());
        
        Long id = eventService.createEvent(event);
        return CommonResult.success(id, "创建事件成功");
    }
    
    @Operation(summary = "更新事件")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:event:update')")
    public CommonResult<Boolean> updateEvent(
            @Parameter(description = "事件ID") @PathVariable Long id,
            @Valid @RequestBody ScheduleEventDTO eventDTO) {
        log.info("更新日程事件：{}", id);
        ScheduleEvent event = new ScheduleEvent();
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setEventType(eventDTO.getEventType());
        event.setStartTime(eventDTO.getStartTime());
        event.setEndTime(eventDTO.getEndTime());
        event.setLocation(eventDTO.getLocation());
        
        boolean success = eventService.updateEvent(id, event);
        return success ? CommonResult.success(true, "更新事件成功") : CommonResult.error("更新事件失败");
    }
    
    @Operation(summary = "删除事件")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:event:delete')")
    public CommonResult<Boolean> deleteEvent(@Parameter(description = "事件ID") @PathVariable Long id) {
        log.info("删除日程事件：{}", id);
        boolean success = eventService.deleteEvent(id);
        return success ? CommonResult.success(true, "删除事件成功") : CommonResult.error("删除事件失败");
    }
    
    @Operation(summary = "获取事件详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:event:view')")
    public CommonResult<ScheduleEventVO> getEventDetail(@Parameter(description = "事件ID") @PathVariable Long id) {
        log.info("获取日程事件详情：{}", id);
        ScheduleEventVO eventVO = eventService.getEventDetail(id);
        return CommonResult.success(eventVO);
    }
    
    @Operation(summary = "获取日程的所有事件")
    @GetMapping("/list/schedule/{scheduleId}")
    @PreAuthorize("hasAuthority('schedule:event:view')")
    public CommonResult<List<ScheduleEventVO>> listByScheduleId(@Parameter(description = "日程ID") @PathVariable Long scheduleId) {
        log.info("获取日程的所有事件，日程ID：{}", scheduleId);
        List<ScheduleEventVO> events = eventService.listByScheduleId(scheduleId);
        return CommonResult.success(events);
    }
    
    @Operation(summary = "获取时间范围内的事件")
    @GetMapping("/list/time-range")
    @PreAuthorize("hasAuthority('schedule:event:view')")
    public CommonResult<List<ScheduleEventVO>> listByTimeRange(
            @Parameter(description = "日程ID") @RequestParam Long scheduleId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        log.info("获取时间范围内的事件，日程ID：{}，时间范围：{} - {}", scheduleId, startTime, endTime);
        List<ScheduleEventVO> events = eventService.listByTimeRange(scheduleId, startTime, endTime);
        return CommonResult.success(events);
    }
    
    @Operation(summary = "分页查询事件")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('schedule:event:view')")
    public CommonResult<Page<ScheduleEventVO>> pageEvents(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "日程ID") @RequestParam(required = false) Long scheduleId,
            @Parameter(description = "事件类型") @RequestParam(required = false) Integer eventType,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        log.info("分页查询事件，页码：{}，每页大小：{}", pageNum, pageSize);
        Page<ScheduleEvent> page = new Page<>(pageNum, pageSize);
        Page<ScheduleEventVO> result = eventService.pageEvents(page, scheduleId, eventType, startTime, endTime);
        return CommonResult.success(result);
    }
    
    @Operation(summary = "移动事件")
    @PutMapping("/{id}/move")
    @PreAuthorize("hasAuthority('schedule:event:update')")
    public CommonResult<Boolean> moveEvent(
            @Parameter(description = "事件ID") @PathVariable Long id,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        log.info("移动事件，事件ID：{}，新的时间范围：{} - {}", id, startTime, endTime);
        boolean success = eventService.moveEvent(id, startTime, endTime);
        return success ? CommonResult.success(true, "移动事件成功") : CommonResult.error("移动事件失败");
    }
    
    @Operation(summary = "获取我的所有事件")
    @GetMapping("/list/my")
    @PreAuthorize("hasAuthority('schedule:event:view')")
    public CommonResult<List<ScheduleEventVO>> listMyEvents(
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        log.info("获取我的所有事件，时间范围：{} - {}", startTime, endTime);
        Long userId = SecurityUtils.getUserId();
        List<ScheduleEventVO> events = eventService.listByUser(userId, startTime, endTime);
        return CommonResult.success(events);
    }
    
    @Operation(summary = "检查事件冲突")
    @GetMapping("/check-conflict")
    @PreAuthorize("hasAuthority('schedule:event:view')")
    public CommonResult<Boolean> checkEventConflict(
            @Parameter(description = "日程ID") @RequestParam Long scheduleId,
            @Parameter(description = "事件ID") @RequestParam(required = false) Long eventId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        log.info("检查事件冲突，日程ID：{}，时间范围：{} - {}", scheduleId, startTime, endTime);
        boolean conflict = eventService.checkConflict(scheduleId, eventId, startTime, endTime);
        return CommonResult.success(!conflict, conflict ? "存在冲突" : "无冲突");
    }
} 