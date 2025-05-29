package com.lawfirm.schedule.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.schedule.dto.ScheduleDTO;
import com.lawfirm.model.schedule.entity.Schedule;
import com.lawfirm.model.schedule.service.ScheduleConflictService;
import com.lawfirm.model.schedule.service.ScheduleService;
import com.lawfirm.model.schedule.vo.ScheduleVO;
import com.lawfirm.schedule.constant.ScheduleConstants;
import com.lawfirm.schedule.service.ScheduleAIManager;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.lawfirm.model.auth.constant.PermissionConstants.*;

/**
 * 日程管理控制器
 */
@Tag(name = "日程管理")
@RestController("scheduleController")
@RequestMapping(ScheduleConstants.API_PREFIX)
@RequiredArgsConstructor
@Validated
@Slf4j
public class ScheduleController {
    
    private final ScheduleService scheduleService;
    private final ScheduleConflictService conflictService;
    private final ScheduleAIManager scheduleAIManager;
    
    @Operation(summary = "创建日程")
    @PostMapping
    @PreAuthorize("hasAuthority('" + SCHEDULE_CREATE + "')")
    public CommonResult<Long> createSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) {
        log.info("创建日程：{}", scheduleDTO.getTitle());
        Long id = scheduleService.createSchedule(scheduleDTO);
        return CommonResult.success(id, "创建日程成功");
    }
    
    @Operation(summary = "更新日程")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('" + SCHEDULE_EDIT + "')")
    public CommonResult<Boolean> updateSchedule(
            @Parameter(description = "日程ID") @PathVariable Long id,
            @Valid @RequestBody ScheduleDTO scheduleDTO) {
        log.info("更新日程：{}", id);
        boolean success = scheduleService.updateSchedule(id, scheduleDTO);
        return success ? CommonResult.success(true, "更新日程成功") : CommonResult.error("更新日程失败");
    }
    
    @Operation(summary = "获取日程详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + SCHEDULE_VIEW + "')")
    public CommonResult<ScheduleVO> getScheduleDetail(@Parameter(description = "日程ID") @PathVariable Long id) {
        log.info("获取日程详情：{}", id);
        ScheduleVO scheduleVO = scheduleService.getScheduleDetail(id);
        return CommonResult.success(scheduleVO);
    }
    
    @Operation(summary = "删除日程")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + SCHEDULE_DELETE + "')")
    public CommonResult<Boolean> deleteSchedule(@Parameter(description = "日程ID") @PathVariable Long id) {
        log.info("删除日程：{}", id);
        boolean success = scheduleService.deleteSchedule(id);
        return success ? CommonResult.success(true, "删除日程成功") : CommonResult.error("删除日程失败");
    }
    
    @Operation(summary = "批量删除日程")
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('" + SCHEDULE_DELETE + "')")
    public CommonResult<Boolean> batchDeleteSchedules(@RequestBody List<Long> ids) {
        log.info("批量删除日程，数量：{}", ids.size());
        boolean success = true;
        for (Long id : ids) {
            success = success && scheduleService.deleteSchedule(id);
        }
        return success ? CommonResult.success(true, "批量删除日程成功") : CommonResult.error("批量删除日程失败");
    }
    
    @Operation(summary = "获取用户的日程列表")
    @GetMapping("/list/user/{userId}")
    @PreAuthorize("hasAuthority('" + SCHEDULE_VIEW + "')")
    public CommonResult<List<ScheduleVO>> listUserSchedules(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @Parameter(description = "日程类型") @RequestParam(required = false) Integer type) {
        log.info("获取用户日程列表，用户ID：{}，时间范围：{} - {}", userId, startTime, endTime);
        List<ScheduleVO> schedules = scheduleService.listByTimeRange(userId, startTime, endTime);
        return CommonResult.success(schedules);
    }
    
    @Operation(summary = "分页查询日程")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('" + SCHEDULE_VIEW + "')")
    public CommonResult<Page<ScheduleVO>> pageSchedules(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "用户ID") @RequestParam(required = false) Long userId,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status) {
        log.info("分页查询日程，页码：{}，每页大小：{}", pageNum, pageSize);
        Page<Schedule> page = new Page<>(pageNum, pageSize);
        Long currentUserId = userId != null ? userId : SecurityUtils.getUserId();
        Page<ScheduleVO> result = scheduleService.pageByUser(page, currentUserId, status);
        return CommonResult.success(result);
    }
    
    @Operation(summary = "获取我的日程列表")
    @GetMapping("/list/my")
    @PreAuthorize("hasAuthority('" + SCHEDULE_VIEW + "')")
    public CommonResult<List<ScheduleVO>> listMySchedules(
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @Parameter(description = "日程类型") @RequestParam(required = false) Integer type) {
        log.info("获取我的日程列表，时间范围：{} - {}", startTime, endTime);
        Long userId = SecurityUtils.getUserId();
        List<ScheduleVO> schedules = scheduleService.listByTimeRange(userId, startTime, endTime);
        return CommonResult.success(schedules);
    }
    
    @Operation(summary = "检查日程时间冲突")
    @PostMapping("/check-conflict")
    @PreAuthorize("hasAuthority('" + SCHEDULE_CREATE + "')")
    public CommonResult<Boolean> checkScheduleConflict(@Valid @RequestBody ScheduleDTO scheduleDTO) {
        log.info("检查日程时间冲突：{}", scheduleDTO.getTitle());
        boolean hasConflict = conflictService.checkScheduleConflict(scheduleDTO);
        return CommonResult.success(!hasConflict, hasConflict ? "存在时间冲突" : "无时间冲突");
    }
    
    @Operation(summary = "复制日程")
    @PostMapping("/{id}/copy")
    @PreAuthorize("hasAuthority('" + SCHEDULE_CREATE + "')")
    public CommonResult<Long> copySchedule(
            @Parameter(description = "日程ID") @PathVariable Long id,
            @Parameter(description = "是否复制参与者") @RequestParam(defaultValue = "true") boolean withParticipants,
            @Parameter(description = "是否复制提醒") @RequestParam(defaultValue = "true") boolean withReminders) {
        log.info("复制日程：{}", id);
        // 获取原日程详情
        ScheduleVO originalSchedule = scheduleService.getScheduleDetail(id);
        if (originalSchedule == null) {
            return CommonResult.error("找不到原日程");
        }
        
        // 创建新的日程DTO
        ScheduleDTO newScheduleDTO = new ScheduleDTO();
        newScheduleDTO.setTitle("复制: " + originalSchedule.getTitle());
        newScheduleDTO.setContent(originalSchedule.getContent());
        newScheduleDTO.setStartTime(originalSchedule.getStartTime());
        newScheduleDTO.setEndTime(originalSchedule.getEndTime());
        newScheduleDTO.setAllDay(originalSchedule.getAllDay());
        newScheduleDTO.setLocation(originalSchedule.getLocation());
        newScheduleDTO.setType(originalSchedule.getType());
        newScheduleDTO.setPriority(originalSchedule.getPriority());
        
        // 创建新日程
        Long newId = scheduleService.createSchedule(newScheduleDTO);
        return CommonResult.success(newId, "复制日程成功");
    }
    
    @Operation(summary = "更新日程状态")
    @PutMapping("/{id}/status/{status}")
    @PreAuthorize("hasAuthority('" + SCHEDULE_EDIT + "')")
    public CommonResult<Boolean> changeScheduleStatus(
            @Parameter(description = "日程ID") @PathVariable Long id,
            @Parameter(description = "状态") @PathVariable Integer status) {
        log.info("更新日程状态，ID：{}，状态：{}", id, status);
        boolean success = scheduleService.updateStatus(id, status);
        return success ? CommonResult.success(true, "更新状态成功") : CommonResult.error("更新状态失败");
    }
    
    @Operation(summary = "获取共享日程列表")
    @GetMapping("/list/shared")
    @PreAuthorize("hasAuthority('" + SCHEDULE_VIEW + "')")
    public CommonResult<List<ScheduleVO>> listSharedSchedules(
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        log.info("获取共享日程列表，时间范围：{} - {}", startTime, endTime);
        // 获取有权限查看的所有用户ID
        List<Long> userIds = List.of(SecurityUtils.getUserId()); // 简化处理，实际应该查询有权限的用户
        
        // 合并所有用户的日程
        List<ScheduleVO> schedules = scheduleService.listByTimeRange(userIds.get(0), startTime, endTime);
        return CommonResult.success(schedules);
    }
    
    /**
     * AI日程智能摘要
     */
    @PostMapping("/ai/summary")
    public org.springframework.http.ResponseEntity<String> aiScheduleSummary(@RequestBody java.util.Map<String, Object> body) {
        String content = (String) body.get("content");
        Integer maxLength = body.get("maxLength") != null ? (Integer) body.get("maxLength") : 200;
        return org.springframework.http.ResponseEntity.ok(scheduleAIManager.summarize(content, maxLength));
    }
    
    /**
     * AI日程冲突检测与优化建议
     */
    @PostMapping("/ai/conflict")
    public org.springframework.http.ResponseEntity<java.util.Map<String, Object>> aiScheduleConflict(@RequestBody java.util.Map<String, Object> body) {
        @SuppressWarnings("unchecked")
        java.util.List<java.util.Map<String, Object>> schedules = (java.util.List<java.util.Map<String, Object>>) body.get("schedules");
        return org.springframework.http.ResponseEntity.ok(scheduleAIManager.detectConflict(schedules));
    }
    
    /**
     * AI日程智能分类
     */
    @PostMapping("/ai/classify")
    public org.springframework.http.ResponseEntity<java.util.Map<String, Double>> aiScheduleClassify(@RequestBody java.util.Map<String, Object> body) {
        String content = (String) body.get("content");
        return org.springframework.http.ResponseEntity.ok(scheduleAIManager.classify(content));
    }
    
    /**
     * AI日程标签推荐
     */
    @PostMapping("/ai/tags")
    public org.springframework.http.ResponseEntity<java.util.List<String>> aiScheduleTags(@RequestBody java.util.Map<String, Object> body) {
        String content = (String) body.get("content");
        Integer limit = body.get("limit") != null ? (Integer) body.get("limit") : 5;
        return org.springframework.http.ResponseEntity.ok(scheduleAIManager.recommendTags(content, limit));
    }
    
    /**
     * AI日程推荐
     */
    @PostMapping("/ai/recommend")
    public org.springframework.http.ResponseEntity<java.util.List<java.util.Map<String, Object>>> aiScheduleRecommend(@RequestBody java.util.Map<String, Object> body) {
        String content = (String) body.get("content");
        Integer limit = body.get("limit") != null ? (Integer) body.get("limit") : 5;
        return org.springframework.http.ResponseEntity.ok(scheduleAIManager.recommendSchedules(content, limit));
    }
    
    /**
     * AI日程智能问答
     */
    @PostMapping("/ai/qa")
    public org.springframework.http.ResponseEntity<String> aiScheduleQA(@RequestBody java.util.Map<String, Object> body) {
        String question = (String) body.get("question");
        String context = (String) body.get("content");
        return org.springframework.http.ResponseEntity.ok(scheduleAIManager.qa(question, context));
    }
    
    /**
     * AI日程自动生成
     */
    @PostMapping("/ai/generate")
    public org.springframework.http.ResponseEntity<String> aiScheduleGenerate(@RequestBody java.util.Map<String, Object> body) {
        return org.springframework.http.ResponseEntity.ok(scheduleAIManager.generate(body));
    }
} 