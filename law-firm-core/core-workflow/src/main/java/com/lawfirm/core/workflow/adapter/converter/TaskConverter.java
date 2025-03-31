package com.lawfirm.core.workflow.adapter.converter;

import com.lawfirm.model.workflow.dto.task.TaskCreateDTO;
import com.lawfirm.model.workflow.entity.base.ProcessTask;
import com.lawfirm.model.workflow.enums.TaskPriorityEnum;
import com.lawfirm.model.workflow.enums.TaskStatusEnum;
import com.lawfirm.model.workflow.enums.TaskTypeEnum;
import com.lawfirm.model.workflow.vo.TaskVO;
import lombok.RequiredArgsConstructor;
import org.flowable.task.api.TaskInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 任务数据转换器
 * 
 * @author JunyuZhan
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lawfirm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class TaskConverter {

    /**
     * 将创建DTO转换为实体
     * 
     * @param createDTO 创建DTO
     * @return 实体对象
     */
    public ProcessTask toEntity(TaskCreateDTO createDTO) {
        if (createDTO == null) {
            return null;
        }
        
        ProcessTask task = new ProcessTask();
        task.setTaskName(createDTO.getName())
            .setTaskType(createDTO.getType())
            .setDescription(createDTO.getDescription())
            .setProcessId(createDTO.getProcessId() != null ? Long.valueOf(createDTO.getProcessId()) : null)
            .setProcessNo(createDTO.getProcessNo())
            .setHandlerId(createDTO.getAssigneeId() != null ? Long.valueOf(createDTO.getAssigneeId()) : null)
            .setHandlerName(createDTO.getAssigneeName())
            .setPriority(createDTO.getPriority())
            .setDueTime(createDTO.getDueDate());
        
        return task;
    }
    
    /**
     * 将Flowable任务转换为实体
     * 
     * @param flowableTask Flowable任务
     * @return 实体对象
     */
    public ProcessTask fromFlowableTask(TaskInfo flowableTask) {
        if (flowableTask == null) {
            return null;
        }
        
        ProcessTask task = new ProcessTask();
        task.setId(Long.valueOf(flowableTask.getId()));
        task.setTaskName(flowableTask.getName());
        task.setDescription(flowableTask.getDescription());
        task.setProcessNo(flowableTask.getProcessInstanceId());
        task.setHandlerId(flowableTask.getAssignee() != null ? Long.valueOf(flowableTask.getAssignee()) : null);
        task.setPriority(flowableTask.getPriority());
        task.setDueTime(flowableTask.getDueDate() != null ? 
            LocalDateTime.ofInstant(flowableTask.getDueDate().toInstant(), ZoneId.systemDefault()) : null);
        task.setCreateTime(flowableTask.getCreateTime() != null ? 
            LocalDateTime.ofInstant(flowableTask.getCreateTime().toInstant(), ZoneId.systemDefault()) : null);
        
        return task;
    }
    
    /**
     * 将实体转换为视图对象
     * 
     * @param task 实体对象
     * @return 视图对象
     */
    public TaskVO toVO(ProcessTask task) {
        if (task == null) {
            return null;
        }
        
        TaskVO vo = new TaskVO();
        vo.setId(task.getId())
          .setTaskName(task.getTaskName())
          .setTaskType(TaskTypeEnum.getByValue(task.getTaskType()))
          .setDescription(task.getDescription())
          .setStatus(TaskStatusEnum.getByValue(task.getStatus()))
          .setProcessId(task.getProcessId())
          .setProcessNo(task.getProcessNo())
          .setHandlerId(task.getHandlerId())
          .setHandlerName(task.getHandlerName())
          .setPriority(TaskPriorityEnum.getByValue(task.getPriority()))
          .setDueDate(task.getDueTime())
          .setResult(task.getResult())
          .setComment(task.getComment());
        
        return vo;
    }
} 
