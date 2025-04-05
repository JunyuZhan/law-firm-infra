package com.lawfirm.model.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.schedule.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 日程Mapper接口
 */
@Mapper
public interface ScheduleMapper extends BaseMapper<Schedule> {
    
    /**
     * 查询指定时间范围内的日程
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param ownerId 所有者ID，可为null
     * @return 日程列表
     */
    List<Schedule> findByTimeRange(@Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime,
                                  @Param("ownerId") Long ownerId);
    
    /**
     * 分页查询用户的日程
     *
     * @param page 分页参数
     * @param ownerId 所有者ID
     * @return 分页结果
     */
    IPage<Schedule> pageByOwnerId(Page<Schedule> page, @Param("ownerId") Long ownerId);
    
    /**
     * 查询参与者的所有日程
     *
     * @param participantId 参与者ID
     * @return 日程列表
     */
    List<Schedule> findByParticipantId(@Param("participantId") Long participantId);
    
    /**
     * 查询与案件关联的日程
     *
     * @param caseId 案件ID
     * @return 日程列表
     */
    List<Schedule> findByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 查询与任务关联的日程
     *
     * @param taskId 任务ID
     * @return 日程列表
     */
    List<Schedule> findByTaskId(@Param("taskId") Long taskId);
    
    /**
     * 查询日程冲突
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param participantId 参与者ID
     * @param excludeScheduleId 排除的日程ID，用于更新操作
     * @return 冲突的日程列表
     */
    List<Schedule> findConflicts(@Param("startTime") LocalDateTime startTime,
                                @Param("endTime") LocalDateTime endTime,
                                @Param("participantId") Long participantId,
                                @Param("excludeScheduleId") Long excludeScheduleId);
} 