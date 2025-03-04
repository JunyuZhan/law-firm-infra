package com.lawfirm.core.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.workflow.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务Mapper接口
 * 基于MyBatis Plus提供任务的数据库访问
 *
 * @author JunyuZhan
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {
    
    /**
     * 根据流程实例ID查询任务列表
     * 
     * @param processInstanceId 流程实例ID
     * @return 任务列表
     */
    List<Task> selectByProcessInstanceId(@Param("processInstanceId") String processInstanceId);
    
    /**
     * 根据处理人ID查询待办任务
     * 
     * @param handlerId 处理人ID
     * @return 任务列表
     */
    List<Task> selectTodoTasksByHandlerId(@Param("handlerId") Long handlerId);
    
    /**
     * 根据处理人ID查询已办任务
     * 
     * @param handlerId 处理人ID
     * @return 任务列表
     */
    List<Task> selectDoneTasksByHandlerId(@Param("handlerId") Long handlerId);
    
    /**
     * 获取用户当前任务数量
     * 
     * @param userId 用户ID
     * @return 任务数量
     */
    int selectUserTaskCount(@Param("userId") Long userId);
} 
