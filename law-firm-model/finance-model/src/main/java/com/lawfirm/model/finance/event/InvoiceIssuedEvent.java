package com.lawfirm.model.finance.event;

import com.lawfirm.model.finance.entity.Invoice;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 发票开具事件
 * 当发票被开具时触发
 */
@Getter
public class InvoiceIssuedEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 发票信息
     */
    private final Invoice invoice;
    
    /**
     * 操作人ID
     */
    private final Long operatorId;
    
    /**
     * 开票时间
     */
    private final long issuedTime;
    
    /**
     * 备注信息
     */
    private final String remark;
    
    /**
     * 构造方法
     * 
     * @param source     事件源
     * @param invoice    发票信息
     * @param operatorId 操作人ID
     * @param remark     备注信息
     */
    public InvoiceIssuedEvent(Object source, Invoice invoice, Long operatorId, String remark) {
        super(source);
        this.invoice = invoice;
        this.operatorId = operatorId;
        this.issuedTime = System.currentTimeMillis();
        this.remark = remark;
    }
} 