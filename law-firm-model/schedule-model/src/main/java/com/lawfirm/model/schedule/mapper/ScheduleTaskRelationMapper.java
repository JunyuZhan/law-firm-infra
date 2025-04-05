package com.lawfirm.model.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.schedule.entity.ScheduleTaskRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 日程与任务关联Mapper接口
 */
@Mapper
public interface ScheduleTaskRelationMapper extends BaseMapper<ScheduleTaskRelation> {
    
    /**
     * 查询日程关联的所有任务ID
     *
     * @param scheduleId 日程ID
     * @return 任务ID列表
     */
    List<Long> findTaskIdsByScheduleId(@Param("scheduleId") Long scheduleId);
    
    /**
     * 查询任务关联的所有日程ID
     *
     * @param taskId 任务ID
     * @return 日程ID列表
     */
    List<Long> findScheduleIdsByTaskId(@Param("taskId") Long taskId);
    
    /**
     * 批量添加日程与任务关联
     *
     * @param relations 关联列表
     * @return 影响行数
     */
    int batchInsert(@Param("list") List<ScheduleTaskRelation> relations);
    
    /**
     * 根据日程ID删除所有关联
     *
     * @param scheduleId 日程ID
     * @return 影响行数
     */
    int deleteByScheduleId(@Param("scheduleId") Long scheduleId);
    
    /**
     * 根据任务ID删除所有关联
     *
     * @param taskId 任务ID
     * @return 影响行数
     */
    int deleteByTaskId(@Param("taskId") Long taskId);
    
    /**
     * 删除特定的日程与任务关联
     *
     * @param scheduleId 日程ID
     * @param taskId 任务ID
     * @return 影响行数
     */
    int deleteByScheduleIdAndTaskId(@Param("scheduleId") Long scheduleId, @Param("taskId") Long taskId);
} 