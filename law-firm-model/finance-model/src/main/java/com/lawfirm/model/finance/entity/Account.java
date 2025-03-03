package com.lawfirm.model.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.finance.enums.AccountStatusEnum;
import com.lawfirm.model.finance.enums.AccountTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 账户实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_account")
public class Account extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 账户编号
     */
    @NotBlank(message = "账户编号不能为空")
    @Size(max = 32, message = "账户编号长度不能超过32个字符")
    @TableField("account_number")
    private String accountNumber;

    /**
     * 账户名称
     */
    @NotBlank(message = "账户名称不能为空")
    @Size(max = 100, message = "账户名称长度不能超过100个字符")
    @TableField("account_name")
    private String accountName;

    /**
     * 账户类型
     */
    @NotNull(message = "账户类型不能为空")
    @TableField("account_type")
    private AccountTypeEnum accountType;

    /**
     * 账户状态
     */
    @NotNull(message = "账户状态不能为空")
    @TableField("account_status")
    private AccountStatusEnum accountStatus;

    /**
     * 币种
     */
    @NotNull(message = "币种不能为空")
    @TableField("currency")
    private CurrencyEnum currency;

    /**
     * 账户余额
     */
    @NotNull(message = "账户余额不能为空")
    @TableField("balance")
    private BigDecimal balance;

    /**
     * 冻结金额
     */
    @TableField("frozen_amount")
    private BigDecimal frozenAmount;

    /**
     * 可用金额
     */
    @TableField("available_amount")
    private BigDecimal availableAmount;

    /**
     * 信用额度
     */
    @TableField("credit_limit")
    private BigDecimal creditLimit;

    /**
     * 开户行
     */
    @Size(max = 100, message = "开户行长度不能超过100个字符")
    @TableField("bank_name")
    private String bankName;

    /**
     * 银行账号
     */
    @Size(max = 32, message = "银行账号长度不能超过32个字符")
    @TableField("bank_account")
    private String bankAccount;

    /**
     * 开户人
     */
    @Size(max = 50, message = "开户人长度不能超过50个字符")
    @TableField("account_holder")
    private String accountHolder;

    /**
     * 关联客户ID
     */
    @TableField("client_id")
    private Long clientId;

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
        if (this.balance == null) {
            this.balance = BigDecimal.ZERO;
        }
        if (this.frozenAmount == null) {
            this.frozenAmount = BigDecimal.ZERO;
        }
        if (this.availableAmount == null) {
            this.availableAmount = this.balance.subtract(this.frozenAmount);
        }
        if (this.creditLimit == null) {
            this.creditLimit = BigDecimal.ZERO;
        }
    }
} 