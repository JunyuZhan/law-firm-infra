package com.lawfirm.finance.event.publisher;

import com.lawfirm.model.finance.entity.Transaction;
import com.lawfirm.model.finance.entity.Invoice;
import com.lawfirm.model.finance.entity.Income;
import com.lawfirm.model.finance.entity.Account;
import com.lawfirm.model.finance.entity.Budget;
import com.lawfirm.model.finance.event.TransactionCreatedEvent;
import com.lawfirm.model.finance.event.InvoiceIssuedEvent;
import com.lawfirm.model.finance.event.PaymentReceivedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 财务事件发布器
 * 负责发布财务相关的各类事件
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FinanceEventPublisher {

    private final Logger logger = LoggerFactory.getLogger(FinanceEventPublisher.class);
    private final ApplicationEventPublisher eventPublisher;
    
    /**
     * 发布交易创建事件
     *
     * @param transaction 交易信息
     * @param source      事件来源
     * @param operatorId  操作人ID
     */
    public void publishTransactionCreatedEvent(Transaction transaction, String source, Long operatorId) {
        logger.debug("发布交易创建事件: transactionId={}, amount={}, 来源={}", 
                  transaction.getId(), transaction.getAmount(), source);
                  
        TransactionCreatedEvent event = new TransactionCreatedEvent(this, transaction, source, operatorId);
        eventPublisher.publishEvent(event);
    }
    
    /**
     * 发布交易创建事件（简化版，使用系统作为来源）
     *
     * @param transaction 交易信息
     */
    public void publishTransactionCreatedEvent(Transaction transaction) {
        publishTransactionCreatedEvent(transaction, "SYSTEM", null);
    }
    
    /**
     * 发布发票开具事件
     *
     * @param invoice    发票信息
     * @param operatorId 操作人ID
     * @param remark     备注信息
     */
    public void publishInvoiceIssuedEvent(Invoice invoice, Long operatorId, String remark) {
        logger.debug("发布发票开具事件: invoiceId={}, invoiceNo={}, amount={}", 
                 invoice.getId(), invoice.getInvoiceNumber(), invoice.getAmount());
                 
        InvoiceIssuedEvent event = new InvoiceIssuedEvent(this, invoice, operatorId, remark);
        eventPublisher.publishEvent(event);
    }
    
    /**
     * 发布付款接收事件
     *
     * @param income       收入信息
     * @param payerId      付款方ID
     * @param payerName    付款方名称
     * @param businessId   关联业务ID
     * @param businessType 业务类型
     * @param operatorId   操作人ID
     */
    public void publishPaymentReceivedEvent(Income income, Long payerId, String payerName, 
                                          Long businessId, String businessType, Long operatorId) {
        logger.debug("发布付款接收事件: incomeId={}, amount={}, 付款方={}", 
                 income.getId(), income.getAmount(), payerName);
                 
        PaymentReceivedEvent event = new PaymentReceivedEvent(
            this, income, payerId, payerName, businessId, businessType, operatorId);
        eventPublisher.publishEvent(event);
    }
    
    /**
     * 发布付款接收事件（简化版）
     *
     * @param income     收入信息
     * @param payerId    付款方ID
     * @param payerName  付款方名称
     */
    public void publishPaymentReceivedEvent(Income income, Long payerId, String payerName) {
        publishPaymentReceivedEvent(income, payerId, payerName, null, null, null);
    }
    
    /**
     * 发布账户余额变更事件
     * 
     * @param account    账户信息
     * @param oldBalance 旧余额
     * @param newBalance 新余额
     * @param reason     变更原因
     * @param operatorId 操作人ID
     */
    public void publishAccountBalanceChangedEvent(Account account, BigDecimal oldBalance, 
                                                BigDecimal newBalance, String reason, Long operatorId) {
        logger.debug("发布账户余额变更事件: accountId={}, 变更: {} -> {}, 原因: {}", 
                 account.getId(), oldBalance, newBalance, reason);
                 
        // 这里需要创建AccountBalanceChangedEvent事件类
        // 暂时使用log记录
    }
    
    /**
     * 发布预算变更事件
     * 
     * @param budget    预算信息
     * @param oldAmount 旧金额
     * @param newAmount 新金额
     * @param reason    变更原因
     * @param operatorId 操作人ID
     */
    public void publishBudgetChangedEvent(Budget budget, BigDecimal oldAmount, 
                                        BigDecimal newAmount, String reason, Long operatorId) {
        logger.debug("发布预算变更事件: budgetId={}, 变更: {} -> {}, 原因: {}", 
                 budget.getId(), oldAmount, newAmount, reason);
                 
        // 这里需要创建BudgetChangedEvent事件类
        // 暂时使用log记录
    }
} 