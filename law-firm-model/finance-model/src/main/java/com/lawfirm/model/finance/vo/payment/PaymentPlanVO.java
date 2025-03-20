package com.lawfirm.model.finance.vo.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 付款计划视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PaymentPlanVO extends BaseVO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 付款计划编号
     */
    private String planNumber;
    
    /**
     * 付款状态
     */
    private Integer status;
    
    /**
     * 付款状态描述
     */
    private String statusDesc;
    
    /**
     * 计划付款金额
     */
    private BigDecimal planAmount;
    
    /**
     * 实际付款金额
     */
    private BigDecimal actualAmount;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 币种描述
     */
    private String currencyDesc;
    
    /**
     * 计划付款日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime planPaymentDate;
    
    /**
     * 实际付款日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actualPaymentDate;
    
    /**
     * 关联案件ID
     */
    private Long caseId;
    
    /**
     * 关联案件编号
     */
    private String caseNumber;
    
    /**
     * 关联合同ID
     */
    private Long contractId;
    
    /**
     * 关联合同编号
     */
    private String contractNumber;
    
    /**
     * 关联客户ID
     */
    private Long clientId;
    
    /**
     * 客户名称
     */
    private String clientName;
    
    /**
     * 关联律师ID
     */
    private Long lawyerId;
    
    /**
     * 律师姓名
     */
    private String lawyerName;
    
    /**
     * 关联部门ID
     */
    private Long departmentId;
    
    /**
     * 部门名称
     */
    private String departmentName;
    
    /**
     * 付款说明
     */
    private String description;
    
    /**
     * 付款方式
     */
    private String paymentMethod;
    
    /**
     * 付款方式描述
     */
    private String paymentMethodDesc;
    
    /**
     * 关联账单ID
     */
    private Long billingId;
    
    /**
     * 关联发票ID
     */
    private Long invoiceId;
    
    /**
     * 备注
     */
    private String remark;
} 