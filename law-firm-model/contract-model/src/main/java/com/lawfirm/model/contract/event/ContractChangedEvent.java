package com.lawfirm.model.contract.event;

import com.lawfirm.model.contract.entity.Contract;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 合同变更事件
 */
@Getter
public class ContractChangedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private final Contract contract;
    private final String changeType;
    private final Long operatorId;
    private final String changeDesc;

    public ContractChangedEvent(Object source, Contract contract, String changeType, Long operatorId, String changeDesc) {
        super(source);
        this.contract = contract;
        this.changeType = changeType;
        this.operatorId = operatorId;
        this.changeDesc = changeDesc;
    }
} 