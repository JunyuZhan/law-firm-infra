package com.lawfirm.finance.event.listener;

import com.lawfirm.model.finance.event.TransactionCreatedEvent;
import com.lawfirm.model.finance.event.InvoiceIssuedEvent;
import com.lawfirm.model.finance.event.PaymentReceivedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 财务事件监听器
 * 负责处理财务相关的事件
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FinanceEventListener {

    /**
     * 处理交易创建事件
     *
     * @param event 交易创建事件
     */
    @Order(1)
    @EventListener
    public void handleTransactionCreatedEvent(TransactionCreatedEvent event) {
        log.info("接收到交易创建事件: 交易ID={}, 金额={}, 类型={}", 
                event.getTransaction().getId(), 
                event.getTransaction().getAmount(),
                event.getTransaction().getTransactionType());
        
        try {
            // 1. 记录审计日志
            // 2. 更新账户余额
            // 3. 同步相关业务数据
            // 4. 其他处理逻辑
            log.info("交易创建事件处理成功: 交易ID={}", event.getTransaction().getId());
        } catch (Exception e) {
            log.error("交易创建事件处理失败: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 处理发票开具事件
     *
     * @param event 发票开具事件
     */
    @Order(1)
    @EventListener
    public void handleInvoiceIssuedEvent(InvoiceIssuedEvent event) {
        log.info("接收到发票开具事件: 发票ID={}, 发票号={}, 金额={}", 
                event.getInvoice().getId(), 
                event.getInvoice().getInvoiceNumber(),
                event.getInvoice().getAmount());
        
        try {
            // 1. 记录审计日志
            // 2. 更新发票状态
            // 3. 发送通知
            // 4. 其他处理逻辑
            log.info("发票开具事件处理成功: 发票ID={}", event.getInvoice().getId());
        } catch (Exception e) {
            log.error("发票开具事件处理失败: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 处理付款接收事件
     *
     * @param event 付款接收事件
     */
    @Async("financeEventExecutor")
    @Order(1)
    @EventListener
    public void handlePaymentReceivedEvent(PaymentReceivedEvent event) {
        log.info("接收到付款接收事件: 收入ID={}, 金额={}, 付款方={}", 
                event.getIncome().getId(), 
                event.getAmount(),
                event.getPayerName());
        
        try {
            // 1. 记录审计日志
            // 2. 更新账户余额
            // 3. 更新应收账款状态
            // 4. 通知相关业务系统（如合同或案件）
            // 5. 其他处理逻辑
            
            if (event.getBusinessId() != null && "CONTRACT".equals(event.getBusinessType())) {
                // 更新合同收款状态
                log.info("更新合同收款状态: 合同ID={}", event.getBusinessId());
            } else if (event.getBusinessId() != null && "CASE".equals(event.getBusinessType())) {
                // 更新案件收款状态
                log.info("更新案件收款状态: 案件ID={}", event.getBusinessId());
            }
            
            log.info("付款接收事件处理成功: 收入ID={}", event.getIncome().getId());
        } catch (Exception e) {
            log.error("付款接收事件处理失败: {}", e.getMessage(), e);
            // 可以考虑重试机制或发送告警
        }
    }
} 