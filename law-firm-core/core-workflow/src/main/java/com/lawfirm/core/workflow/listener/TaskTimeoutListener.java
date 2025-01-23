package com.lawfirm.core.workflow.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 任务超时监听器
 */
@Slf4j
@Component
public class TaskTimeoutListener extends BaseTaskListener {
    
    @Override
    protected void onCreate(DelegateTask delegateTask) {
        // 设置任务到期时间为3天后
        LocalDateTime dueDate = LocalDateTime.now().plusDays(3);
        delegateTask.setDueDate(Date.from(dueDate.atZone(ZoneId.systemDefault()).toInstant()));
        
        log.info("Task due date set: taskId={}, dueDate={}", delegateTask.getId(), dueDate);
    }
    
    @Override
    protected void onComplete(DelegateTask delegateTask) {
        // 检查任务是否超时
        Date dueDate = delegateTask.getDueDate();
        if (dueDate != null && dueDate.before(new Date())) {
            log.warn("Task completed after due date: taskId={}, dueDate={}, completionTime={}",
                    delegateTask.getId(), dueDate, new Date());
            
            // 记录超时信息
            delegateTask.setVariable("timeout", true);
            delegateTask.setVariable("timeoutDuration",
                    System.currentTimeMillis() - dueDate.getTime());
        }
    }
} 