package com.lawfirm.model.cases.mapper.business;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.business.CaseTask;
import com.lawfirm.model.cases.constants.CaseSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 案件任务Mapper接口
 */
@Mapper
public interface CaseTaskMapper extends BaseMapper<CaseTask> {
    
    /**
     * 根据案件ID查询任务
     *
     * @param caseId 案件ID
     * @return 任务列表
     */
    @Select(CaseSqlConstants.Task.SELECT_BY_CASE_ID)
    List<CaseTask> selectByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 根据执行人查询任务
     *
     * @param executorId 执行人ID
     * @return 任务列表
     */
    @Select(CaseSqlConstants.Task.SELECT_BY_EXECUTOR)
    List<CaseTask> selectByExecutor(@Param("executorId") Long executorId);
    
    /**
     * 查询未完成的任务
     *
     * @param caseId 案件ID
     * @return 未完成任务列表
     */
    @Select(CaseSqlConstants.Task.SELECT_UNFINISHED)
    List<CaseTask> selectUnfinished(@Param("caseId") Long caseId);
} 