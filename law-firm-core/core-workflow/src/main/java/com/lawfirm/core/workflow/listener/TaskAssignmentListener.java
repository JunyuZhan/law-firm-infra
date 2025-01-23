package com.lawfirm.core.workflow.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 任务分配监听器
 */
@Slf4j
@Component
public class TaskAssignmentListener extends BaseTaskListener {

    private final MongoTemplate mongoTemplate;
    
    public TaskAssignmentListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    @Override
    protected void onAssignment(DelegateTask delegateTask) {
        // 记录任务分配信息
        Map<String, Object> assignmentInfo = new HashMap<>();
        assignmentInfo.put("taskId", delegateTask.getId());
        assignmentInfo.put("taskName", delegateTask.getName());
        assignmentInfo.put("processInstanceId", delegateTask.getProcessInstanceId());
        assignmentInfo.put("processDefinitionId", delegateTask.getProcessDefinitionId());
        assignmentInfo.put("assignee", delegateTask.getAssignee());
        assignmentInfo.put("owner", delegateTask.getOwner());
        assignmentInfo.put("assignmentTime", LocalDateTime.now());
        
        // 保存到MongoDB
        mongoTemplate.save(assignmentInfo, "task_assignments");
        
        log.info("Task assigned: taskId={}, assignee={}", delegateTask.getId(), delegateTask.getAssignee());
    }
    
    @Override
    protected void onCreate(DelegateTask delegateTask) {
        // 设置默认到期时间为3天后
        delegateTask.setDueDate(java.util.Date.from(
                LocalDateTime.now().plusDays(3)
                        .atZone(java.time.ZoneId.systemDefault())
                        .toInstant()));
        
        log.info("Task created with due date: taskId={}, dueDate={}", 
                delegateTask.getId(), delegateTask.getDueDate());
    }
} 