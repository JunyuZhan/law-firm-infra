package com.lawfirm.cases.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseEventDTO;
import com.lawfirm.model.cases.service.business.CaseEventService;
import com.lawfirm.model.cases.vo.business.CaseEventVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件事件管理控制器实现
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/cases/events")
@RequiredArgsConstructor
@Tag(name = "案件事件管理", description = "案件事件管理相关接口")
public class EventControllerImpl {

    private final CaseEventService eventService;

    @PostMapping
    @Operation(summary = "创建事件")
    public Long createEvent(@RequestBody @Validated CaseEventDTO eventDTO) {
        log.info("创建事件: {}", eventDTO);
        return eventService.createEvent(eventDTO);
    }

    @PostMapping("/batch")
    @Operation(summary = "批量创建事件")
    public boolean batchCreateEvents(@RequestBody @Validated List<CaseEventDTO> eventDTOs) {
        log.info("批量创建事件: {}", eventDTOs);
        return eventService.batchCreateEvents(eventDTOs);
    }

    @PutMapping
    @Operation(summary = "更新事件")
    public boolean updateEvent(@RequestBody @Validated CaseEventDTO eventDTO) {
        log.info("更新事件: {}", eventDTO);
        return eventService.updateEvent(eventDTO);
    }

    @DeleteMapping("/{eventId}")
    @Operation(summary = "删除事件")
    public boolean deleteEvent(@PathVariable("eventId") Long eventId) {
        log.info("删除事件: {}", eventId);
        return eventService.deleteEvent(eventId);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除事件")
    public boolean batchDeleteEvents(@RequestBody List<Long> eventIds) {
        log.info("批量删除事件: {}", eventIds);
        return eventService.batchDeleteEvents(eventIds);
    }

    @GetMapping("/{eventId}")
    @Operation(summary = "获取事件详情")
    public CaseEventVO getEventDetail(@PathVariable("eventId") Long eventId) {
        log.info("获取事件详情: {}", eventId);
        return eventService.getEventDetail(eventId);
    }

    @GetMapping("/cases/{caseId}")
    @Operation(summary = "获取案件的所有事件")
    public List<CaseEventVO> listCaseEvents(@PathVariable("caseId") Long caseId) {
        log.info("获取案件的所有事件: caseId={}", caseId);
        return eventService.listCaseEvents(caseId);
    }

    @GetMapping("/cases/{caseId}/page")
    @Operation(summary = "分页查询事件")
    public IPage<CaseEventVO> pageEvents(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "事件类型") @RequestParam(required = false) Integer eventType,
            @Parameter(description = "事件状态") @RequestParam(required = false) Integer eventStatus,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询事件: caseId={}, eventType={}, eventStatus={}, pageNum={}, pageSize={}", 
                caseId, eventType, eventStatus, pageNum, pageSize);
        return eventService.pageEvents(caseId, eventType, eventStatus, pageNum, pageSize);
    }

    @PostMapping("/{eventId}/start")
    @Operation(summary = "开始事件")
    public boolean startEvent(@PathVariable("eventId") Long eventId) {
        log.info("开始事件: {}", eventId);
        return eventService.startEvent(eventId);
    }

    @PostMapping("/{eventId}/complete")
    @Operation(summary = "完成事件")
    public boolean completeEvent(
            @PathVariable("eventId") Long eventId,
            @Parameter(description = "完成说明") @RequestParam(required = false) String completionNote) {
        log.info("完成事件: eventId={}, completionNote={}", eventId, completionNote);
        return eventService.completeEvent(eventId, completionNote);
    }

    @PostMapping("/{eventId}/cancel")
    @Operation(summary = "取消事件")
    public boolean cancelEvent(
            @PathVariable("eventId") Long eventId,
            @Parameter(description = "取消原因") @RequestParam String reason) {
        log.info("取消事件: eventId={}, reason={}", eventId, reason);
        return eventService.cancelEvent(eventId, reason);
    }

    @PostMapping("/{eventId}/participants")
    @Operation(summary = "添加事件参与人")
    public boolean addParticipant(
            @PathVariable("eventId") Long eventId,
            @Parameter(description = "参与人ID") @RequestParam Long participantId) {
        log.info("添加事件参与人: eventId={}, participantId={}", eventId, participantId);
        return eventService.addParticipant(eventId, participantId);
    }

    @DeleteMapping("/{eventId}/participants/{participantId}")
    @Operation(summary = "移除事件参与人")
    public boolean removeParticipant(
            @PathVariable("eventId") Long eventId,
            @PathVariable("participantId") Long participantId) {
        log.info("移除事件参与人: eventId={}, participantId={}", eventId, participantId);
        return eventService.removeParticipant(eventId, participantId);
    }

    @PostMapping("/{eventId}/attachments")
    @Operation(summary = "添加事件附件")
    public boolean addAttachment(
            @PathVariable("eventId") Long eventId,
            @Parameter(description = "文件ID") @RequestParam Long fileId) {
        log.info("添加事件附件: eventId={}, fileId={}", eventId, fileId);
        return eventService.addAttachment(eventId, fileId);
    }

    @DeleteMapping("/{eventId}/attachments/{fileId}")
    @Operation(summary = "移除事件附件")
    public boolean removeAttachment(
            @PathVariable("eventId") Long eventId,
            @PathVariable("fileId") Long fileId) {
        log.info("移除事件附件: eventId={}, fileId={}", eventId, fileId);
        return eventService.removeAttachment(eventId, fileId);
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "获取用户的事件列表")
    public List<CaseEventVO> listUserEvents(
            @PathVariable("userId") Long userId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取用户的事件列表: userId={}, startTime={}, endTime={}", userId, startTime, endTime);
        return eventService.listUserEvents(userId, startTime, endTime);
    }

    @GetMapping("/users/{userId}/todo")
    @Operation(summary = "获取用户的待办事件")
    public List<CaseEventVO> listUserTodoEvents(@PathVariable("userId") Long userId) {
        log.info("获取用户的待办事件: userId={}", userId);
        return eventService.listUserTodoEvents(userId);
    }

    @GetMapping("/cases/{caseId}/timeline")
    @Operation(summary = "获取案件时间线")
    public List<CaseEventVO> getCaseTimeline(@PathVariable("caseId") Long caseId) {
        log.info("获取案件时间线: caseId={}", caseId);
        return eventService.getCaseTimeline(caseId);
    }

    @GetMapping("/{eventId}/exists")
    @Operation(summary = "检查事件是否存在")
    public boolean checkEventExists(@PathVariable("eventId") Long eventId) {
        log.info("检查事件是否存在: {}", eventId);
        return eventService.checkEventExists(eventId);
    }

    @GetMapping("/cases/{caseId}/count")
    @Operation(summary = "统计案件事件数量")
    public int countEvents(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "事件类型") @RequestParam(required = false) Integer eventType,
            @Parameter(description = "事件状态") @RequestParam(required = false) Integer eventStatus) {
        log.info("统计案件事件数量: caseId={}, eventType={}, eventStatus={}", caseId, eventType, eventStatus);
        return eventService.countEvents(caseId, eventType, eventStatus);
    }
}
