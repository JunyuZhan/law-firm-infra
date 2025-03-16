package com.lawfirm.model.contract.event;

import com.lawfirm.model.contract.entity.Contract;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 合同创建事件
 */
@Getter
public class ContractCreatedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private final Contract contract;
    private final String source;
    private final Long creatorId;

    public ContractCreatedEvent(Object source, Contract contract, String eventSource, Long creatorId) {
        super(source);
        this.contract = contract;
        this.source = eventSource;
        this.creatorId = creatorId;
    }
} 