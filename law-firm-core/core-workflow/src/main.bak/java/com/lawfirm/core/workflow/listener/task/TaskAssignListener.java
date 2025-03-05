package com.lawfirm.core.workflow.listener.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

/**
 * 任务分配监听�? * 当流程引擎中的任务被分配给用户时触发
 *
 * @author JunyuZhan
 * @date 2023/03/03
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskAssignListener implements TaskListener {

    private static final long serialVersionUID = 1L;

    /**
     * 当任务被分配时触�?     *
     * @param delegateTask 任务委托对象
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("任务分配: ID={}, 名称={}, 处理�?{}, 流程实例ID={}",
                delegateTask.getId(),
                delegateTask.getName(),
                delegateTask.getAssignee(),
                delegateTask.getProcessInstanceId());

        try {
            // 任务分配后的处理逻辑
            // 1. 可以在这里执行权限检�?            // 2. 可以记录任务分配历史
            // 3. 可以发送任务分配通知
            
            // 设置任务变量，记录分配时�?            delegateTask.setVariable("assignTime", new java.util.Date());
            
            // 如果有原处理人，记录转办历史
            Object oldAssignee = delegateTask.getVariable("oldAssignee");
            if (oldAssignee != null && !oldAssignee.equals(delegateTask.getAssignee())) {
                // 记录转办历史，可以是一个列�?                @SuppressWarnings("unchecked")
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
            
            // 更新旧处理人为当前处理人，用于下次转办记�?            delegateTask.setVariable("oldAssignee", delegateTask.getAssignee());
            
        } catch (Exception e) {
            log.error("任务分配监听器处理异�?, e);
        }
    }
} 
