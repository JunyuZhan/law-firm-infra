package com.lawfirm.model.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.ReceivableStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 应收账款实体类
 * 主要用于管理从合同模块产生的应收款项
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_receivable")
public class Receivable extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 应收账款编号
     */
    @NotBlank(message = "应收账款编号不能为空")
    @Size(max = 32, message = "应收账款编号长度不能超过32个字符")
    @TableField("receivable_no")
    private String receivableNo;

    /**
     * 应收账款名称
     */
    @NotBlank(message = "应收账款名称不能为空")
    @Size(max = 100, message = "应收账款名称长度不能超过100个字符")
    @TableField("receivable_name")
    private String receivableName;

    /**
     * 关联合同ID
     */
    @NotNull(message = "合同ID不能为空")
    @TableField("contract_id")
    private Long contractId;

    /**
     * 合同编号
     */
    @NotBlank(message = "合同编号不能为空")
    @Size(max = 32, message = "合同编号长度不能超过32个字符")
    @TableField("contract_no")
    private String contractNo;

    /**
     * 应收总金额
     */
    @NotNull(message = "应收总金额不能为空")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 已收金额
     */
    @TableField("received_amount")
    private BigDecimal receivedAmount;

    /**
     * 未收金额
     */
    @TableField("unreceived_amount")
    private BigDecimal unreceivedAmount;

    /**
     * 应收款币种
     */
    @NotNull(message = "币种不能为空")
    @TableField("currency")
    private CurrencyEnum currency;

    /**
     * 账期（天）
     */
    @TableField("credit_period")
    private Integer creditPeriod;

    /**
     * 预计收款日期
     */
    @TableField("expected_receipt_date")
    private LocalDateTime expectedReceiptDate;

    /**
     * 逾期天数
     */
    @TableField("overdue_days")
    private Integer overdueDays;

    /**
     * 应收款状态（存储状态码）
     */
    @NotNull(message = "应收款状态不能为空")
    @TableField("status")
    private Integer statusCode;

    /**
     * 关联付款计划ID
     */
    @TableField("payment_plan_id")
    private Long paymentPlanId;

    /**
     * 关联案件ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 关联客户ID
     */
    @NotNull(message = "客户ID不能为空")
    @TableField("client_id")
    private Long clientId;

    /**
     * 负责律师ID
     */
    @TableField("lawyer_id")
    private Long lawyerId;

    /**
     * 部门ID
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 最近收款日期
     */
    @TableField("last_receipt_date")
    private LocalDateTime lastReceiptDate;

    /**
     * 应收款备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    @TableField("remark")
    private String remark;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    /**
     * 获取应收款状态枚举
     */
    public ReceivableStatusEnum getStatusEnum() {
        return ReceivableStatusEnum.getByCode(this.statusCode);
    }

    /**
     * 设置应收款状态枚举
     */
    public void setStatusEnum(ReceivableStatusEnum statusEnum) {
        this.statusCode = statusEnum != null ? statusEnum.getCode() : null;
    }

    @Override
    public void preInsert() {
        super.preInsert();
        if (this.receivedAmount == null) {
            this.receivedAmount = BigDecimal.ZERO;
        }
        if (this.unreceivedAmount == null) {
            this.unreceivedAmount = this.totalAmount;
        }
        if (this.overdueDays == null) {
            this.overdueDays = 0;
        }
    }
} 