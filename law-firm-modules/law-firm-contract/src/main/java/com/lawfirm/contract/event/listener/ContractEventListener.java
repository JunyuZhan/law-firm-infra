package com.lawfirm.contract.event.listener;

import com.lawfirm.model.contract.event.ContractChangedEvent;
import com.lawfirm.model.contract.event.ContractCreatedEvent;
import com.lawfirm.model.contract.event.ContractExpiredEvent;
import com.lawfirm.model.contract.event.ContractReviewedEvent;
import com.lawfirm.model.contract.event.ContractStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 合同事件监听器
 * 负责处理合同相关的事件
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ContractEventListener {

    /**
     * 处理合同创建事件
     * 当合同创建时触发
     *
     * @param event 合同创建事件
     */
    @EventListener
    public void handleContractCreatedEvent(ContractCreatedEvent event) {
        log.info("接收到合同创建事件: 合同ID={}, 合同名称={}", 
                event.getContract().getId(), event.getContract().getContractName());
        
        // 1. 记录审计日志
        // 2. 发送创建通知
        // 3. 启动合同审批流程（如需要）
        // 4. 其他业务逻辑
    }

    /**
     * 处理合同审批事件
     * 当合同被审批时触发
     *
     * @param event 合同审批事件
     */
    @EventListener
    public void handleContractReviewedEvent(ContractReviewedEvent event) {
        log.info("接收到合同审批事件: 合同ID={}, 审批结果={}", 
                event.getContract().getId(), event.isApproved() ? "通过" : "拒绝");
        
        // 1. 更新合同状态
        // 2. 记录审批历史
        // 3. 发送审批结果通知
        // 4. 如果审批通过，执行后续流程
        // 5. 如果审批拒绝，中止流程并通知相关人员
    }

    /**
     * 处理合同状态变更事件
     * 当合同状态发生变化时触发
     *
     * @param event 合同状态变更事件
     */
    @EventListener
    public void handleContractStatusChangedEvent(ContractStatusChangedEvent event) {
        log.info("接收到合同状态变更事件: 合同ID={}, 状态变更: {} -> {}", 
                event.getContract().getId(), event.getOldStatus(), event.getNewStatus());
        
        // 1. 记录状态变更历史
        // 2. 发送状态变更通知
        // 3. 根据新状态执行不同的业务逻辑
        // 4. 如果状态为终止或到期，执行相应的清理工作
    }

    /**
     * 处理合同到期事件
     * 当合同到期时触发
     *
     * @param event 合同到期事件
     */
    @EventListener
    public void handleContractExpiredEvent(ContractExpiredEvent event) {
        log.info("接收到合同到期事件: 合同ID={}, 合同名称={}", 
                event.getContract().getId(), event.getContract().getContractName());
        
        // 1. 更新合同状态为已到期
        // 2. 发送到期通知
        // 3. 生成合同归档任务
        // 4. 如果需要续约，生成续约提醒
    }

    /**
     * 处理合同变更事件
     * 当合同信息变更时触发
     *
     * @param event 合同变更事件
     */
    @EventListener
    public void handleContractChangedEvent(ContractChangedEvent event) {
        log.info("接收到合同变更事件: 合同ID={}, 变更类型={}", 
                event.getContract().getId(), event.getChangeType());
        
        // 1. 记录变更历史
        // 2. 发送变更通知
        // 3. 如果是重要变更，可能需要重新走审批流程
        // 4. 根据变更类型执行不同的业务逻辑
    }
} 