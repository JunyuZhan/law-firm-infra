package com.lawfirm.model.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.finance.enums.BillingStatusEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账单记录实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_billing_record")
public class BillingRecord extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 账单编号
     */
    @NotBlank(message = "账单编号不能为空")
    @Size(max = 32, message = "账单编号长度不能超过32个字符")
    @TableField("billing_number")
    private String billingNumber;

    /**
     * 账单状态
     */
    @NotNull(message = "账单状态不能为空")
    @TableField("billing_status")
    private BillingStatusEnum billingStatus;

    /**
     * 账单金额
     */
    @NotNull(message = "账单金额不能为空")
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 已付金额
     */
    @TableField("paid_amount")
    private BigDecimal paidAmount;

    /**
     * 未付金额
     */
    @TableField("unpaid_amount")
    private BigDecimal unpaidAmount;

    /**
     * 币种
     */
    @NotNull(message = "币种不能为空")
    @TableField("currency")
    private CurrencyEnum currency;

    /**
     * 账单日期
     */
    @NotNull(message = "账单日期不能为空")
    @TableField("billing_date")
    private LocalDateTime billingDate;

    /**
     * 付款截止日期
     */
    @NotNull(message = "付款截止日期不能为空")
    @TableField("due_date")
    private LocalDateTime dueDate;

    /**
     * 关联案件ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 关联客户ID
     */
    @NotNull(message = "客户不能为空")
    @TableField("client_id")
    private Long clientId;

    /**
     * 关联律师ID
     */
    @TableField("lawyer_id")
    private Long lawyerId;

    /**
     * 关联部门ID
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 账单说明
     */
    @Size(max = 500, message = "账单说明长度不能超过500个字符")
    @TableField("description")
    private String description;

    /**
     * 账单明细，JSON格式
     */
    @TableField("details")
    private String details;

    /**
     * 付款计划ID
     */
    @TableField("payment_plan_id")
    private Long paymentPlanId;

    /**
     * 开票状态（0-未开票，1-已开票）
     */
    @TableField("invoice_status")
    private Integer invoiceStatus;

    /**
     * 开票时间
     */
    @TableField("invoice_time")
    private LocalDateTime invoiceTime;

    /**
     * 开票金额
     */
    @TableField("invoice_amount")
    private BigDecimal invoiceAmount;

    /**
     * 发票号码
     */
    @Size(max = 50, message = "发票号码长度不能超过50个字符")
    @TableField("invoice_number")
    private String invoiceNumber;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    @Override
    public void preInsert() {
        super.preInsert();
        if (this.billingDate == null) {
            this.billingDate = LocalDateTime.now();
        }
        if (this.paidAmount == null) {
            this.paidAmount = BigDecimal.ZERO;
        }
        if (this.unpaidAmount == null) {
            this.unpaidAmount = this.amount;
        }
        if (this.invoiceStatus == null) {
            this.invoiceStatus = 0;
        }
    }
} 