package com.lawfirm.model.finance.vo.invoice;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.InvoiceTypeEnum;
import com.lawfirm.model.finance.enums.InvoiceStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 发票列表VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class InvoiceListVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 发票编号
     */
    private String invoiceNumber;

    /**
     * 发票类型
     */
    private InvoiceTypeEnum invoiceType;

    /**
     * 发票金额
     */
    private BigDecimal amount;

    /**
     * 发票抬头
     */
    private String title;

    /**
     * 纳税人识别号
     */
    private String taxpayerNumber;

    /**
     * 开票时间
     */
    private LocalDateTime invoiceTime;

    /**
     * 发票状态
     */
    private InvoiceStatusEnum invoiceStatus;

    /**
     * 关联账单编号
     */
    private String billingNumber;

    /**
     * 关联案件编号
     */
    private String caseNumber;

    /**
     * 关联客户名称
     */
    private String clientName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;
} 