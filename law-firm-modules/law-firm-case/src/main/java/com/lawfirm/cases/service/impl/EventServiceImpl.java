package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.model.cases.dto.business.CaseEventDTO;
import com.lawfirm.model.cases.entity.business.CaseEvent;
import com.lawfirm.model.cases.mapper.business.CaseEventMapper;
import com.lawfirm.model.cases.service.business.CaseEventService;
import com.lawfirm.model.cases.vo.business.CaseEventVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 案件事件服务实现类
 */
@Slf4j
@Service("caseEventServiceImpl")
@RequiredArgsConstructor
public class EventServiceImpl implements CaseEventService {

    private final CaseEventMapper eventMapper;
    private final CaseAuditProvider auditProvider;
    private final CaseMessageManager messageManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createEvent(CaseEventDTO eventDTO) {
        log.info("创建事件: 案件ID={}, 事件标题={}", eventDTO.getCaseId(), eventDTO.getEventTitle());
        
        // 1. 创建事件实体
        CaseEvent event = new CaseEvent();
        BeanUtils.copyProperties(eventDTO, event);
        event.setCreateTime(LocalDateTime.now());
        event.setUpdateTime(LocalDateTime.now());
        
        // 2. 保存事件
        eventMapper.insert(event);
        Long eventId = event.getId();
        
        // 3. 处理参与人
        if (eventDTO.getParticipantIds() != null && !eventDTO.getParticipantIds().isEmpty()) {
            String participantIds = eventDTO.getParticipantIds().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            event.setParticipantIds(participantIds);
            
            if (eventDTO.getParticipantNames() != null && !eventDTO.getParticipantNames().isEmpty()) {
                String participantNames = String.join(",", eventDTO.getParticipantNames());
                event.setParticipantNames(participantNames);
            }
            
            eventMapper.updateById(event);
        }
        
        // 4. 记录审计
        // 获取当前操作用户ID（应从上下文或认证信息中获取）
        Long operatorId = 0L; // 默认值，实际应从上下文获取
        
        auditProvider.auditCaseStatusChange(
                eventDTO.getCaseId(),
                operatorId,
                "0", // 无状态
                "1", // 创建状态
                "创建事件: " + event.getEventTitle()
        );
        
        // 5. 发送消息通知
        Map<String, Object> changes = Map.of(
            "changeType", "EVENT_CREATED",
            "eventId", eventId,
            "eventTitle", event.getEventTitle(),
            "eventType", event.getEventType(),
            "startTime", event.getStartTime(),
            "endTime", event.getEndTime()
        );
        
        List<Map<String, Object>> changesList = List.of(changes);
        messageManager.sendCaseTeamChangeMessage(
                eventDTO.getCaseId(),
                changesList,
                operatorId
        );
        
        log.info("事件创建成功, ID: {}", eventId);
        return eventId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchCreateEvents(List<CaseEventDTO> eventDTOs) {
        log.info("批量创建事件: 数量={}", eventDTOs.size());
        
        for (CaseEventDTO dto : eventDTOs) {
            createEvent(dto);
        }
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateEvent(CaseEventDTO eventDTO) {
        log.info("更新事件: ID={}", eventDTO.getId());
        
        // 1. 获取原事件数据
        CaseEvent oldEvent = eventMapper.selectById(eventDTO.getId());
        if (oldEvent == null) {
            throw new RuntimeException("事件不存在: " + eventDTO.getId());
        }
        
        // 2. 更新事件数据
        CaseEvent event = new CaseEvent();
        BeanUtils.copyProperties(eventDTO, event);
        event.setUpdateTime(LocalDateTime.now());
        
        // 3. 处理参与人
        if (eventDTO.getParticipantIds() != null) {
            String participantIds = eventDTO.getParticipantIds().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            event.setParticipantIds(participantIds);
            
            if (eventDTO.getParticipantNames() != null) {
                String participantNames = String.join(",", eventDTO.getParticipantNames());
                event.setParticipantNames(participantNames);
            }
        }
        
        // 4. 保存更新
        int result = eventMapper.updateById(event);
        
        // 5. 记录审计
        Map<String, Object> changes = new HashMap<>();
        if (oldEvent.getEventTitle() != null && event.getEventTitle() != null && 
            !oldEvent.getEventTitle().equals(event.getEventTitle())) {
            changes.put("eventTitle", Map.of("old", oldEvent.getEventTitle(), "new", event.getEventTitle()));
        }
        if (oldEvent.getEventType() != null && event.getEventType() != null && 
            !oldEvent.getEventType().equals(event.getEventType())) {
            changes.put("eventType", Map.of("old", oldEvent.getEventType(), "new", event.getEventType()));
        }
        if (oldEvent.getEventStatus() != null && event.getEventStatus() != null && 
            !oldEvent.getEventStatus().equals(event.getEventStatus())) {
            changes.put("eventStatus", Map.of("old", oldEvent.getEventStatus(), "new", event.getEventStatus()));
        }
        if (oldEvent.getStartTime() != null && event.getStartTime() != null && 
            !oldEvent.getStartTime().equals(event.getStartTime())) {
            changes.put("startTime", Map.of("old", oldEvent.getStartTime(), "new", event.getStartTime()));
        }
        if (oldEvent.getEndTime() != null && event.getEndTime() != null && 
            !oldEvent.getEndTime().equals(event.getEndTime())) {
            changes.put("endTime", Map.of("old", oldEvent.getEndTime(), "new", event.getEndTime()));
        }
        
        // 获取当前操作用户ID（应从上下文或认证信息中获取）
        Long operatorId = 0L; // 默认值，实际应从上下文获取
        
        auditProvider.auditCaseUpdate(
                eventDTO.getCaseId(),
                operatorId,
                oldEvent,
                event,
                changes
        );
        
        // 6. 发送消息通知
        Map<String, Object> eventChanges = Map.of(
            "changeType", "EVENT_UPDATED",
            "eventId", event.getId(),
            "eventTitle", event.getEventTitle(),
            "changes", changes
        );
        
        List<Map<String, Object>> changesList = List.of(eventChanges);
        messageManager.sendCaseTeamChangeMessage(
                eventDTO.getCaseId(),
                changesList,
                operatorId
        );
        
        log.info("事件更新成功, ID: {}", eventDTO.getId());
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteEvent(Long eventId) {
        log.info("删除事件: ID={}", eventId);
        
        // 1. 获取事件数据
        CaseEvent event = eventMapper.selectById(eventId);
        if (event == null) {
            throw new RuntimeException("事件不存在: " + eventId);
        }
        
        // 2. 软删除事件
        event.setDeleted(1);
        event.setUpdateTime(LocalDateTime.now());
        int result = eventMapper.updateById(event);
        
        // 3. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                event.getCaseId(),
                operatorId,
                "1", // 正常状态
                "9", // 删除状态
                "删除事件: " + event.getEventTitle()
        );
        
        // 4. 发送消息通知
        Map<String, Object> changes = Map.of(
            "changeType", "EVENT_DELETED",
            "eventId", eventId,
            "eventTitle", event.getEventTitle()
        );
        
        List<Map<String, Object>> changesList = List.of(changes);
        messageManager.sendCaseTeamChangeMessage(
                event.getCaseId(),
                changesList,
                operatorId
        );
        
        log.info("事件删除成功, ID: {}", eventId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteEvents(List<Long> eventIds) {
        log.info("批量删除事件: 数量={}", eventIds.size());
        
        for (Long id : eventIds) {
            deleteEvent(id);
        }
        
        return true;
    }

    @Override
    public CaseEventVO getEventDetail(Long eventId) {
        log.info("获取事件详情: ID={}", eventId);
        
        CaseEvent event = eventMapper.selectById(eventId);
        if (event == null) {
            throw new RuntimeException("事件不存在: " + eventId);
        }
        
        CaseEventVO vo = new CaseEventVO();
        BeanUtils.copyProperties(event, vo);
        
        return vo;
    }

    @Override
    public List<CaseEventVO> listCaseEvents(Long caseId) {
        log.info("获取案件所有事件: 案件ID={}", caseId);
        
        LambdaQueryWrapper<CaseEvent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseEvent::getCaseId, caseId);
        wrapper.eq(CaseEvent::getDeleted, 0); // 只查询未删除的
        wrapper.orderByDesc(CaseEvent::getCreateTime);
        
        List<CaseEvent> events = eventMapper.selectList(wrapper);
        
        return events.stream().map(entity -> {
            CaseEventVO vo = new CaseEventVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public IPage<CaseEventVO> pageEvents(Long caseId, Integer eventType, Integer eventStatus, 
                                       Integer pageNum, Integer pageSize) {
        log.info("分页查询事件: 案件ID={}, 事件类型={}, 事件状态={}, 页码={}, 每页大小={}", 
                 caseId, eventType, eventStatus, pageNum, pageSize);
        
        Page<CaseEvent> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CaseEvent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseEvent::getCaseId, caseId);
        wrapper.eq(CaseEvent::getDeleted, 0); // 只查询未删除的
        
        if (eventType != null) {
            wrapper.eq(CaseEvent::getEventType, eventType);
        }
        
        if (eventStatus != null) {
            wrapper.eq(CaseEvent::getEventStatus, eventStatus);
        }
        
        // 排序
        wrapper.orderByDesc(CaseEvent::getUpdateTime);
        
        // 执行查询
        IPage<CaseEvent> resultPage = eventMapper.selectPage(page, wrapper);
        
        // 转换为VO
        return resultPage.convert(entity -> {
            CaseEventVO vo = new CaseEventVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean startEvent(Long eventId) {
        log.info("开始事件: ID={}", eventId);
        
        // 1. 获取事件数据
        CaseEvent event = eventMapper.selectById(eventId);
        if (event == null) {
            throw new RuntimeException("事件不存在: " + eventId);
        }
        
        // 2. 更新事件状态
        Integer oldStatus = event.getEventStatus();
        event.setEventStatus(2); // 假设2表示进行中
        event.setUpdateTime(LocalDateTime.now());
        
        // 如果实际开始时间为空，设置为当前时间
        if (event.getStartTime() == null) {
            event.setStartTime(LocalDateTime.now());
        }
        
        int result = eventMapper.updateById(event);
        
        // 3. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                event.getCaseId(),
                operatorId,
                oldStatus != null ? oldStatus.toString() : "1", // 默认为待开始
                "2", // 进行中
                "开始事件: " + event.getEventTitle()
        );
        
        log.info("事件开始成功, ID: {}", eventId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean completeEvent(Long eventId, String completionNote) {
        log.info("完成事件: ID={}", eventId);
        
        // 1. 获取事件数据
        CaseEvent event = eventMapper.selectById(eventId);
        if (event == null) {
            throw new RuntimeException("事件不存在: " + eventId);
        }
        
        // 2. 更新事件状态
        Integer oldStatus = event.getEventStatus();
        event.setEventStatus(3); // 假设3表示已完成
        event.setUpdateTime(LocalDateTime.now());
        
        // 如果实际结束时间为空，设置为当前时间
        if (event.getEndTime() == null) {
            event.setEndTime(LocalDateTime.now());
        }
        
        // 添加完成说明
        if (StringUtils.hasText(completionNote)) {
            String currentRemarks = event.getRemarks();
            if (StringUtils.hasText(currentRemarks)) {
                event.setRemarks(currentRemarks + "\n完成说明: " + completionNote);
            } else {
                event.setRemarks("完成说明: " + completionNote);
            }
        }
        
        int result = eventMapper.updateById(event);
        
        // 3. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                event.getCaseId(),
                operatorId,
                oldStatus != null ? oldStatus.toString() : "2", // 默认为进行中
                "3", // 已完成
                "完成事件: " + event.getEventTitle()
        );
        
        log.info("事件完成成功, ID: {}", eventId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelEvent(Long eventId, String reason) {
        log.info("取消事件: ID={}", eventId);
        
        // 1. 获取事件数据
        CaseEvent event = eventMapper.selectById(eventId);
        if (event == null) {
            throw new RuntimeException("事件不存在: " + eventId);
        }
        
        // 2. 更新事件状态
        Integer oldStatus = event.getEventStatus();
        event.setEventStatus(4); // 假设4表示已取消
        event.setUpdateTime(LocalDateTime.now());
        
        // 添加取消原因
        if (StringUtils.hasText(reason)) {
            String currentRemarks = event.getRemarks();
            if (StringUtils.hasText(currentRemarks)) {
                event.setRemarks(currentRemarks + "\n取消原因: " + reason);
            } else {
                event.setRemarks("取消原因: " + reason);
            }
        }
        
        int result = eventMapper.updateById(event);
        
        // 3. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                event.getCaseId(),
                operatorId,
                oldStatus != null ? oldStatus.toString() : "0", 
                "4", // 取消状态
                "取消事件: " + event.getEventTitle() + (reason != null ? ", 原因: " + reason : "")
        );
        
        log.info("事件取消成功, ID: {}", eventId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addParticipant(Long eventId, Long participantId) {
        log.info("添加事件参与人: 事件ID={}, 参与人ID={}", eventId, participantId);
        
        // 1. 获取事件数据
        CaseEvent event = eventMapper.selectById(eventId);
        if (event == null) {
            throw new RuntimeException("事件不存在: " + eventId);
        }
        
        // 2. 添加参与人
        String currentParticipantIds = event.getParticipantIds();
        if (StringUtils.hasText(currentParticipantIds)) {
            // 检查是否已存在
            List<String> idList = new ArrayList<>(Arrays.asList(currentParticipantIds.split(",")));
            if (!idList.contains(participantId.toString())) {
                idList.add(participantId.toString());
                event.setParticipantIds(String.join(",", idList));
            }
        } else {
            event.setParticipantIds(participantId.toString());
        }
        
        event.setUpdateTime(LocalDateTime.now());
        
        int result = eventMapper.updateById(event);
        
        // 3. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                event.getCaseId(),
                operatorId,
                null, 
                null,
                "添加事件参与人: " + event.getEventTitle() + ", 参与人ID: " + participantId
        );
        
        log.info("事件参与人添加成功, 事件ID: {}, 参与人ID: {}", eventId, participantId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeParticipant(Long eventId, Long participantId) {
        log.info("移除事件参与人: 事件ID={}, 参与人ID={}", eventId, participantId);
        
        // 1. 获取事件数据
        CaseEvent event = eventMapper.selectById(eventId);
        if (event == null) {
            throw new RuntimeException("事件不存在: " + eventId);
        }
        
        // 2. 移除参与人
        String currentParticipantIds = event.getParticipantIds();
        if (StringUtils.hasText(currentParticipantIds)) {
            List<String> idList = new ArrayList<>(Arrays.asList(currentParticipantIds.split(",")));
            if (idList.remove(participantId.toString())) {
                event.setParticipantIds(String.join(",", idList));
                event.setUpdateTime(LocalDateTime.now());
                
                int result = eventMapper.updateById(event);
                
                // 3. 记录审计
                Long operatorId = 0L; // 实际应从上下文或参数获取
                
                auditProvider.auditCaseStatusChange(
                        event.getCaseId(),
                        operatorId,
                        null, 
                        null,
                        "移除事件参与人: " + event.getEventTitle() + ", 参与人ID: " + participantId
                );
                
                log.info("事件参与人移除成功, 事件ID: {}, 参与人ID: {}", eventId, participantId);
                return result > 0;
            }
        }
        
        log.info("事件参与人不存在, 无需移除, 事件ID: {}, 参与人ID: {}", eventId, participantId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAttachment(Long eventId, Long fileId) {
        log.info("添加事件附件: 事件ID={}, 文件ID={}", eventId, fileId);
        
        // 1. 获取事件数据
        CaseEvent event = eventMapper.selectById(eventId);
        if (event == null) {
            throw new RuntimeException("事件不存在: " + eventId);
        }
        
        // 2. 添加附件
        String currentDocumentIds = event.getDocumentIds();
        if (StringUtils.hasText(currentDocumentIds)) {
            // 检查是否已存在
            List<String> idList = new ArrayList<>(Arrays.asList(currentDocumentIds.split(",")));
            if (!idList.contains(fileId.toString())) {
                idList.add(fileId.toString());
                event.setDocumentIds(String.join(",", idList));
            }
        } else {
            event.setDocumentIds(fileId.toString());
        }
        
        event.setUpdateTime(LocalDateTime.now());
        
        int result = eventMapper.updateById(event);
        
        // 3. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                event.getCaseId(),
                operatorId,
                null, 
                null,
                "添加事件附件: " + event.getEventTitle() + ", 文件ID: " + fileId
        );
        
        log.info("事件附件添加成功, 事件ID: {}, 文件ID: {}", eventId, fileId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeAttachment(Long eventId, Long fileId) {
        log.info("移除事件附件: 事件ID={}, 文件ID={}", eventId, fileId);
        
        // 1. 获取事件数据
        CaseEvent event = eventMapper.selectById(eventId);
        if (event == null) {
            throw new RuntimeException("事件不存在: " + eventId);
        }
        
        // 2. 移除附件
        String currentDocumentIds = event.getDocumentIds();
        if (StringUtils.hasText(currentDocumentIds)) {
            List<String> idList = new ArrayList<>(Arrays.asList(currentDocumentIds.split(",")));
            if (idList.remove(fileId.toString())) {
                event.setDocumentIds(String.join(",", idList));
                event.setUpdateTime(LocalDateTime.now());
                
                int result = eventMapper.updateById(event);
                
                // 3. 记录审计
                Long operatorId = 0L; // 实际应从上下文或参数获取
                
                auditProvider.auditCaseStatusChange(
                        event.getCaseId(),
                        operatorId,
                        null, 
                        null,
                        "移除事件附件: " + event.getEventTitle() + ", 文件ID: " + fileId
                );
                
                log.info("事件附件移除成功, 事件ID: {}, 文件ID: {}", eventId, fileId);
                return result > 0;
            }
        }
        
        log.info("事件附件不存在, 无需移除, 事件ID: {}, 文件ID: {}", eventId, fileId);
        return true;
    }
    
    @Override
    public List<CaseEventVO> listUserEvents(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("获取用户事件列表: 用户ID={}, 开始时间={}, 结束时间={}", userId, startTime, endTime);
        
        // 查询用户参与的事件
        LambdaQueryWrapper<CaseEvent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseEvent::getDeleted, 0);
        wrapper.and(w -> w.eq(CaseEvent::getOrganizerId, userId)
                .or()
                .like(CaseEvent::getParticipantIds, userId));
        
        if (startTime != null) {
            wrapper.ge(CaseEvent::getStartTime, startTime);
        }
        
        if (endTime != null) {
            wrapper.le(CaseEvent::getEndTime, endTime);
        }
        
        wrapper.orderByAsc(CaseEvent::getStartTime);
        
        List<CaseEvent> events = eventMapper.selectList(wrapper);
        
        return events.stream().map(entity -> {
            CaseEventVO vo = new CaseEventVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
    }
    
    @Override
    public List<CaseEventVO> listUserTodoEvents(Long userId) {
        log.info("获取用户待办事件: 用户ID={}", userId);
        
        // 查询用户待办的事件（未完成且未取消的事件）
        LambdaQueryWrapper<CaseEvent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseEvent::getDeleted, 0);
        wrapper.and(w -> w.eq(CaseEvent::getOrganizerId, userId)
                .or()
                .like(CaseEvent::getParticipantIds, userId));
        
        // 状态为未开始或进行中
        wrapper.in(CaseEvent::getEventStatus, Arrays.asList(1, 2));
        
        // 按优先级和开始时间排序
        wrapper.orderByDesc(CaseEvent::getEventPriority);
        wrapper.orderByAsc(CaseEvent::getStartTime);
        
        List<CaseEvent> events = eventMapper.selectList(wrapper);
        
        return events.stream().map(entity -> {
            CaseEventVO vo = new CaseEventVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
    }
    
    @Override
    public List<CaseEventVO> getCaseTimeline(Long caseId) {
        log.info("获取案件时间线: 案件ID={}", caseId);
        
        LambdaQueryWrapper<CaseEvent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseEvent::getCaseId, caseId);
        wrapper.eq(CaseEvent::getDeleted, 0);
        wrapper.orderByAsc(CaseEvent::getStartTime);
        
        List<CaseEvent> events = eventMapper.selectList(wrapper);
        
        return events.stream().map(entity -> {
            CaseEventVO vo = new CaseEventVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
    }
    
    @Override
    public boolean checkEventExists(Long eventId) {
        log.info("检查事件是否存在: ID={}", eventId);
        
        if (eventId == null) {
            return false;
        }
        
        LambdaQueryWrapper<CaseEvent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseEvent::getId, eventId);
        wrapper.eq(CaseEvent::getDeleted, 0);
        
        return eventMapper.selectCount(wrapper) > 0;
    }
    
    @Override
    public int countEvents(Long caseId, Integer eventType, Integer eventStatus) {
        log.info("统计事件数量: 案件ID={}, 事件类型={}, 状态={}", caseId, eventType, eventStatus);
        
        LambdaQueryWrapper<CaseEvent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseEvent::getCaseId, caseId);
        wrapper.eq(CaseEvent::getDeleted, 0);
        
        if (eventType != null) {
            wrapper.eq(CaseEvent::getEventType, eventType);
        }
        
        if (eventStatus != null) {
            wrapper.eq(CaseEvent::getEventStatus, eventStatus);
        }
        
        return eventMapper.selectCount(wrapper).intValue();
    }
}
