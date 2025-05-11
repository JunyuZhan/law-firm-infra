package com.lawfirm.core.workflow.listener.task;

import com.lawfirm.model.workflow.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 任务创建监听器
 *
 * @author JunyuZhan
 */
@Slf4j
@Component("coreTaskCreateListener")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "law-firm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class TaskCreateListener implements TaskListener {

    private static final long serialVersionUID = 1L;

    private transient final TaskService taskService;

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("任务创建事件触发: taskId={}, taskName={}, assignee={}", 
            delegateTask.getId(), delegateTask.getName(), delegateTask.getAssignee());

        // 获取任务相关信息
        String taskId = delegateTask.getId();
        String taskName = delegateTask.getName();
        String assignee = delegateTask.getAssignee();
        String processInstanceId = delegateTask.getProcessInstanceId();

        // 如果有指定处理人，发送任务通知
        if (assignee != null) {
            taskService.sendTaskCreatedNotification(taskId, taskName, assignee, null);
        }

        // 记录任务创建日志
        log.info("任务已创建: taskId={}, taskName={}, assignee={}, processInstanceId={}", 
            taskId, taskName, assignee, processInstanceId);
    }
} 
