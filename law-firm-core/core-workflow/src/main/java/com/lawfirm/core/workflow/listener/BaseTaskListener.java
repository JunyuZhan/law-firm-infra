package com.lawfirm.core.workflow.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.delegate.TaskListener;

/**
 * 任务监听器基类
 */
@Slf4j
public abstract class BaseTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        try {
            switch (eventName) {
                case EVENTNAME_CREATE:
                    onCreate(delegateTask);
                    break;
                case EVENTNAME_ASSIGNMENT:
                    onAssignment(delegateTask);
                    break;
                case EVENTNAME_COMPLETE:
                    onComplete(delegateTask);
                    break;
                case EVENTNAME_DELETE:
                    onDelete(delegateTask);
                    break;
                default:
                    log.warn("Unknown task event: {}", eventName);
            }
        } catch (Exception e) {
            log.error("Failed to handle task event: {}, taskId={}", eventName, delegateTask.getId(), e);
            throw e;
        }
    }

    /**
     * 任务创建事件处理
     */
    protected void onCreate(DelegateTask delegateTask) {
        // 默认空实现
    }

    /**
     * 任务分配事件处理
     */
    protected void onAssignment(DelegateTask delegateTask) {
        // 默认空实现
    }

    /**
     * 任务完成事件处理
     */
    protected void onComplete(DelegateTask delegateTask) {
        // 默认空实现
    }

    /**
     * 任务删除事件处理
     */
    protected void onDelete(DelegateTask delegateTask) {
        // 默认空实现
    }
} 