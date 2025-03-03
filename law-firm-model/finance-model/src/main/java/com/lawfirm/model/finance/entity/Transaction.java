package com.lawfirm.model.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_transaction")
public class Transaction extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 交易编号
     */
    @NotBlank(message = "交易编号不能为空")
    @Size(max = 32, message = "交易编号长度不能超过32个字符")
    @TableField("transaction_number")
    private String transactionNumber;

    /**
     * 交易类型
     */
    @NotNull(message = "交易类型不能为空")
    @TableField("transaction_type")
    private TransactionTypeEnum transactionType;

    /**
     * 交易金额
     */
    @NotNull(message = "交易金额不能为空")
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 币种
     */
    @NotNull(message = "币种不能为空")
    @TableField("currency")
    private CurrencyEnum currency;

    /**
     * 交易时间
     */
    @NotNull(message = "交易时间不能为空")
    @TableField("transaction_time")
    private LocalDateTime transactionTime;

    /**
     * 付款账户ID
     */
    @TableField("from_account_id")
    private Long fromAccountId;

    /**
     * 收款账户ID
     */
    @TableField("to_account_id")
    private Long toAccountId;

    /**
     * 关联业务ID
     */
    @TableField("business_id")
    private Long businessId;

    /**
     * 关联业务类型
     */
    @Size(max = 50, message = "业务类型长度不能超过50个字符")
    @TableField("business_type")
    private String businessType;

    /**
     * 交易摘要
     */
    @Size(max = 200, message = "交易摘要长度不能超过200个字符")
    @TableField("summary")
    private String summary;

    /**
     * 交易备注
     */
    @Size(max = 500, message = "交易备注长度不能超过500个字符")
    @TableField("remark")
    private String remark;

    /**
     * 关联部门ID
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    @Override
    public void preInsert() {
        super.preInsert();
        if (this.transactionTime == null) {
            this.transactionTime = LocalDateTime.now();
        }
    }
} 