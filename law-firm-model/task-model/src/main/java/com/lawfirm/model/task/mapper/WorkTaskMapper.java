package com.lawfirm.model.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.task.constants.WorkTaskSqlConstants;
import com.lawfirm.model.task.entity.WorkTask;
import com.lawfirm.model.task.query.WorkTaskQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 工作任务Mapper接口
 */
@Mapper
public interface WorkTaskMapper extends BaseMapper<WorkTask> {
    
    /**
     * 根据查询条件获取任务列表
     *
     * @param query 查询条件
     * @return 任务列表
     */
    @Select(WorkTaskSqlConstants.Task.SELECT_TASKS)
    List<WorkTask> selectTasks(WorkTaskQuery query);
    
    /**
     * 根据用户ID获取待办任务数量
     *
     * @param userId 用户ID
     * @return 待办任务数量
     */
    @Select(WorkTaskSqlConstants.Task.SELECT_USER_TASK_COUNT)
    int selectUserTaskCount(Long userId);
    
    /**
     * 根据用户ID获取待办任务列表
     *
     * @param userId 用户ID
     * @return 待办任务列表
     */
    @Select(WorkTaskSqlConstants.Task.SELECT_USER_PENDING_TASKS)
    List<WorkTask> selectUserPendingTasks(Long userId);
    
    /**
     * 根据父任务ID获取子任务列表
     *
     * @param parentId 父任务ID
     * @return 子任务列表
     */
    @Select(WorkTaskSqlConstants.Task.SELECT_SUB_TASKS)
    List<WorkTask> selectSubTasks(Long parentId);
} 