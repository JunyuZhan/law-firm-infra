package com.lawfirm.model.finance.event;

import com.lawfirm.model.finance.entity.Income;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

/**
 * 付款接收事件
 * 当收到付款时触发
 */
@Getter
public class PaymentReceivedEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 收入信息
     */
    private final Income income;
    
    /**
     * 付款金额
     */
    private final BigDecimal amount;
    
    /**
     * 付款方ID
     */
    private final Long payerId;
    
    /**
     * 付款方名称
     */
    private final String payerName;
    
    /**
     * 关联业务ID (可能是合同ID或案件ID)
     */
    private final Long businessId;
    
    /**
     * 业务类型 (CONTRACT/CASE)
     */
    private final String businessType;
    
    /**
     * 操作人ID
     */
    private final Long operatorId;
    
    /**
     * 构造方法
     * 
     * @param source       事件源
     * @param income       收入信息
     * @param payerId      付款方ID
     * @param payerName    付款方名称
     * @param businessId   关联业务ID
     * @param businessType 业务类型
     * @param operatorId   操作人ID
     */
    public PaymentReceivedEvent(Object source, Income income, Long payerId, String payerName, 
                               Long businessId, String businessType, Long operatorId) {
        super(source);
        this.income = income;
        this.amount = income.getAmount();
        this.payerId = payerId;
        this.payerName = payerName;
        this.businessId = businessId;
        this.businessType = businessType;
        this.operatorId = operatorId;
    }
} 