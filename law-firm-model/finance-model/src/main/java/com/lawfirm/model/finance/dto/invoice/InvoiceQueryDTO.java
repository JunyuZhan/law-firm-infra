package com.lawfirm.model.finance.dto.invoice;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.InvoiceTypeEnum;
import com.lawfirm.model.finance.enums.InvoiceStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 发票查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class InvoiceQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 发票编号
     */
    private String invoiceNumber;

    /**
     * 发票类型
     */
    private InvoiceTypeEnum invoiceType;

    /**
     * 最小金额
     */
    private BigDecimal minAmount;

    /**
     * 最大金额
     */
    private BigDecimal maxAmount;

    /**
     * 发票抬头
     */
    private String title;

    /**
     * 纳税人识别号
     */
    private String taxpayerNumber;

    /**
     * 开票时间起始
     */
    private LocalDateTime invoiceTimeStart;

    /**
     * 开票时间结束
     */
    private LocalDateTime invoiceTimeEnd;

    /**
     * 发票状态
     */
    private InvoiceStatusEnum invoiceStatus;

    /**
     * 关联账单ID
     */
    private Long billingId;

    /**
     * 关联案件ID
     */
    private Long caseId;

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
     * 创建时间起始
     */
    private LocalDateTime createTimeStart;

    /**
     * 创建时间结束
     */
    private LocalDateTime createTimeEnd;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
} 