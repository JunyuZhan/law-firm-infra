package com.lawfirm.model.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.schedule.entity.ScheduleParticipant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 日程参与者Mapper接口
 */
@Mapper
public interface ScheduleParticipantMapper extends BaseMapper<ScheduleParticipant> {
    
    /**
     * 查询日程的所有参与者
     *
     * @param scheduleId 日程ID
     * @return 参与者列表
     */
    List<ScheduleParticipant> findByScheduleId(@Param("scheduleId") Long scheduleId);
    
    /**
     * 查询用户参与的所有日程ID
     *
     * @param participantId 参与者ID
     * @return 日程ID列表
     */
    List<Long> findScheduleIdsByParticipantId(@Param("participantId") Long participantId);
    
    /**
     * 根据日程ID删除所有参与者
     *
     * @param scheduleId 日程ID
     * @return 影响行数
     */
    int deleteByScheduleId(@Param("scheduleId") Long scheduleId);
    
    /**
     * 批量添加参与者
     *
     * @param participants 参与者列表
     * @return 影响行数
     */
    int batchInsert(@Param("list") List<ScheduleParticipant> participants);
    
    /**
     * 更新参与者响应状态
     *
     * @param id 参与者记录ID
     * @param responseStatusCode 响应状态码
     * @param comments 回复意见
     * @return 影响行数
     */
    int updateResponseStatus(@Param("id") Long id, 
                             @Param("responseStatusCode") Integer responseStatusCode,
                             @Param("comments") String comments);
} 