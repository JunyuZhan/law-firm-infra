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
 * 账单详情VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BillingDetailVO extends BaseVO {

    private static final long serialVersionUID = 1L;

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
     * 关联案件ID
     */
    private Long caseId;

    /**
     * 关联案件编号
     */
    private String caseNumber;

    /**
     * 关联案件名称
     */
    private String caseName;

    /**
     * 关联客户ID
     */
    private Long clientId;

    /**
     * 关联客户名称
     */
    private String clientName;

    /**
     * 关联律师ID
     */
    private Long lawyerId;

    /**
     * 关联律师名称
     */
    private String lawyerName;

    /**
     * 关联部门ID
     */
    private Long departmentId;

    /**
     * 关联部门名称
     */
    private String departmentName;

    /**
     * 账单说明
     */
    private String description;

    /**
     * 账单明细，JSON格式
     */
    private String details;

    /**
     * 付款计划ID
     */
    private Long paymentPlanId;

    /**
     * 发票状态（0-未开票，1-已开票，2-已作废）
     */
    private Integer invoiceStatus;

    /**
     * 开票时间
     */
    private LocalDateTime invoiceTime;

    /**
     * 发票金额
     */
    private BigDecimal invoiceAmount;

    /**
     * 发票号码
     */
    private String invoiceNumber;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateBy;
} 