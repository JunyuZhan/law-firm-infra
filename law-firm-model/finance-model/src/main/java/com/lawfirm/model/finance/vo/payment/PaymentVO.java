package com.lawfirm.model.finance.vo.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 付款记录视图对象
 */
@Data
public class PaymentVO {
    /**
     * 付款记录ID
     */
    private Long id;
    
    /**
     * 付款编号
     */
    private String paymentNumber;
    
    /**
     * 付款状态
     */
    private Integer paymentStatus;
    
    /**
     * 付款状态描述
     */
    private String paymentStatusDesc;
    
    /**
     * 付款金额
     */
    private BigDecimal amount;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 币种描述
     */
    private String currencyDesc;
    
    /**
     * 付款日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;
    
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
     * 付款明细，JSON格式
     */
    private String details;
    
    /**
     * 付款方式
     */
    private String paymentMethod;
    
    /**
     * 付款方式描述
     */
    private String paymentMethodDesc;
    
    /**
     * 付款计划ID
     */
    private Long paymentPlanId;
    
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
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    /**
     * 创建人
     */
    private String createBy;
    
    /**
     * 创建人姓名
     */
    private String createName;
    
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    /**
     * 更新人
     */
    private String updateBy;
    
    /**
     * 更新人姓名
     */
    private String updateName;
} 