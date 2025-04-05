package com.lawfirm.model.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.schedule.entity.MeetingRoomReservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会议室预约Mapper接口
 */
@Mapper
public interface MeetingRoomReservationMapper extends BaseMapper<MeetingRoomReservation> {
    
    /**
     * 查询会议室在指定时间段的预约
     *
     * @param meetingRoomId 会议室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 预约列表
     */
    List<MeetingRoomReservation> findByMeetingRoomAndTimeRange(
            @Param("meetingRoomId") Long meetingRoomId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询用户的所有预约
     *
     * @param page 分页参数
     * @param reserverId 预约者ID
     * @param statusCode 状态码，可为null
     * @return 预约分页结果
     */
    IPage<MeetingRoomReservation> pageByReserverId(
            Page<MeetingRoomReservation> page,
            @Param("reserverId") Long reserverId,
            @Param("statusCode") Integer statusCode);
    
    /**
     * 查询需要审核的预约
     *
     * @param page 分页参数
     * @param statusCode 状态码
     * @return 预约分页结果
     */
    IPage<MeetingRoomReservation> pageByStatus(
            Page<MeetingRoomReservation> page,
            @Param("statusCode") Integer statusCode);
    
    /**
     * 更新预约状态
     *
     * @param id 预约ID
     * @param statusCode 状态码
     * @param reviewerId 审核人ID，可为null
     * @param reviewComments 审核意见，可为null
     * @return 影响行数
     */
    int updateStatus(
            @Param("id") Long id,
            @Param("statusCode") Integer statusCode,
            @Param("reviewerId") Long reviewerId,
            @Param("reviewComments") String reviewComments);
    
    /**
     * 根据日程ID查询预约
     *
     * @param scheduleId 日程ID
     * @return 预约信息
     */
    MeetingRoomReservation findByScheduleId(@Param("scheduleId") Long scheduleId);
    
    /**
     * 根据日程ID删除预约
     *
     * @param scheduleId 日程ID
     * @return 影响行数
     */
    int deleteByScheduleId(@Param("scheduleId") Long scheduleId);
} 