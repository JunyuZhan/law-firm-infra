package com.lawfirm.model.finance.event;

import com.lawfirm.model.finance.entity.Transaction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 交易创建事件
 * 当新的交易被创建时触发
 */
@Getter
public class TransactionCreatedEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 交易信息
     */
    private final Transaction transaction;
    
    /**
     * 操作人ID
     */
    private final Long operatorId;
    
    /**
     * 事件来源
     */
    private final String source;
    
    /**
     * 构造方法
     * 
     * @param source      事件源
     * @param transaction 交易信息
     * @param eventSource 事件来源
     * @param operatorId  操作人ID
     */
    public TransactionCreatedEvent(Object source, Transaction transaction, String eventSource, Long operatorId) {
        super(source);
        this.transaction = transaction;
        this.source = eventSource;
        this.operatorId = operatorId;
    }
} 