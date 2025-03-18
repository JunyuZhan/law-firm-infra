package com.lawfirm.model.finance.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 财务事件基类
 * 所有财务相关事件的基类，提供通用的事件属性和方法
 */
public abstract class FinanceEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 事件类型
     */
    protected final String eventType;
    
    /**
     * 操作人ID
     */
    protected final Long operatorId;
    
    /**
     * 发生时间
     */
    protected final long timestamp;
    
    /**
     * 构造方法
     * 
     * @param source    事件源
     * @param eventType 事件类型
     * @param operatorId 操作人ID
     */
    public FinanceEvent(Object source, String eventType, Long operatorId) {
        super(source);
        this.eventType = eventType;
        this.operatorId = operatorId;
        this.timestamp = System.currentTimeMillis();
    }
    
    /**
     * 获取事件类型
     */
    public String getEventType() {
        return eventType;
    }
    
    /**
     * 获取操作人ID
     */
    public Long getOperatorId() {
        return operatorId;
    }
    
    /**
     * 获取事件发生时间戳
     */
    public long getEventTimestamp() {
        return timestamp;
    }
} 