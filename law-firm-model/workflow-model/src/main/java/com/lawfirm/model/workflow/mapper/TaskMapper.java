package com.lawfirm.model.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.workflow.entity.base.ProcessTask;
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
    @Select("SELECT COUNT(*) FROM workflow_task WHERE handler_id = #{userId} AND status = 0")
    int selectUserTaskCount(Long userId);
} 