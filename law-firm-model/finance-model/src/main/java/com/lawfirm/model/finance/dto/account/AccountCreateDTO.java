package com.lawfirm.model.finance.dto.account;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.AccountStatusEnum;
import com.lawfirm.model.finance.enums.AccountTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 账户创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AccountCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 账户编号
     */
    @NotBlank(message = "账户编号不能为空")
    @Size(max = 32, message = "账户编号长度不能超过32个字符")
    private String accountNumber;

    /**
     * 账户名称
     */
    @NotBlank(message = "账户名称不能为空")
    @Size(max = 100, message = "账户名称长度不能超过100个字符")
    private String accountName;

    /**
     * 账户类型
     */
    @NotNull(message = "账户类型不能为空")
    private AccountTypeEnum accountType;

    /**
     * 账户状态
     */
    @NotNull(message = "账户状态不能为空")
    private AccountStatusEnum accountStatus;

    /**
     * 币种
     */
    @NotNull(message = "币种不能为空")
    private CurrencyEnum currency;

    /**
     * 账户余额
     */
    @NotNull(message = "账户余额不能为空")
    private BigDecimal balance;

    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;

    /**
     * 可用金额
     */
    private BigDecimal availableAmount;

    /**
     * 信用额度
     */
    private BigDecimal creditLimit;

    /**
     * 开户行
     */
    @Size(max = 100, message = "开户行长度不能超过100个字符")
    private String bankName;

    /**
     * 银行账号
     */
    @Size(max = 32, message = "银行账号长度不能超过32个字符")
    private String bankAccount;

    /**
     * 开户人
     */
    @Size(max = 50, message = "开户人长度不能超过50个字符")
    private String accountHolder;

    /**
     * 关联客户ID
     */
    private Long clientId;

    /**
     * 关联部门ID
     */
    private Long departmentId;
} 