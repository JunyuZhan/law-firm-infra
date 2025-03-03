package com.lawfirm.model.finance.vo.billing;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.BillingStatusEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账单列表VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BillingListVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 账单编号
     */
    private String billingNumber;

    /**
     * 账单状态
     */
    private BillingStatusEnum billingStatus;

    /**
     * 账单金额
     */
    private BigDecimal amount;

    /**
     * 已付金额
     */
    private BigDecimal paidAmount;

    /**
     * 未付金额
     */
    private BigDecimal unpaidAmount;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 账单日期
     */
    private LocalDateTime billingDate;

    /**
     * 付款截止日期
     */
    private LocalDateTime dueDate;

    /**
     * 关联案件编号
     */
    private String caseNumber;

    /**
     * 关联客户名称
     */
    private String clientName;

    /**
     * 关联律师名称
     */
    private String lawyerName;

    /**
     * 发票状态（0-未开票，1-已开票，2-已作废）
     */
    private Integer invoiceStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;
} 