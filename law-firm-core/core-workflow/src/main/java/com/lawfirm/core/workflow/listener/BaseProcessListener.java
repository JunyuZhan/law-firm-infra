package com.lawfirm.core.workflow.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;

/**
 * 流程监听器基类
 */
@Slf4j
public abstract class BaseProcessListener implements FlowableEventListener {
    
    @Override
    public void onEvent(FlowableEvent event) {
        try {
            handleEvent(event);
        } catch (Exception e) {
            log.error("Failed to handle process event: {}", event.getType(), e);
            throw e;
        }
    }
    
    /**
     * 处理流程事件
     */
    protected abstract void handleEvent(FlowableEvent event);
    
    @Override
    public boolean isFailOnException() {
        return true;
    }
    
    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }
    
    @Override
    public String getOnTransaction() {
        return null;
    }
} 