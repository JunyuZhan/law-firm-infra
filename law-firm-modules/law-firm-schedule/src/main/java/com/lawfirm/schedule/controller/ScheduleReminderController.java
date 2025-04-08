package com.lawfirm.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.schedule.dto.ScheduleReminderDTO;
import com.lawfirm.model.schedule.entity.ScheduleReminder;
import com.lawfirm.model.schedule.service.ScheduleReminderService;
import com.lawfirm.model.schedule.vo.ScheduleReminderVO;
import com.lawfirm.schedule.constant.ScheduleConstants;
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
import java.util.stream.Collectors;

/**
 * 日程提醒控制器
 */
@Tag(name = "日程提醒")
@RestController("scheduleReminderController")
@RequestMapping(ScheduleConstants.API_REMINDER_PREFIX)
@RequiredArgsConstructor
@Validated
@Slf4j
public class ScheduleReminderController {
    
    private final ScheduleReminderService reminderService;
    
    @Operation(summary = "创建提醒")
    @PostMapping
    @PreAuthorize("hasAuthority('schedule:reminder:create')")
    public CommonResult<Long> createReminder(@Valid @RequestBody ScheduleReminderDTO reminderDTO) {
        log.info("创建日程提醒，日程ID：{}", reminderDTO.getScheduleId());
        ScheduleReminder reminder = new ScheduleReminder();
        reminder.setScheduleId(reminderDTO.getScheduleId());
        reminder.setUserId(SecurityUtils.getUserId());
        reminder.setReminderTime(reminderDTO.getReminderTime());
        reminder.setReminderType(reminderDTO.getReminderType().ordinal());
        reminder.setContent(reminderDTO.getContent());
        
        Long id = reminderService.addReminder(reminderDTO.getScheduleId(), reminderDTO);
        return CommonResult.success(id, "创建提醒成功");
    }
    
    @Operation(summary = "更新提醒")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:reminder:update')")
    public CommonResult<Boolean> updateReminder(
            @Parameter(description = "提醒ID") @PathVariable Long id,
            @Valid @RequestBody ScheduleReminderDTO reminderDTO) {
        log.info("更新日程提醒：{}", id);
        boolean success = reminderService.updateReminder(id, reminderDTO);
        return success ? CommonResult.success(true, "更新提醒成功") : CommonResult.error("更新提醒失败");
    }
    
    @Operation(summary = "删除提醒")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:reminder:delete')")
    public CommonResult<Boolean> deleteReminder(@Parameter(description = "提醒ID") @PathVariable Long id) {
        log.info("删除日程提醒：{}", id);
        boolean success = reminderService.removeReminder(id);
        return success ? CommonResult.success(true, "删除提醒成功") : CommonResult.error("删除提醒失败");
    }
    
    @Operation(summary = "获取提醒详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:reminder:view')")
    public CommonResult<ScheduleReminderVO> getReminderDetail(@Parameter(description = "提醒ID") @PathVariable Long id) {
        log.info("获取日程提醒详情：{}", id);
        ScheduleReminderVO reminderVO = reminderService.getReminderDetail(id);
        return CommonResult.success(reminderVO);
    }
    
    @Operation(summary = "获取日程的所有提醒")
    @GetMapping("/list/schedule/{scheduleId}")
    @PreAuthorize("hasAuthority('schedule:reminder:view')")
    public CommonResult<List<ScheduleReminderVO>> listByScheduleId(@Parameter(description = "日程ID") @PathVariable Long scheduleId) {
        log.info("获取日程的所有提醒，日程ID：{}", scheduleId);
        List<ScheduleReminderVO> reminders = reminderService.listByScheduleId(scheduleId);
        return CommonResult.success(reminders);
    }
    
    @Operation(summary = "获取用户的所有提醒")
    @GetMapping("/list/user/{userId}")
    @PreAuthorize("hasAuthority('schedule:reminder:view')")
    public CommonResult<List<ScheduleReminderVO>> listByUserId(@Parameter(description = "用户ID") @PathVariable Long userId) {
        log.info("获取用户的所有提醒，用户ID：{}", userId);
        // 使用查询条件获取用户的所有提醒
        QueryWrapper<ScheduleReminder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ScheduleReminder::getUserId, userId);
        List<ScheduleReminder> reminders = reminderService.list(queryWrapper);
        List<ScheduleReminderVO> reminderVOs = reminders.stream()
                .map(reminder -> {
                    ScheduleReminderVO vo = new ScheduleReminderVO();
                    // 设置VO属性
                    vo.setId(reminder.getId());
                    vo.setScheduleId(reminder.getScheduleId());
                    vo.setReminderTime(reminder.getReminderTime());
                    vo.setContent(reminder.getContent());
                    vo.setUserId(reminder.getUserId());
                    // 其他属性设置...
                    return vo;
                })
                .collect(Collectors.toList());
        return CommonResult.success(reminderVOs);
    }
    
    @Operation(summary = "获取我的所有提醒")
    @GetMapping("/list/my")
    @PreAuthorize("hasAuthority('schedule:reminder:view')")
    public CommonResult<List<ScheduleReminderVO>> listMyReminders() {
        log.info("获取我的所有提醒");
        Long userId = SecurityUtils.getUserId();
        // 使用查询条件获取用户的所有提醒
        QueryWrapper<ScheduleReminder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ScheduleReminder::getUserId, userId);
        List<ScheduleReminder> reminders = reminderService.list(queryWrapper);
        List<ScheduleReminderVO> reminderVOs = reminders.stream()
                .map(reminder -> {
                    ScheduleReminderVO vo = new ScheduleReminderVO();
                    // 设置VO属性
                    vo.setId(reminder.getId());
                    vo.setScheduleId(reminder.getScheduleId());
                    vo.setReminderTime(reminder.getReminderTime());
                    vo.setContent(reminder.getContent());
                    vo.setUserId(reminder.getUserId());
                    // 其他属性设置...
                    return vo;
                })
                .collect(Collectors.toList());
        return CommonResult.success(reminderVOs);
    }
    
    @Operation(summary = "分页查询提醒")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('schedule:reminder:view')")
    public CommonResult<Page<ScheduleReminderVO>> pageReminders(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "日程ID") @RequestParam(required = false) Long scheduleId,
            @Parameter(description = "用户ID") @RequestParam(required = false) Long userId,
            @Parameter(description = "提醒类型") @RequestParam(required = false) Integer remindType,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        log.info("分页查询提醒，页码：{}，每页大小：{}", pageNum, pageSize);
        Page<ScheduleReminder> page = new Page<>(pageNum, pageSize);
        // 构建查询条件
        QueryWrapper<ScheduleReminder> queryWrapper = new QueryWrapper<>();
        if (scheduleId != null) {
            queryWrapper.lambda().eq(ScheduleReminder::getScheduleId, scheduleId);
        }
        if (userId != null) {
            queryWrapper.lambda().eq(ScheduleReminder::getUserId, userId);
        }
        if (remindType != null) {
            queryWrapper.lambda().eq(ScheduleReminder::getReminderType, remindType);
        }
        if (startTime != null) {
            queryWrapper.lambda().ge(ScheduleReminder::getReminderTime, startTime);
        }
        if (endTime != null) {
            queryWrapper.lambda().le(ScheduleReminder::getReminderTime, endTime);
        }
        
        Page<ScheduleReminder> result = reminderService.page(page, queryWrapper);
        // 转换为VO
        Page<ScheduleReminderVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<ScheduleReminderVO> voList = result.getRecords().stream()
                .map(reminder -> {
                    ScheduleReminderVO vo = new ScheduleReminderVO();
                    // 设置VO属性
                    vo.setId(reminder.getId());
                    vo.setScheduleId(reminder.getScheduleId());
                    vo.setReminderTime(reminder.getReminderTime());
                    vo.setContent(reminder.getContent());
                    vo.setUserId(reminder.getUserId());
                    // 其他属性设置...
                    return vo;
                })
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        return CommonResult.success(voPage);
    }
    
    @Operation(summary = "标记提醒为已处理")
    @PutMapping("/{id}/processed")
    @PreAuthorize("hasAuthority('schedule:reminder:update')")
    public CommonResult<Boolean> markAsProcessed(@Parameter(description = "提醒ID") @PathVariable Long id) {
        log.info("标记提醒为已处理：{}", id);
        boolean success = reminderService.markAsReminded(id);  // 使用已有的方法
        return success ? CommonResult.success(true, "标记成功") : CommonResult.error("标记失败");
    }
    
    @Operation(summary = "获取待处理提醒")
    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('schedule:reminder:view')")
    public CommonResult<List<ScheduleReminderVO>> getPendingReminders(
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        log.info("获取待处理提醒");
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }
        if (endTime == null) {
            endTime = startTime.plusDays(7);  // 默认查询一周内的提醒
        }
        
        List<ScheduleReminderVO> reminders = reminderService.getPendingReminders(startTime, endTime);
        return CommonResult.success(reminders);
    }
} 