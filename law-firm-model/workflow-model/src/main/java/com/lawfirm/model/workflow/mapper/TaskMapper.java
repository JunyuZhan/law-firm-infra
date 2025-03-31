package com.lawfirm.model.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.workflow.entity.base.ProcessTask;
import com.lawfirm.model.workflow.constant.WorkflowSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 任务Mapper接口
 * 负责ProcessTask实体的数据访问操作
 */
@Mapper
public interface TaskMapper extends BaseMapper<ProcessTask> {
    
    /**
     * 查询用户当前任务数量
     * 
     * @param userId 用户ID
     * @return 任务数量
     */
    @Select(WorkflowSqlConstants.Task.SELECT_USER_TASK_COUNT)
    int selectUserTaskCount(Long userId);
} 