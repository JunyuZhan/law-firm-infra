package com.lawfirm.model.contract.event;

import com.lawfirm.model.contract.entity.Contract;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 合同到期事件
 */
@Getter
public class ContractExpiredEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private final Contract contract;

    public ContractExpiredEvent(Object source, Contract contract) {
        super(source);
        this.contract = contract;
    }
} 