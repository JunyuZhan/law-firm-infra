package com.lawfirm.schedule.service.impl;

import com.lawfirm.model.schedule.dto.ScheduleDTO;
import com.lawfirm.model.schedule.dto.ScheduleEventDTO;
import com.lawfirm.model.schedule.entity.enums.ScheduleType;
import com.lawfirm.model.schedule.service.ScheduleConflictService;
import com.lawfirm.model.schedule.service.ScheduleService;
import com.lawfirm.model.schedule.vo.ScheduleConflictVO;
import com.lawfirm.model.schedule.vo.ScheduleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日程冲突检测服务实现类
 */
@Service("scheduleConflictService")
@Slf4j
public class ScheduleConflictServiceImpl implements ScheduleConflictService {

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public boolean checkScheduleConflict(ScheduleDTO scheduleDTO) {
        if (scheduleDTO == null || scheduleDTO.getStartTime() == null || scheduleDTO.getEndTime() == null || scheduleDTO.getOwnerId() == null) {
            return false;
        }
        
        // 获取枚举类型的整数值
        Integer typeCode = scheduleDTO.getType() != null ? scheduleDTO.getType().getCode() : null;
        
        return checkScheduleConflict(
                scheduleDTO.getId(),
                scheduleDTO.getOwnerId(),
                scheduleDTO.getStartTime(),
                scheduleDTO.getEndTime(),
                typeCode
        );
    }

    @Override
    public boolean checkScheduleConflict(Long scheduleId, Long userId, LocalDateTime startTime, LocalDateTime endTime, Integer type) {
        log.info("检查日程冲突，用户ID：{}，时间范围：{} - {}", userId, startTime, endTime);
        
        if (userId == null || startTime == null || endTime == null) {
            return false;
        }
        
        // 获取可能冲突的日程列表
        List<ScheduleVO> conflictSchedules = scheduleService.checkConflicts(userId, startTime, endTime, scheduleId);
        
        return !conflictSchedules.isEmpty();
    }

    @Override
    public boolean checkEventConflict(ScheduleEventDTO eventDTO) {
        if (eventDTO == null || eventDTO.getScheduleId() == null || eventDTO.getStartTime() == null || eventDTO.getEndTime() == null) {
            return false;
        }
        
        return checkEventConflict(
                eventDTO.getEventId() != null ? Long.valueOf(eventDTO.getEventId()) : null,
                eventDTO.getScheduleId(),
                eventDTO.getStartTime(),
                eventDTO.getEndTime(),
                eventDTO.getType()
        );
    }

    @Override
    public boolean checkEventConflict(Long eventId, Long scheduleId, LocalDateTime startTime, LocalDateTime endTime, Integer type) {
        log.info("检查事件冲突，日程ID：{}，时间范围：{} - {}", scheduleId, startTime, endTime);
        
        // 此处可以实现事件冲突检查逻辑
        // 由于目前系统中不存在事件冲突检查的具体实现，暂时返回false
        return false;
    }

    @Override
    public List<ScheduleConflictVO> getConflictingSchedules(Long userId, LocalDateTime startTime, LocalDateTime endTime, Long excludeScheduleId) {
        log.info("获取冲突日程，用户ID：{}，时间范围：{} - {}", userId, startTime, endTime);
        
        if (userId == null || startTime == null || endTime == null) {
            return Collections.emptyList();
        }
        
        // 获取冲突的日程列表
        List<ScheduleVO> conflictSchedules = scheduleService.checkConflicts(userId, startTime, endTime, excludeScheduleId);
        
        // 转换为冲突VO
        return conflictSchedules.stream()
                .map(this::convertToConflictVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleConflictVO> getConflictingEvents(Long scheduleId, LocalDateTime startTime, LocalDateTime endTime, Long excludeEventId) {
        log.info("获取冲突事件，日程ID：{}，时间范围：{} - {}", scheduleId, startTime, endTime);
        
        // 此处可以实现事件冲突查询逻辑
        // 由于目前系统中不存在事件冲突查询的具体实现，暂时返回空列表
        return Collections.emptyList();
    }

    @Override
    public boolean checkMeetingRoomAvailability(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime, Long excludeBookingId) {
        log.info("检查会议室可用性，会议室ID：{}，时间范围：{} - {}", meetingRoomId, startTime, endTime);
        
        // 此处可以实现会议室可用性检查逻辑
        // 由于目前系统中不存在会议室可用性检查的具体实现，暂时返回true（表示可用）
        return true;
    }

    @Override
    public List<ScheduleConflictVO> getConflictingMeetingRoomBookings(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime, Long excludeBookingId) {
        log.info("获取会议室冲突预订，会议室ID：{}，时间范围：{} - {}", meetingRoomId, startTime, endTime);
        
        // 此处可以实现会议室冲突预订查询逻辑
        // 由于目前系统中不存在会议室冲突预订查询的具体实现，暂时返回空列表
        return Collections.emptyList();
    }
    
    /**
     * 将日程VO转换为冲突VO
     */
    private ScheduleConflictVO convertToConflictVO(ScheduleVO scheduleVO) {
        ScheduleConflictVO conflictVO = new ScheduleConflictVO();
        conflictVO.setConflictType(1); // 1表示日程冲突
        conflictVO.setConflictObjectId(scheduleVO.getId());
        conflictVO.setTitle(scheduleVO.getTitle());
        conflictVO.setStartTime(scheduleVO.getStartTime());
        conflictVO.setEndTime(scheduleVO.getEndTime());
        conflictVO.setOwnerId(scheduleVO.getOwnerId());
        conflictVO.setOwnerName(scheduleVO.getOwnerName());
        
        // 将枚举类型转换为整数类型
        if (scheduleVO.getType() != null) {
            conflictVO.setObjectType(scheduleVO.getType().getCode());
        }
        
        conflictVO.setLocation(scheduleVO.getLocation());
        
        // 计算冲突严重程度（简单实现）
        conflictVO.setSeverityLevel(3); // 默认为严重冲突
        conflictVO.setConflictDescription("与现有日程时间冲突");
        
        return conflictVO;
    }
} 