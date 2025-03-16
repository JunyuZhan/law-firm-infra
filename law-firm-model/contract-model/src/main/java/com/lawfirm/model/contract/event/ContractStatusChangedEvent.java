package com.lawfirm.model.contract.event;

import com.lawfirm.model.contract.entity.Contract;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 合同状态变更事件
 */
@Getter
public class ContractStatusChangedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private final Contract contract;
    private final Integer oldStatus;
    private final Integer newStatus;
    private final Long operatorId;

    public ContractStatusChangedEvent(Object source, Contract contract, Integer oldStatus, Integer newStatus, Long operatorId) {
        super(source);
        this.contract = contract;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.operatorId = operatorId;
    }
} 