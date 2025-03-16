package com.lawfirm.model.contract.event;

import com.lawfirm.model.contract.entity.Contract;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 合同审批事件
 */
@Getter
public class ContractReviewedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private final Contract contract;
    private final Long reviewerId;
    private final boolean approved;
    private final String comment;

    public ContractReviewedEvent(Object source, Contract contract, Long reviewerId, boolean approved, String comment) {
        super(source);
        this.contract = contract;
        this.reviewerId = reviewerId;
        this.approved = approved;
        this.comment = comment;
    }
} 