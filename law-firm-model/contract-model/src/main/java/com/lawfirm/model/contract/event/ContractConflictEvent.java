package com.lawfirm.model.contract.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 合同冲突事件
 */
@Getter
public class ContractConflictEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 合同ID
     */
    private final Long contractId;
    
    /**
     * 检查人ID
     */
    private final Long checkerId;
    
    /**
     * 检查类型
     */
    private final String checkType;
    
    /**
     * 事件类型
     */
    private final EventType eventType;
    
    public ContractConflictEvent(Object source, Long contractId, Long checkerId, String checkType, EventType eventType) {
        super(source);
        this.contractId = contractId;
        this.checkerId = checkerId;
        this.checkType = checkType;
        this.eventType = eventType;
    }
    
    /**
     * 事件类型枚举
     */
    public enum EventType {
        /**
         * 开始检查
         */
        CHECK_START,
        
        /**
         * 检查完成
         */
        CHECK_COMPLETE,
        
        /**
         * 发现冲突
         */
        CONFLICT_FOUND,
        
        /**
         * 冲突解决
         */
        CONFLICT_RESOLVED
    }
} 