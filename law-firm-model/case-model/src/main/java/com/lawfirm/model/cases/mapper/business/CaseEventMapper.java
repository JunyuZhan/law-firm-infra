package com.lawfirm.model.cases.mapper.business;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.business.CaseEvent;
import com.lawfirm.model.cases.constants.CaseSqlConstants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 案件事件Mapper接口
 */
public interface CaseEventMapper extends BaseMapper<CaseEvent> {
    
    /**
     * 根据案件ID查询事件
     *
     * @param caseId 案件ID
     * @return 事件列表
     */
    @Select(CaseSqlConstants.Event.SELECT_BY_CASE_ID)
    List<CaseEvent> selectByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 根据事件类型查询
     *
     * @param caseId 案件ID
     * @param eventType 事件类型
     * @return 事件列表
     */
    @Select(CaseSqlConstants.Event.SELECT_BY_TYPE)
    List<CaseEvent> selectByType(@Param("caseId") Long caseId, @Param("eventType") Integer eventType);
    
    /**
     * 查询指定时间范围内的事件
     *
     * @param caseId 案件ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 事件列表
     */
    @Select(CaseSqlConstants.Event.SELECT_BY_TIME_RANGE)
    List<CaseEvent> selectByTimeRange(@Param("caseId") Long caseId, 
                                     @Param("startTime") Date startTime, 
                                     @Param("endTime") Date endTime);
} 