package com.lawfirm.model.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.schedule.entity.ScheduleCaseRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 日程与案件关联Mapper接口
 */
@Mapper
public interface ScheduleCaseRelationMapper extends BaseMapper<ScheduleCaseRelation> {
    
    /**
     * 查询日程关联的所有案件ID
     *
     * @param scheduleId 日程ID
     * @return 案件ID列表
     */
    List<Long> findCaseIdsByScheduleId(@Param("scheduleId") Long scheduleId);
    
    /**
     * 查询案件关联的所有日程ID
     *
     * @param caseId 案件ID
     * @return 日程ID列表
     */
    List<Long> findScheduleIdsByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 批量添加日程与案件关联
     *
     * @param relations 关联列表
     * @return 影响行数
     */
    int batchInsert(@Param("list") List<ScheduleCaseRelation> relations);
    
    /**
     * 根据日程ID删除所有关联
     *
     * @param scheduleId 日程ID
     * @return 影响行数
     */
    int deleteByScheduleId(@Param("scheduleId") Long scheduleId);
    
    /**
     * 根据案件ID删除所有关联
     *
     * @param caseId 案件ID
     * @return 影响行数
     */
    int deleteByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 删除特定的日程与案件关联
     *
     * @param scheduleId 日程ID
     * @param caseId 案件ID
     * @return 影响行数
     */
    int deleteByScheduleIdAndCaseId(@Param("scheduleId") Long scheduleId, @Param("caseId") Long caseId);
} 