package com.lawfirm.model.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.PaymentStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 付款实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_payment")
public class Payment extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 付款编号
     */
    @NotBlank(message = "付款编号不能为空")
    @Size(max = 32, message = "付款编号长度不能超过32个字符")
    @TableField("payment_number")
    private String paymentNumber;

    /**
     * 付款状态
     */
    @NotNull(message = "付款状态不能为空")
    @TableField("payment_status")
    private PaymentStatusEnum paymentStatus;

    /**
     * 付款金额
     */
    @NotNull(message = "付款金额不能为空")
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 币种
     */
    @NotNull(message = "币种不能为空")
    @TableField("currency")
    private CurrencyEnum currency;

    /**
     * 付款日期
     */
    @NotNull(message = "付款日期不能为空")
    @TableField("payment_date")
    private LocalDateTime paymentDate;

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
     * 付款说明
     */
    @Size(max = 500, message = "付款说明长度不能超过500个字符")
    @TableField("description")
    private String description;

    /**
     * 付款明细，JSON格式
     */
    @TableField("details")
    private String details;

    /**
     * 付款计划ID
     */
    @TableField("payment_plan_id")
    private Long paymentPlanId;

    /**
     * 关联账单ID
     */
    @TableField("billing_id")
    private Long billingId;

    /**
     * 关联发票ID
     */
    @TableField("invoice_id")
    private Long invoiceId;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    @Override
    public void preInsert() {
        super.preInsert();
        if (this.paymentDate == null) {
            this.paymentDate = LocalDateTime.now();
        }
    }
} 