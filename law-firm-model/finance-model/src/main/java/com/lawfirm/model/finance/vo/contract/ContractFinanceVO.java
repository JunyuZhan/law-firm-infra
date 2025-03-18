package com.lawfirm.model.finance.vo.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 合同财务视图对象
 */
@Data
public class ContractFinanceVO {
    /**
     * 合同ID
     */
    private Long contractId;
    
    /**
     * 合同编号
     */
    private String contractNumber;
    
    /**
     * 合同名称
     */
    private String contractName;
    
    /**
     * 合同类型
     */
    private Integer contractType;
    
    /**
     * 合同类型描述
     */
    private String contractTypeDesc;
    
    /**
     *
     * 客户ID
     */
    private Long clientId;
    
    /**
     * 客户名称
     */
    private String clientName;
    
    /**
     * 合同金额
     */
    private BigDecimal contractAmount;
    
    /**
     * 实际金额
     */
    private BigDecimal actualAmount;
    
    /**
     * 已收金额
     */
    private BigDecimal receivedAmount;
    
    /**
     * 未收金额
     */
    private BigDecimal unpaidAmount;
    
    /**
     * 收款进度（百分比）
     */
    private Double paymentProgress;
    
    /**
     * 开票金额
     */
    private BigDecimal invoicedAmount;
    
    /**
     * 开票进度（百分比）
     */
    private Double invoiceProgress;
    
    /**
     * 税率
     */
    private BigDecimal taxRate;
    
    /**
     * 税额
     */
    private BigDecimal taxAmount;
    
    /**
     * 合同签订日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate signDate;
    
    /**
     * 合同生效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectiveDate;
    
    /**
     * 合同到期日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;
    
    /**
     * 合同状态
     */
    private Integer contractStatus;
    
    /**
     * 合同状态描述
     */
    private String contractStatusDesc;
    
    /**
     * 付款类型
     */
    private Integer paymentType;
    
    /**
     * 付款类型描述
     */
    private String paymentTypeDesc;
    
    /**
     * 付款条款
     */
    private String paymentTerms;
    
    /**
     * 付款计划列表
     */
    private List<?> paymentPlans;
    
    /**
     * 收款记录列表
     */
    private List<?> incomeRecords;
    
    /**
     * 发票记录列表
     */
    private List<?> invoiceRecords;
    
    /**
     * 付款记录列表
     */
    private List<?> paymentRecords;
    
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
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
} 