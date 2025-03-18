package com.lawfirm.model.finance.dto.payment;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 付款查询DTO
 */
@Data
public class PaymentQueryDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 当前页码
     */
    private Integer pageNum = 1;
    
    /**
     * 每页大小
     */
    private Integer pageSize = 10;
    
    /**
     * 付款编号
     */
    private String paymentNumber;
    
    /**
     * 付款状态
     */
    private Integer paymentStatus;
    
    /**
     * 最小金额
     */
    private BigDecimal minAmount;
    
    /**
     * 最大金额
     */
    private BigDecimal maxAmount;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 付款日期开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDateStart;
    
    /**
     * 付款日期结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDateEnd;
    
    /**
     * 关联案件ID
     */
    private Long caseId;
    
    /**
     * 关联合同ID
     */
    private Long contractId;
    
    /**
     * 关联客户ID
     */
    private Long clientId;
    
    /**
     * 关联律师ID
     */
    private Long lawyerId;
    
    /**
     * 关联部门ID
     */
    private Long departmentId;
    
    /**
     * 关键词（用于搜索付款说明、备注等）
     */
    private String keyword;
    
    /**
     * 付款方式
     */
    private String paymentMethod;
    
    /**
     * 创建时间开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTimeStart;
    
    /**
     * 创建时间结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTimeEnd;
    
    /**
     * 创建人
     */
    private String createBy;
    
    /**
     * 排序字段
     */
    private String orderField;
    
    /**
     * 排序方向（asc/desc）
     */
    private String orderDirection;
} 