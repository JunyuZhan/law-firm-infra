package com.lawfirm.model.schedule.service;

import com.lawfirm.model.schedule.dto.ScheduleDTO;
import com.lawfirm.model.schedule.dto.ScheduleEventDTO;
import com.lawfirm.model.schedule.vo.ScheduleConflictVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 日程冲突检测服务接口
 */
public interface ScheduleConflictService {

    /**
     * 检测日程是否存在时间冲突
     *
     * @param scheduleDTO 日程信息
     * @return 是否冲突
     */
    boolean checkScheduleConflict(ScheduleDTO scheduleDTO);

    /**
     * 检测日程是否存在时间冲突
     *
     * @param scheduleId 当前日程ID（更新时使用，新建时可为null）
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param type 日程类型（可为null）
     * @return 是否冲突
     */
    boolean checkScheduleConflict(Long scheduleId, Long userId, LocalDateTime startTime, LocalDateTime endTime, Integer type);

    /**
     * 检测事件是否存在时间冲突
     *
     * @param eventDTO 事件信息
     * @return 是否冲突
     */
    boolean checkEventConflict(ScheduleEventDTO eventDTO);

    /**
     * 检测事件是否存在时间冲突
     *
     * @param eventId 当前事件ID（更新时使用，新建时可为null）
     * @param scheduleId 日程ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param type 事件类型（可为null）
     * @return 是否冲突
     */
    boolean checkEventConflict(Long eventId, Long scheduleId, LocalDateTime startTime, LocalDateTime endTime, Integer type);

    /**
     * 获取与指定时间范围冲突的日程
     *
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param excludeScheduleId 排除的日程ID（可为null）
     * @return 冲突的日程信息列表
     */
    List<ScheduleConflictVO> getConflictingSchedules(Long userId, LocalDateTime startTime, LocalDateTime endTime, Long excludeScheduleId);

    /**
     * 获取与指定时间范围冲突的事件
     *
     * @param scheduleId 日程ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param excludeEventId 排除的事件ID（可为null）
     * @return 冲突的事件信息列表
     */
    List<ScheduleConflictVO> getConflictingEvents(Long scheduleId, LocalDateTime startTime, LocalDateTime endTime, Long excludeEventId);

    /**
     * 检测会议室在指定时间是否可用
     *
     * @param meetingRoomId 会议室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param excludeBookingId 排除的预订ID（可为null）
     * @return 是否可用
     */
    boolean checkMeetingRoomAvailability(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime, Long excludeBookingId);

    /**
     * 获取会议室在指定时间段的冲突预订
     *
     * @param meetingRoomId 会议室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param excludeBookingId 排除的预订ID（可为null）
     * @return 冲突的预订信息列表
     */
    List<ScheduleConflictVO> getConflictingMeetingRoomBookings(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime, Long excludeBookingId);
} 