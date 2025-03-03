package com.lawfirm.core.workflow.adapter.converter;

import com.lawfirm.model.workflow.dto.task.TaskCreateDTO;
import com.lawfirm.model.workflow.entity.Task;
import com.lawfirm.model.workflow.enums.TaskPriorityEnum;
import com.lawfirm.model.workflow.enums.TaskStatusEnum;
import com.lawfirm.model.workflow.enums.TaskTypeEnum;
import com.lawfirm.model.workflow.vo.TaskVO;
import lombok.RequiredArgsConstructor;
import org.flowable.task.api.TaskInfo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 任务数据转换器
 * 
 * @author claude
 */
@Component
@RequiredArgsConstructor
public class TaskConverter {

    /**
     * 将创建DTO转换为实体
     * 
     * @param createDTO 创建DTO
     * @return 实体对象
     */
    public Task toEntity(TaskCreateDTO createDTO) {
        if (createDTO == null) {
            return null;
        }
        
        Task task = new Task();
        task.setTaskName(createDTO.getTaskName())
            .setTaskType(createDTO.getTaskType())
            .setDescription(createDTO.getDescription())
            .setProcessInstanceId(createDTO.getProcessInstanceId())
            .setHandlerId(createDTO.getHandlerId())
            .setHandlerName(createDTO.getHandlerName())
            .setPriority(createDTO.getPriority())
            .setDueDate(createDTO.getDueDate() != null ? 
                LocalDateTime.ofInstant(createDTO.getDueDate().toInstant(), ZoneId.systemDefault()) : null);
        
        return task;
    }
    
    /**
     * 将Flowable任务转换为实体
     * 
     * @param flowableTask Flowable任务
     * @return 实体对象
     */
    public Task fromFlowableTask(TaskInfo flowableTask) {
        if (flowableTask == null) {
            return null;
        }
        
        Task task = new Task();
        task.setId(Long.valueOf(flowableTask.getId()));
        task.setTaskName(flowableTask.getName());
        task.setDescription(flowableTask.getDescription());
        task.setProcessInstanceId(flowableTask.getProcessInstanceId());
        task.setHandlerId(Long.valueOf(flowableTask.getAssignee()));
        task.setPriority(flowableTask.getPriority());
        task.setDueDate(flowableTask.getDueDate() != null ? 
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
    public TaskVO toVO(Task task) {
        if (task == null) {
            return null;
        }
        
        TaskVO vo = new TaskVO();
        vo.setId(task.getId())
          .setTaskName(task.getTaskName())
          .setTaskType(TaskTypeEnum.getByValue(task.getTaskType()))
          .setDescription(task.getDescription())
          .setStatus(TaskStatusEnum.getByValue(task.getStatus()))
          .setProcessInstanceId(task.getProcessInstanceId())
          .setHandlerId(task.getHandlerId())
          .setHandlerName(task.getHandlerName())
          .setPriority(TaskPriorityEnum.getByValue(task.getPriority()))
          .setDueDate(task.getDueDate())
          .setResult(task.getResult())
          .setComment(task.getComment());
        
        return vo;
    }
} 