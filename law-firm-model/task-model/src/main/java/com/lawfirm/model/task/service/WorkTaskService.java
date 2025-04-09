package com.lawfirm.model.task.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.task.dto.WorkTaskDTO;
import com.lawfirm.model.task.entity.WorkTask;
import com.lawfirm.model.task.query.WorkTaskQuery;

import java.util.List;
import java.util.Map;

/**
 * 工作任务服务接口
 */
public interface WorkTaskService extends BaseService<WorkTask> {
    
    /**
     * 创建工作任务
     *
     * @param taskDTO 工作任务DTO
     * @return 创建的工作任务ID
     */
    Long createTask(WorkTaskDTO taskDTO);
    
    /**
     * 更新工作任务
     *
     * @param taskDTO 工作任务DTO
     */
    void updateTask(WorkTaskDTO taskDTO);
    
    /**
     * 删除工作任务
     *
     * @param taskId 工作任务ID
     */
    void deleteTask(Long taskId);
    
    /**
     * 获取工作任务详情
     *
     * @param taskId 工作任务ID
     * @return 工作任务DTO
     */
    WorkTaskDTO getTaskDetail(Long taskId);
    
    /**
     * 查询工作任务列表
     *
     * @param query 查询条件
     * @return 工作任务列表
     */
    List<WorkTaskDTO> queryTaskList(WorkTaskQuery query);
    
    /**
     * 更新工作任务状态
     *
     * @param taskId 工作任务ID
     * @param status 状态
     */
    void updateTaskStatus(Long taskId, Integer status);
    
    /**
     * 更新工作任务优先级
     *
     * @param taskId 工作任务ID
     * @param priority 优先级
     */
    void updateTaskPriority(Long taskId, Integer priority);
    
    /**
     * 分配工作任务
     *
     * @param taskId 工作任务ID
     * @param assigneeId 被分配人ID
     */
    void assignTask(Long taskId, Long assigneeId);
    
    /**
     * 完成工作任务
     *
     * @param taskId 工作任务ID
     */
    void completeTask(Long taskId);
    
    /**
     * 取消工作任务
     *
     * @param taskId 工作任务ID
     */
    void cancelTask(Long taskId);
    
    /**
     * 获取任务统计信息
     *
     * @param query 查询条件
     * @return 任务统计信息
     */
    Map<String, Object> getTaskStatistics(WorkTaskQuery query);
} 