package com.lawfirm.cases.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.cases.constant.CaseBusinessConstants;
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
import static com.lawfirm.model.auth.constant.PermissionConstants.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件事件控制器
 */
@Slf4j
@RestController("eventController")
@RequestMapping(CaseBusinessConstants.Controller.API_EVENT_PREFIX)
@RequiredArgsConstructor
@Tag(name = "案件事件管理", description = "提供案件事件管理功能，包括事件的创建、更新、查询等操作")
public class EventController {

    private final CaseEventService eventService;

    @Operation(
        summary = "创建事件",
        description = "创建新的案件事件，包括事件基本信息、关联案件、参与人等"
    )
    @PostMapping
    @PreAuthorize(SCHEDULE_EDIT)
    public Long createEvent(
            @Parameter(description = "事件信息，包括事件标题、内容、类型、开始时间、结束时间等") 
            @RequestBody @Validated CaseEventDTO eventDTO) {
        log.info("创建事件: {}", eventDTO);
        return eventService.createEvent(eventDTO);
    }

    @Operation(
        summary = "批量创建事件",
        description = "批量创建多个案件事件，支持同时创建多个相关联的事件"
    )
    @PostMapping("/batch")
    @PreAuthorize(SCHEDULE_EDIT)
    public boolean batchCreateEvents(
            @Parameter(description = "事件信息列表，每个事件包括标题、内容、类型等") 
            @RequestBody @Validated List<CaseEventDTO> eventDTOs) {
        log.info("批量创建事件: {}", eventDTOs);
        return eventService.batchCreateEvents(eventDTOs);
    }

    @Operation(
        summary = "更新事件",
        description = "更新已有事件的信息，包括事件内容、时间、参与人等"
    )
    @PutMapping
    @PreAuthorize(SCHEDULE_EDIT)
    public boolean updateEvent(
            @Parameter(description = "更新的事件信息，包括可修改的事件相关字段") 
            @RequestBody @Validated CaseEventDTO eventDTO) {
        log.info("更新事件: {}", eventDTO);
        return eventService.updateEvent(eventDTO);
    }

    @Operation(
        summary = "删除事件",
        description = "删除指定的事件，如果事件已开始或完成则不允许删除"
    )
    @DeleteMapping("/{eventId}")
    @PreAuthorize(SCHEDULE_EDIT)
    public boolean deleteEvent(
            @Parameter(description = "事件ID") 
            @PathVariable("eventId") Long eventId) {
        log.info("删除事件: {}", eventId);
        return eventService.deleteEvent(eventId);
    }

    @Operation(
        summary = "批量删除事件",
        description = "批量删除多个事件，如果任一事件已开始或完成则不允许删除"
    )
    @DeleteMapping("/batch")
    @PreAuthorize(SCHEDULE_EDIT)
    public boolean batchDeleteEvents(
            @Parameter(description = "事件ID列表") 
            @RequestBody List<Long> eventIds) {
        log.info("批量删除事件: {}", eventIds);
        return eventService.batchDeleteEvents(eventIds);
    }

    @Operation(
        summary = "获取事件详情",
        description = "获取事件的详细信息，包括基本信息、参与人、附件等"
    )
    @GetMapping("/{eventId}")
    @PreAuthorize(SCHEDULE_VIEW)
    public CaseEventVO getEventDetail(
            @Parameter(description = "事件ID") 
            @PathVariable("eventId") Long eventId) {
        log.info("获取事件详情: {}", eventId);
        return eventService.getEventDetail(eventId);
    }

    @Operation(
        summary = "获取案件的所有事件",
        description = "获取指定案件关联的所有事件列表"
    )
    @GetMapping("/cases/{caseId}")
    @PreAuthorize(SCHEDULE_VIEW)
    public List<CaseEventVO> listCaseEvents(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId) {
        log.info("获取案件的所有事件: caseId={}", caseId);
        return eventService.listCaseEvents(caseId);
    }

    @Operation(
        summary = "分页查询事件",
        description = "分页查询案件的事件列表，支持按事件类型和状态筛选"
    )
    @GetMapping("/cases/{caseId}/page")
    @PreAuthorize(SCHEDULE_VIEW)
    public IPage<CaseEventVO> pageEvents(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "事件类型：1-开庭，2-谈判，3-调解，4-会见当事人，5-证据交换") 
            @RequestParam(required = false) Integer eventType,
            @Parameter(description = "事件状态：1-未开始，2-进行中，3-已完成，4-已取消") 
            @RequestParam(required = false) Integer eventStatus,
            @Parameter(description = "页码，从1开始") 
            @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页显示记录数") 
            @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询事件: caseId={}, eventType={}, eventStatus={}, pageNum={}, pageSize={}", 
                caseId, eventType, eventStatus, pageNum, pageSize);
        return eventService.pageEvents(caseId, eventType, eventStatus, pageNum, pageSize);
    }

    @Operation(
        summary = "开始事件",
        description = "将事件状态更新为进行中，记录实际开始时间"
    )
    @PostMapping("/{eventId}/start")
    @PreAuthorize(SCHEDULE_EDIT)
    public boolean startEvent(
            @Parameter(description = "事件ID") 
            @PathVariable("eventId") Long eventId) {
        log.info("开始事件: {}", eventId);
        return eventService.startEvent(eventId);
    }

    @Operation(
        summary = "完成事件",
        description = "将事件标记为已完成，可以添加完成说明"
    )
    @PostMapping("/{eventId}/complete")
    @PreAuthorize(SCHEDULE_EDIT)
    public boolean completeEvent(
            @Parameter(description = "事件ID") 
            @PathVariable("eventId") Long eventId,
            @Parameter(description = "完成说明，记录事件完成的具体情况") 
            @RequestParam(required = false) String completionNote) {
        log.info("完成事件: eventId={}, completionNote={}", eventId, completionNote);
        return eventService.completeEvent(eventId, completionNote);
    }

    @Operation(
        summary = "取消事件",
        description = "取消未完成的事件，需要提供取消原因"
    )
    @PostMapping("/{eventId}/cancel")
    @PreAuthorize(SCHEDULE_EDIT)
    public boolean cancelEvent(
            @Parameter(description = "事件ID") 
            @PathVariable("eventId") Long eventId,
            @Parameter(description = "取消原因，说明取消事件的具体原因") 
            @RequestParam String reason) {
        log.info("取消事件: eventId={}, reason={}", eventId, reason);
        return eventService.cancelEvent(eventId, reason);
    }

    @Operation(
        summary = "添加事件参与人",
        description = "为事件添加参与人，支持添加多个参与人"
    )
    @PostMapping("/{eventId}/participants")
    @PreAuthorize(SCHEDULE_EDIT)
    public boolean addParticipant(
            @Parameter(description = "事件ID") 
            @PathVariable("eventId") Long eventId,
            @Parameter(description = "要添加的参与人用户ID") 
            @RequestParam Long participantId) {
        log.info("添加事件参与人: eventId={}, participantId={}", eventId, participantId);
        return eventService.addParticipant(eventId, participantId);
    }

    @Operation(
        summary = "移除事件参与人",
        description = "从事件中移除指定的参与人"
    )
    @DeleteMapping("/{eventId}/participants/{participantId}")
    @PreAuthorize(SCHEDULE_EDIT)
    public boolean removeParticipant(
            @Parameter(description = "事件ID") 
            @PathVariable("eventId") Long eventId,
            @Parameter(description = "要移除的参与人用户ID") 
            @PathVariable("participantId") Long participantId) {
        log.info("移除事件参与人: eventId={}, participantId={}", eventId, participantId);
        return eventService.removeParticipant(eventId, participantId);
    }

    @Operation(
        summary = "添加事件附件",
        description = "为事件添加相关附件文件"
    )
    @PostMapping("/{eventId}/attachments")
    @PreAuthorize(SCHEDULE_EDIT)
    public boolean addAttachment(
            @Parameter(description = "事件ID") 
            @PathVariable("eventId") Long eventId,
            @Parameter(description = "要添加的附件文件ID") 
            @RequestParam Long fileId) {
        log.info("添加事件附件: eventId={}, fileId={}", eventId, fileId);
        return eventService.addAttachment(eventId, fileId);
    }

    @Operation(
        summary = "移除事件附件",
        description = "移除事件关联的指定附件"
    )
    @DeleteMapping("/{eventId}/attachments/{fileId}")
    @PreAuthorize(SCHEDULE_EDIT)
    public boolean removeAttachment(
            @Parameter(description = "事件ID") 
            @PathVariable("eventId") Long eventId,
            @Parameter(description = "要移除的附件文件ID") 
            @PathVariable("fileId") Long fileId) {
        log.info("移除事件附件: eventId={}, fileId={}", eventId, fileId);
        return eventService.removeAttachment(eventId, fileId);
    }

    @Operation(
        summary = "获取用户的事件列表",
        description = "获取指定用户在指定时间范围内参与的所有事件"
    )
    @GetMapping("/users/{userId}")
    @PreAuthorize(SCHEDULE_VIEW)
    public List<CaseEventVO> listUserEvents(
            @Parameter(description = "用户ID") 
            @PathVariable("userId") Long userId,
            @Parameter(description = "开始时间，格式：yyyy-MM-dd HH:mm:ss") 
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间，格式：yyyy-MM-dd HH:mm:ss") 
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取用户的事件列表: userId={}, startTime={}, endTime={}", userId, startTime, endTime);
        return eventService.listUserEvents(userId, startTime, endTime);
    }

    @Operation(
        summary = "获取用户的待办事件",
        description = "获取指定用户需要处理的未完成事件列表"
    )
    @GetMapping("/users/{userId}/todo")
    @PreAuthorize(SCHEDULE_VIEW)
    public List<CaseEventVO> listUserTodoEvents(
            @Parameter(description = "用户ID") 
            @PathVariable("userId") Long userId) {
        log.info("获取用户的待办事件: userId={}", userId);
        return eventService.listUserTodoEvents(userId);
    }

    @Operation(
        summary = "获取案件时间线",
        description = "按时间顺序获取案件的所有事件，用于展示案件进展时间线"
    )
    @GetMapping("/cases/{caseId}/timeline")
    @PreAuthorize(SCHEDULE_VIEW)
    public List<CaseEventVO> getCaseTimeline(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId) {
        log.info("获取案件时间线: caseId={}", caseId);
        return eventService.getCaseTimeline(caseId);
    }

    @Operation(
        summary = "检查事件是否存在",
        description = "检查指定ID的事件是否存在"
    )
    @GetMapping("/{eventId}/exists")
    @PreAuthorize(SCHEDULE_VIEW)
    public boolean checkEventExists(
            @Parameter(description = "事件ID") 
            @PathVariable("eventId") Long eventId) {
        log.info("检查事件是否存在: {}", eventId);
        return eventService.checkEventExists(eventId);
    }

    @Operation(
        summary = "统计案件事件数量",
        description = "统计指定案件的事件数量，支持按事件类型和状态统计"
    )
    @GetMapping("/cases/{caseId}/count")
    @PreAuthorize(SCHEDULE_VIEW)
    public int countEvents(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "事件类型：1-开庭，2-谈判，3-调解，4-会见当事人，5-证据交换") 
            @RequestParam(required = false) Integer eventType,
            @Parameter(description = "事件状态：1-未开始，2-进行中，3-已完成，4-已取消") 
            @RequestParam(required = false) Integer eventStatus) {
        log.info("统计案件事件数量: caseId={}, eventType={}, eventStatus={}", caseId, eventType, eventStatus);
        return eventService.countEvents(caseId, eventType, eventStatus);
    }
}
