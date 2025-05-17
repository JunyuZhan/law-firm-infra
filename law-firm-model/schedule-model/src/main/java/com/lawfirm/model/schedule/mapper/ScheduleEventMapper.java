package com.lawfirm.model.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.schedule.entity.ScheduleEvent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 日程事件Mapper接口
 */
@Mapper
public interface ScheduleEventMapper extends BaseMapper<ScheduleEvent> {
    
    /**
     * 查询特定时间区间的事件
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 事件列表
     */
    @Select("SELECT e.* FROM schedule_event e " +
            "INNER JOIN schedule s ON e.schedule_id = s.id " +
            "WHERE s.start_time >= #{startTime} AND s.start_time <= #{endTime} " +
            "ORDER BY e.create_time DESC")
    List<ScheduleEvent> findEventsByTimeRange(@Param("startTime") LocalDateTime startTime,
                                              @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询用户的近期事件
     *
     * @param userId    用户ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 事件列表
     */
    @Select("SELECT e.* FROM schedule_event e " +
            "INNER JOIN schedule s ON e.schedule_id = s.id " +
            "LEFT JOIN schedule_participant p ON s.id = p.schedule_id " +
            "WHERE (s.creator_id = #{userId} OR p.participant_id = #{userId}) " +
            "AND s.start_time >= #{startTime} AND s.start_time <= #{endTime} " +
            "ORDER BY e.create_time DESC")
    List<ScheduleEvent> findRecentEvents(@Param("userId") Long userId,
                                         @Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计用户的事件数量
     *
     * @param userId    用户ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 事件数量
     */
    @Select("SELECT COUNT(DISTINCT e.id) FROM schedule_event e " +
            "INNER JOIN schedule s ON e.schedule_id = s.id " +
            "LEFT JOIN schedule_participant p ON s.id = p.schedule_id " +
            "WHERE (s.creator_id = #{userId} OR p.participant_id = #{userId}) " +
            "AND s.start_time >= #{startTime} AND s.start_time <= #{endTime}")
    int countUserEvents(@Param("userId") Long userId,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);
} 