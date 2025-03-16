package com.lawfirm.contract.event.publisher;

import com.lawfirm.model.contract.entity.Contract;
import com.lawfirm.model.contract.event.ContractChangedEvent;
import com.lawfirm.model.contract.event.ContractCreatedEvent;
import com.lawfirm.model.contract.event.ContractExpiredEvent;
import com.lawfirm.model.contract.event.ContractReviewedEvent;
import com.lawfirm.model.contract.event.ContractStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 合同事件发布器
 * 负责发布合同相关的事件
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ContractEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 发布合同创建事件
     *
     * @param contract 合同信息
     * @param source   事件来源
     * @param creatorId 创建人ID
     */
    public void publishContractCreatedEvent(Contract contract, String source, Long creatorId) {
        log.debug("发布合同创建事件: {}, 来源: {}, 创建人: {}", contract.getId(), source, creatorId);
        ContractCreatedEvent event = new ContractCreatedEvent(this, contract, source, creatorId);
        eventPublisher.publishEvent(event);
    }

    /**
     * 发布合同创建事件（默认系统来源）
     *
     * @param contract 合同信息
     */
    public void publishContractCreatedEvent(Contract contract) {
        publishContractCreatedEvent(contract, "SYSTEM", null);
    }

    /**
     * 发布合同审批事件
     *
     * @param contract    合同信息
     * @param reviewerId  审批人ID
     * @param approved    是否通过
     * @param comment     审批意见
     */
    public void publishContractReviewedEvent(Contract contract, Long reviewerId, boolean approved, String comment) {
        log.debug("发布合同审批事件: {}, 审批人: {}, 结果: {}", contract.getId(), reviewerId, approved ? "通过" : "拒绝");
        ContractReviewedEvent event = new ContractReviewedEvent(this, contract, reviewerId, approved, comment);
        eventPublisher.publishEvent(event);
    }

    /**
     * 发布合同状态变更事件
     *
     * @param contract  合同信息
     * @param oldStatus 旧状态
     * @param newStatus 新状态
     * @param operatorId 操作人ID
     */
    public void publishContractStatusChangedEvent(Contract contract, Integer oldStatus, Integer newStatus, Long operatorId) {
        log.debug("发布合同状态变更事件: {}, 状态变更: {} -> {}, 操作人: {}", 
                 contract.getId(), oldStatus, newStatus, operatorId);
        ContractStatusChangedEvent event = new ContractStatusChangedEvent(this, contract, oldStatus, newStatus, operatorId);
        eventPublisher.publishEvent(event);
    }
    
    /**
     * 发布合同到期事件
     *
     * @param contract  合同信息
     */
    public void publishContractExpiredEvent(Contract contract) {
        log.debug("发布合同到期事件: {}, 合同名称: {}", contract.getId(), contract.getContractName());
        ContractExpiredEvent event = new ContractExpiredEvent(this, contract);
        eventPublisher.publishEvent(event);
    }
    
    /**
     * 发布合同变更事件
     *
     * @param contract   合同信息
     * @param changeType 变更类型
     * @param operatorId 操作人ID
     * @param changeDesc 变更描述
     */
    public void publishContractChangedEvent(Contract contract, String changeType, Long operatorId, String changeDesc) {
        log.debug("发布合同变更事件: {}, 变更类型: {}, 操作人: {}", contract.getId(), changeType, operatorId);
        ContractChangedEvent event = new ContractChangedEvent(this, contract, changeType, operatorId, changeDesc);
        eventPublisher.publishEvent(event);
    }
} 