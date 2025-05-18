package com.lawfirm.schedule.integration;

import com.lawfirm.model.task.dto.WorkTaskDTO;
import com.lawfirm.model.task.service.WorkTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 任务模块集成组件
 * 用于在日程模块中获取任务信息
 */
@Component("taskIntegration")
@RequiredArgsConstructor
@Slf4j
public class TaskIntegration {
    
    private final WorkTaskService workTaskService;
    
    /**
     * 获取任务信息
     *
     * @param taskId 任务ID
     * @return 任务信息
     */
    public WorkTaskDTO getTaskInfo(Long taskId) {
        if (taskId == null) {
            return null;
        }
        
        try {
            return workTaskService.getTaskDetail(taskId);
        } catch (Exception e) {
            log.error("获取任务信息失败，任务ID：{}", taskId, e);
            return null;
        }
    }
    
    /**
     * 批量获取任务信息
     *
     * @param taskIds 任务ID集合
     * @return 任务信息映射 (taskId -> WorkTaskDTO)
     */
    public Map<Long, WorkTaskDTO> getTasksInfo(Set<Long> taskIds) {
        log.info("批量获取任务信息，任务ID数量：{}", taskIds.size());
        
        if (taskIds.isEmpty()) {
            return Collections.emptyMap();
        }
        
        try {
            Map<Long, WorkTaskDTO> result = new HashMap<>();
            for (Long taskId : taskIds) {
                WorkTaskDTO taskDetail = workTaskService.getTaskDetail(taskId);
                if (taskDetail != null) {
                    result.put(taskId, taskDetail);
                }
            }
            return result;
        } catch (Exception e) {
            log.error("批量获取任务信息失败", e);
            return Collections.emptyMap();
        }
    }
    
    /**
     * 检查任务是否存在
     *
     * @param taskId 任务ID
     * @return 是否存在
     */
    public boolean taskExists(Long taskId) {
        log.info("检查任务是否存在，任务ID：{}", taskId);
        
        if (taskId == null) {
            return false;
        }
        
        try {
            WorkTaskDTO taskDetail = workTaskService.getTaskDetail(taskId);
            return taskDetail != null;
        } catch (Exception e) {
            log.error("检查任务是否存在失败，任务ID：{}", taskId, e);
            return false;
        }
    }
    
    /**
     * 获取任务负责人ID
     *
     * @param taskId 任务ID
     * @return 负责人ID
     */
    public Long getTaskAssigneeId(Long taskId) {
        if (taskId == null) {
            return null;
        }
        
        try {
            WorkTaskDTO taskDTO = workTaskService.getTaskDetail(taskId);
            return taskDTO != null ? taskDTO.getAssigneeId() : null;
        } catch (Exception e) {
            log.error("获取任务负责人失败，任务ID：{}", taskId, e);
            return null;
        }
    }
} 