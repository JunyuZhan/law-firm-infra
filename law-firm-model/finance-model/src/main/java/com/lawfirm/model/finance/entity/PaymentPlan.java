package com.lawfirm.model.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
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
 * 付款计划实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_payment_plan")
public class PaymentPlan extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 付款计划编号
     */
    @NotBlank(message = "付款计划编号不能为空")
    @Size(max = 32, message = "付款计划编号长度不能超过32个字符")
    @TableField("plan_number")
    private String planNumber;

    /**
     * 付款计划名称
     */
    @NotBlank(message = "付款计划名称不能为空")
    @Size(max = 100, message = "付款计划名称长度不能超过100个字符")
    @TableField("plan_name")
    private String planName;

    /**
     * 总金额
     */
    @NotNull(message = "总金额不能为空")
    @TableField("total_amount")
    private BigDecimal totalAmount;

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
     * 付款期数
     */
    @NotNull(message = "付款期数不能为空")
    @TableField("installments")
    private Integer installments;

    /**
     * 已付期数
     */
    @TableField("paid_installments")
    private Integer paidInstallments;

    /**
     * 开始日期
     */
    @NotNull(message = "开始日期不能为空")
    @TableField("start_date")
    private LocalDateTime startDate;

    /**
     * 结束日期
     */
    @NotNull(message = "结束日期不能为空")
    @TableField("end_date")
    private LocalDateTime endDate;

    /**
     * 付款周期（1-按月，2-按季度，3-按年，4-自定义）
     */
    @NotNull(message = "付款周期不能为空")
    @TableField("payment_cycle")
    private Integer paymentCycle;

    /**
     * 付款日（每月/季度/年的第几天）
     */
    @TableField("payment_day")
    private Integer paymentDay;

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
     * 付款计划说明
     */
    @Size(max = 500, message = "付款计划说明长度不能超过500个字符")
    @TableField("description")
    private String description;

    /**
     * 付款计划明细，JSON格式
     */
    @TableField("details")
    private String details;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    @Override
    public void preInsert() {
        super.preInsert();
        if (this.paidAmount == null) {
            this.paidAmount = BigDecimal.ZERO;
        }
        if (this.unpaidAmount == null) {
            this.unpaidAmount = this.totalAmount;
        }
        if (this.paidInstallments == null) {
            this.paidInstallments = 0;
        }
    }
} 