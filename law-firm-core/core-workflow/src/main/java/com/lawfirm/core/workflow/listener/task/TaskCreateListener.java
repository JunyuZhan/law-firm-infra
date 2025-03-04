package com.lawfirm.core.workflow.listener.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

/**
 * 任务创建监听�? * 当流程引擎创建任务时触发
 *
 * @author JunyuZhan
 * @date 2023/03/03
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskCreateListener implements TaskListener {

    private static final long serialVersionUID = 1L;

    /**
     * 当任务被创建时触�?     *
     * @param delegateTask 任务委托对象
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("任务创建: ID={}, 名称={}, 处理�?{}", 
                delegateTask.getId(), 
                delegateTask.getName(),
                delegateTask.getAssignee());
        
        try {
            // 任务创建后的处理逻辑
            // 1. 可以在这里设置任务的截止日期、优先级�?            // 2. 可以记录任务创建日志
            // 3. 可以发送任务创建通知
            
            // 设置任务变量，记录创建时�?            delegateTask.setVariable("createTime", new java.util.Date());
            
            // 如果是特定类型的任务，可以进行特殊处�?            String taskType = (String) delegateTask.getVariable("taskType");
            if (taskType != null) {
                switch (taskType) {
                    case "urgent":
                        // 紧急任务设置高优先�?                        delegateTask.setPriority(100);
                        // 添加过期时间为当前时间后24小时
                        delegateTask.setDueDate(new java.util.Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
                        break;
                    case "normal":
                        // 普通任务设置中等优先级
                        delegateTask.setPriority(50);
                        // 添加过期时间为当前时间后72小时
                        delegateTask.setDueDate(new java.util.Date(System.currentTimeMillis() + 72 * 60 * 60 * 1000));
                        break;
                    default:
                        // 默认任务设置普通优先级
                        delegateTask.setPriority(0);
                        break;
                }
            }
            
        } catch (Exception e) {
            log.error("任务创建监听器处理异�?, e);
        }
    }
} 
