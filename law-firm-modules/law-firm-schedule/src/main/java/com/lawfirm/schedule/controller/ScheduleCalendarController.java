package com.lawfirm.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.schedule.dto.ScheduleCalendarDTO;
import com.lawfirm.model.schedule.entity.ScheduleCalendar;
import com.lawfirm.model.schedule.entity.ScheduleEvent;
import com.lawfirm.model.schedule.service.ScheduleCalendarService;
import com.lawfirm.model.schedule.vo.ScheduleCalendarVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 日历管理控制器
 */
@Tag(name = "日历管理")
@RestController("scheduleCalendarController")
@RequestMapping("/schedule/calendar")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ScheduleCalendarController {
    
    private final ScheduleCalendarService calendarService;
    
    @Operation(summary = "创建日历")
    @PostMapping
    @PreAuthorize("hasAuthority('schedule:calendar:create')")
    public CommonResult<Long> createCalendar(@Valid @RequestBody ScheduleCalendarDTO calendarDTO) {
        log.info("创建日历：{}", calendarDTO.getName());
        Long userId = SecurityUtils.getUserId();
        ScheduleCalendar calendar = new ScheduleCalendar();
        calendar.setName(calendarDTO.getName());
        calendar.setDescription(calendarDTO.getDescription());
        calendar.setColor(calendarDTO.getColor());
        calendar.setUserId(userId);
        calendar.setType(calendarDTO.getType());
        calendar.setVisibility(calendarDTO.getVisibility());
        
        Long id = calendarService.createCalendar(calendar);
        return CommonResult.success(id, "创建日历成功");
    }
    
    @Operation(summary = "更新日历")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:calendar:update')")
    public CommonResult<Boolean> updateCalendar(
            @Parameter(description = "日历ID") @PathVariable Long id,
            @Valid @RequestBody ScheduleCalendarDTO calendarDTO) {
        log.info("更新日历：{}", id);
        ScheduleCalendar calendar = new ScheduleCalendar();
        calendar.setName(calendarDTO.getName());
        calendar.setDescription(calendarDTO.getDescription());
        calendar.setColor(calendarDTO.getColor());
        calendar.setVisibility(calendarDTO.getVisibility());
        
        boolean success = calendarService.updateCalendar(id, calendar);
        return success ? CommonResult.success(true, "更新日历成功") : CommonResult.error("更新日历失败");
    }
    
    @Operation(summary = "删除日历")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:calendar:delete')")
    public CommonResult<Boolean> deleteCalendar(@Parameter(description = "日历ID") @PathVariable Long id) {
        log.info("删除日历：{}", id);
        boolean success = calendarService.deleteCalendar(id);
        return success ? CommonResult.success(true, "删除日历成功") : CommonResult.error("删除日历失败");
    }
    
    @Operation(summary = "获取日历详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:calendar:view')")
    public CommonResult<ScheduleCalendarVO> getCalendarDetail(@Parameter(description = "日历ID") @PathVariable Long id) {
        log.info("获取日历详情：{}", id);
        ScheduleCalendarVO calendarVO = calendarService.getCalendarDetail(id);
        return CommonResult.success(calendarVO);
    }
    
    @Operation(summary = "获取用户所有日历")
    @GetMapping("/list/user/{userId}")
    @PreAuthorize("hasAuthority('schedule:calendar:view')")
    public CommonResult<List<ScheduleCalendarVO>> listByUserId(@Parameter(description = "用户ID") @PathVariable Long userId) {
        log.info("获取用户的所有日历，用户ID：{}", userId);
        List<ScheduleCalendarVO> calendars = calendarService.listByUserId(userId);
        return CommonResult.success(calendars);
    }
    
    @Operation(summary = "获取我的所有日历")
    @GetMapping("/list/my")
    @PreAuthorize("hasAuthority('schedule:calendar:view')")
    public CommonResult<List<ScheduleCalendarVO>> listMyCalendars() {
        log.info("获取我的所有日历");
        Long userId = SecurityUtils.getUserId();
        List<ScheduleCalendarVO> calendars = calendarService.listByUserId(userId);
        return CommonResult.success(calendars);
    }
    
    @Operation(summary = "设置日历显示状态")
    @PutMapping("/{id}/visibility")
    @PreAuthorize("hasAuthority('schedule:calendar:update')")
    public CommonResult<Boolean> setCalendarVisibility(
            @Parameter(description = "日历ID") @PathVariable Long id,
            @Parameter(description = "可见性") @RequestParam Integer visibility) {
        log.info("设置日历可见性，日历ID：{}，可见性：{}", id, visibility);
        boolean success = calendarService.updateCalendarVisibility(id, visibility);
        return success ? CommonResult.success(true, "设置日历可见性成功") : CommonResult.error("设置日历可见性失败");
    }
    
    @Operation(summary = "获取共享日历")
    @GetMapping("/list/shared")
    @PreAuthorize("hasAuthority('schedule:calendar:view')")
    public CommonResult<List<ScheduleCalendarVO>> listSharedCalendars() {
        log.info("获取与我共享的日历");
        Long userId = SecurityUtils.getUserId();
        List<ScheduleCalendarVO> calendars = calendarService.listSharedCalendars(userId);
        return CommonResult.success(calendars);
    }
    
    @Operation(summary = "分页查询日历")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('schedule:calendar:view')")
    public CommonResult<Page<ScheduleCalendarVO>> pageCalendars(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "用户ID") @RequestParam(required = false) Long userId,
            @Parameter(description = "类型") @RequestParam(required = false) Integer type) {
        log.info("分页查询日历，页码：{}，每页大小：{}", pageNum, pageSize);
        Page<ScheduleCalendar> page = new Page<>(pageNum, pageSize);
        Page<ScheduleCalendarVO> result = calendarService.pageCalendars(page, userId, type);
        return CommonResult.success(result);
    }
    
    @Operation(summary = "设置默认日历")
    @PutMapping("/{id}/default")
    @PreAuthorize("hasAuthority('schedule:calendar:update')")
    public CommonResult<Boolean> setDefaultCalendar(@Parameter(description = "日历ID") @PathVariable Long id) {
        log.info("设置默认日历，日历ID：{}", id);
        Long userId = SecurityUtils.getUserId();
        boolean success = calendarService.setDefaultCalendar(userId, id);
        return success ? CommonResult.success(true, "设置默认日历成功") : CommonResult.error("设置默认日历失败");
    }
    
    @Operation(summary = "获取默认日历")
    @GetMapping("/default")
    @PreAuthorize("hasAuthority('schedule:calendar:view')")
    public CommonResult<ScheduleCalendarVO> getDefaultCalendar() {
        log.info("获取默认日历");
        Long userId = SecurityUtils.getUserId();
        ScheduleCalendarVO calendar = calendarService.getDefaultCalendar(userId);
        return CommonResult.success(calendar);
    }
    
    @Operation(summary = "共享日历")
    @PostMapping("/{id}/share")
    @PreAuthorize("hasAuthority('schedule:calendar:share')")
    public CommonResult<Boolean> shareCalendar(
            @Parameter(description = "日历ID") @PathVariable Long id,
            @Parameter(description = "用户ID列表") @RequestBody List<Long> userIds) {
        log.info("共享日历，日历ID：{}，用户ID列表：{}", id, userIds);
        boolean success = calendarService.shareCalendar(id, userIds);
        return success ? CommonResult.success(true, "共享日历成功") : CommonResult.error("共享日历失败");
    }
    
    @Operation(summary = "取消共享日历")
    @DeleteMapping("/{id}/share/{userId}")
    @PreAuthorize("hasAuthority('schedule:calendar:share')")
    public CommonResult<Boolean> unshareCalendar(
            @Parameter(description = "日历ID") @PathVariable Long id,
            @Parameter(description = "用户ID") @PathVariable Long userId) {
        log.info("取消共享日历，日历ID：{}，用户ID：{}", id, userId);
        boolean success = calendarService.unshareCalendar(id, userId);
        return success ? CommonResult.success(true, "取消共享日历成功") : CommonResult.error("取消共享日历失败");
    }
} 