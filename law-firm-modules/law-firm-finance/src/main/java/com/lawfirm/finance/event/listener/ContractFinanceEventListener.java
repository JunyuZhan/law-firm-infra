package com.lawfirm.finance.event.listener;

import com.lawfirm.model.contract.event.ContractCreatedEvent;
import com.lawfirm.model.contract.event.ContractChangedEvent;
import com.lawfirm.model.contract.entity.Contract;
import com.lawfirm.model.finance.service.ContractFinanceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 合同财务事件监听器
 * 负责监听合同模块的事件，处理与财务相关的业务
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ContractFinanceEventListener {

    private final Logger logger = LoggerFactory.getLogger(ContractFinanceEventListener.class);
    private final ContractFinanceService contractFinanceService;
    
    /**
     * 监听合同创建事件，创建合同相关的财务记录
     *
     * @param event 合同创建事件
     */
    @Async("financeEventExecutor")
    @EventListener
    public void handleContractCreatedEvent(ContractCreatedEvent event) {
        Contract contract = event.getContract();
        logger.info("接收到合同创建事件: 合同ID={}, 合同编号={}, 合同金额={}", 
                contract.getId(), contract.getContractNo(), contract.getAmount());
                
        try {
            // 创建合同应收账款
            if (contract.getAmount() != null && contract.getAmount() > 0) {
                BigDecimal amount = BigDecimal.valueOf(contract.getAmount());
                Long receivableId = contractFinanceService.createContractReceivable(
                    contract.getId(), 
                    contract.getContractNo(), 
                    amount, 
                    contract.getClientId()
                );
                
                logger.info("为合同创建应收账款成功: 合同ID={}, 应收账款ID={}", contract.getId(), receivableId);
                
                // 创建合同收款计划
                if (contract.getPaymentTerms() != null) {
                    Long paymentPlanId = contractFinanceService.createPaymentPlan(
                        contract.getId(),
                        contract.getContractNo(),
                        "合同收款计划",
                        amount,
                        getInstallments(contract),
                        contract.getClientId()
                    );
                    
                    logger.info("为合同创建收款计划成功: 合同ID={}, 收款计划ID={}", contract.getId(), paymentPlanId);
                }
            }
        } catch (Exception e) {
            logger.error("处理合同创建事件失败: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 监听合同变更事件，更新合同相关的财务记录
     *
     * @param event 合同变更事件
     */
    @Async("financeEventExecutor")
    @EventListener
    public void handleContractChangedEvent(ContractChangedEvent event) {
        Contract contract = event.getContract();
        String changeType = event.getChangeType();
        
        logger.info("接收到合同变更事件: 合同ID={}, 变更类型={}", contract.getId(), changeType);
        
        try {
            // 处理合同金额变更
            if ("AMOUNT_CHANGE".equals(changeType) && contract.getAmount() != null) {
                BigDecimal amount = BigDecimal.valueOf(contract.getAmount());
                boolean updated = contractFinanceService.updateContractReceivable(
                    contract.getId(),
                    contract.getContractNo(),
                    amount
                );
                
                logger.info("更新合同应收账款: 合同ID={}, 更新结果={}", contract.getId(), updated ? "成功" : "失败");
            }
            
            // 处理其他类型的变更...
        } catch (Exception e) {
            logger.error("处理合同变更事件失败: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 获取分期数
     *
     * @param contract 合同信息
     * @return 分期数
     */
    private Integer getInstallments(Contract contract) {
        // 根据合同付款条款确定分期数
        String paymentTerms = contract.getPaymentTerms();
        
        if (paymentTerms == null || paymentTerms.isEmpty()) {
            return 1; // 默认一次性付款
        }
        
        // 这里简化处理，实际应该根据具体业务逻辑解析paymentTerms
        if (paymentTerms.contains("分期") || paymentTerms.contains("installment")) {
            // 简单判断，实际应该通过正则表达式等方式提取具体分期数
            if (paymentTerms.contains("2期") || paymentTerms.contains("两期")) {
                return 2;
            } else if (paymentTerms.contains("3期") || paymentTerms.contains("三期")) {
                return 3;
            } else if (paymentTerms.contains("4期") || paymentTerms.contains("四期")) {
                return 4;
            }
            return 2; // 默认分2期
        }
        
        return 1;
    }
} 