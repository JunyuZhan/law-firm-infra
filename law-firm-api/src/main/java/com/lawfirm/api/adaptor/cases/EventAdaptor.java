package com.lawfirm.api.adaptor.cases;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.cases.dto.business.CaseEventDTO;
import com.lawfirm.model.cases.service.business.CaseEventService;
import com.lawfirm.model.cases.vo.business.CaseEventVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件事件适配器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EventAdaptor extends BaseAdaptor {

    private final CaseEventService eventService;

    /**
     * 创建事件
     */
    public Long createEvent(CaseEventDTO eventDTO) {
        log.info("创建事件: {}", eventDTO);
        return eventService.createEvent(eventDTO);
    }

    /**
     * 批量创建事件
     */
    public boolean batchCreateEvents(List<CaseEventDTO> eventDTOs) {
        log.info("批量创建事件: {}", eventDTOs);
        return eventService.batchCreateEvents(eventDTOs);
    }

    /**
     * 更新事件
     */
    public boolean updateEvent(CaseEventDTO eventDTO) {
        log.info("更新事件: {}", eventDTO);
        return eventService.updateEvent(eventDTO);
    }

    /**
     * 删除事件
     */
    public boolean deleteEvent(Long eventId) {
        log.info("删除事件: {}", eventId);
        return eventService.deleteEvent(eventId);
    }

    /**
     * 批量删除事件
     */
    public boolean batchDeleteEvents(List<Long> eventIds) {
        log.info("批量删除事件: {}", eventIds);
        return eventService.batchDeleteEvents(eventIds);
    }

    /**
     * 获取事件详情
     */
    public CaseEventVO getEventDetail(Long eventId) {
        log.info("获取事件详情: {}", eventId);
        return eventService.getEventDetail(eventId);
    }

    /**
     * 获取案件的所有事件
     */
    public List<CaseEventVO> listCaseEvents(Long caseId) {
        log.info("获取案件的所有事件: caseId={}", caseId);
        return eventService.listCaseEvents(caseId);
    }

    /**
     * 分页查询事件
     */
    public IPage<CaseEventVO> pageEvents(Long caseId, Integer eventType, Integer eventStatus, Integer pageNum, Integer pageSize) {
        log.info("分页查询事件: caseId={}, eventType={}, eventStatus={}, pageNum={}, pageSize={}", 
                caseId, eventType, eventStatus, pageNum, pageSize);
        return eventService.pageEvents(caseId, eventType, eventStatus, pageNum, pageSize);
    }

    /**
     * 开始事件
     */
    public boolean startEvent(Long eventId) {
        log.info("开始事件: {}", eventId);
        return eventService.startEvent(eventId);
    }

    /**
     * 完成事件
     */
    public boolean completeEvent(Long eventId, String completionNote) {
        log.info("完成事件: eventId={}, completionNote={}", eventId, completionNote);
        return eventService.completeEvent(eventId, completionNote);
    }

    /**
     * 取消事件
     */
    public boolean cancelEvent(Long eventId, String reason) {
        log.info("取消事件: eventId={}, reason={}", eventId, reason);
        return eventService.cancelEvent(eventId, reason);
    }

    /**
     * 添加事件参与人
     */
    public boolean addParticipant(Long eventId, Long participantId) {
        log.info("添加事件参与人: eventId={}, participantId={}", eventId, participantId);
        return eventService.addParticipant(eventId, participantId);
    }

    /**
     * 移除事件参与人
     */
    public boolean removeParticipant(Long eventId, Long participantId) {
        log.info("移除事件参与人: eventId={}, participantId={}", eventId, participantId);
        return eventService.removeParticipant(eventId, participantId);
    }

    /**
     * 添加事件附件
     */
    public boolean addAttachment(Long eventId, Long fileId) {
        log.info("添加事件附件: eventId={}, fileId={}", eventId, fileId);
        return eventService.addAttachment(eventId, fileId);
    }

    /**
     * 移除事件附件
     */
    public boolean removeAttachment(Long eventId, Long fileId) {
        log.info("移除事件附件: eventId={}, fileId={}", eventId, fileId);
        return eventService.removeAttachment(eventId, fileId);
    }

    /**
     * 获取用户的事件列表
     */
    public List<CaseEventVO> listUserEvents(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("获取用户的事件列表: userId={}, startTime={}, endTime={}", userId, startTime, endTime);
        return eventService.listUserEvents(userId, startTime, endTime);
    }

    /**
     * 获取用户的待办事件
     */
    public List<CaseEventVO> listUserTodoEvents(Long userId) {
        log.info("获取用户的待办事件: userId={}", userId);
        return eventService.listUserTodoEvents(userId);
    }

    /**
     * 获取案件时间线
     */
    public List<CaseEventVO> getCaseTimeline(Long caseId) {
        log.info("获取案件时间线: caseId={}", caseId);
        return eventService.getCaseTimeline(caseId);
    }

    /**
     * 检查事件是否存在
     */
    public boolean checkEventExists(Long eventId) {
        log.info("检查事件是否存在: {}", eventId);
        return eventService.checkEventExists(eventId);
    }

    /**
     * 统计案件事件数量
     */
    public int countEvents(Long caseId, Integer eventType, Integer eventStatus) {
        log.info("统计案件事件数量: caseId={}, eventType={}, eventStatus={}", caseId, eventType, eventStatus);
        return eventService.countEvents(caseId, eventType, eventStatus);
    }
} 