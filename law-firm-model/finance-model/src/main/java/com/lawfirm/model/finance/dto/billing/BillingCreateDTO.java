package com.lawfirm.model.finance.dto.billing;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.BillingStatusEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账单创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BillingCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 账单编号
     */
    @NotBlank(message = "账单编号不能为空")
    @Size(max = 32, message = "账单编号长度不能超过32个字符")
    private String billingNumber;

    /**
     * 账单状态
     */
    @NotNull(message = "账单状态不能为空")
    private BillingStatusEnum billingStatus;

    /**
     * 账单金额
     */
    @NotNull(message = "账单金额不能为空")
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
    @NotNull(message = "币种不能为空")
    private CurrencyEnum currency;

    /**
     * 账单日期
     */
    @NotNull(message = "账单日期不能为空")
    private LocalDateTime billingDate;

    /**
     * 付款截止日期
     */
    @NotNull(message = "付款截止日期不能为空")
    private LocalDateTime dueDate;

    /**
     * 关联案件ID
     */
    private Long caseId;

    /**
     * 关联客户ID
     */
    @NotNull(message = "客户不能为空")
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
     * 账单说明
     */
    @Size(max = 500, message = "账单说明长度不能超过500个字符")
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
    @Size(max = 50, message = "发票号码长度不能超过50个字符")
    private String invoiceNumber;
} 