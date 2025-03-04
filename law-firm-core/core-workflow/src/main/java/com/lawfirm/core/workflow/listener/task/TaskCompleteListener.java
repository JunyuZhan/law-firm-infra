package com.lawfirm.core.workflow.listener.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

/**
 * 任务完成监听�? * 当流程引擎中的任务完成时触发
 *
 * @author JunyuZhan
 * @date 2023/03/03
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskCompleteListener implements TaskListener {

    private static final long serialVersionUID = 1L;

    /**
     * 当任务被完成时触�?     *
     * @param delegateTask 任务委托对象
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("任务完成: ID={}, 名称={}, 处理�?{}, 流程实例ID={}",
                delegateTask.getId(),
                delegateTask.getName(),
                delegateTask.getAssignee(),
                delegateTask.getProcessInstanceId());

        try {
            // 任务完成后的处理逻辑
            // 1. 可以在这里记录任务完成的相关数据
            // 2. 可以更新相关业务状�?            // 3. 可以发送任务完成通知
            
            // 设置任务变量，记录完成时�?            delegateTask.setVariable("completeTime", new java.util.Date());
            
            // 获取任务结果和处理意见（如果有）
            Object result = delegateTask.getVariable("result");
            Object comment = delegateTask.getVariable("comment");
            
            if (result != null) {
                log.info("任务结果: {}", result);
            }
            
            if (comment != null) {
                log.info("处理意见: {}", comment);
            }
            
            // 计算任务处理时长（毫秒）
            java.util.Date createTime = (java.util.Date) delegateTask.getVariable("createTime");
            if (createTime != null) {
                long duration = System.currentTimeMillis() - createTime.getTime();
                delegateTask.setVariable("taskDuration", duration);
                log.info("任务处理时长: {}毫秒", duration);
            }
            
        } catch (Exception e) {
            log.error("任务完成监听器处理异�?, e);
        }
    }
} 
