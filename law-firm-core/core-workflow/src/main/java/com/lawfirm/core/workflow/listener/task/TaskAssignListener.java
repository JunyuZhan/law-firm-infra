package com.lawfirm.core.workflow.listener.task;

import com.lawfirm.model.workflow.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

/**
 * 任务分配监听器
 *
 * @author JunyuZhan
 * @date 2023/03/03
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskAssignListener implements TaskListener {

    private static final long serialVersionUID = 1L;

    private transient final TaskService taskService;

    /**
     * 当任务被分配时触发
     *
     * @param delegateTask 任务委托对象
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("任务分配事件触发: taskId={}, taskName={}, assignee={}", 
            delegateTask.getId(), delegateTask.getName(), delegateTask.getAssignee());

        // 获取任务相关信息
        String taskId = delegateTask.getId();
        String taskName = delegateTask.getName();
        String assignee = delegateTask.getAssignee();
        String oldAssignee = delegateTask.getVariable("oldAssignee", String.class);
        String processInstanceId = delegateTask.getProcessInstanceId();

        // 如果有指定处理人，发送任务通知
        if (assignee != null) {
            taskService.sendTaskAssignedNotification(taskId, taskName, assignee, oldAssignee, null);
        }

        // 记录任务分配日志
        log.info("任务已分配: taskId={}, taskName={}, assignee={}, oldAssignee={}, processInstanceId={}", 
            taskId, taskName, assignee, oldAssignee, processInstanceId);

        try {
            // 任务分配后的处理逻辑
            // 1. 可以在这里执行权限检查
            // 2. 可以记录任务分配历史
            // 3. 可以发送任务分配通知
            
            // 设置任务变量，记录分配时间
            delegateTask.setVariable("assignTime", new java.util.Date());
            
            // 如果有原处理人，记录转办历史
            if (oldAssignee != null && !oldAssignee.equals(delegateTask.getAssignee())) {
                // 记录转办历史，可以是一个列表
                @SuppressWarnings("unchecked")
                java.util.List<String> assignHistory = 
                        (java.util.List<String>) delegateTask.getVariable("assignHistory");
                
                if (assignHistory == null) {
                    assignHistory = new java.util.ArrayList<>();
                }
                
                // 添加转办记录
                assignHistory.add(String.format("%s -> %s (%s)", 
                        oldAssignee, 
                        delegateTask.getAssignee(),
                        new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())));
                
                // 更新转办历史变量
                delegateTask.setVariable("assignHistory", assignHistory);
            }
            
            // 更新旧处理人为当前处理人，用于下次转办记录
            delegateTask.setVariable("oldAssignee", delegateTask.getAssignee());
            
        } catch (Exception e) {
            log.error("任务分配监听器处理异常, e");
        }
    }
} 
