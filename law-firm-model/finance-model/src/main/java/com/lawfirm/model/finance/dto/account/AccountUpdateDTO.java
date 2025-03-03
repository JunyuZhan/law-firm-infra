package com.lawfirm.model.finance.dto.account;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.AccountStatusEnum;
import com.lawfirm.model.finance.enums.AccountTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 账户更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AccountUpdateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "账户ID不能为空")
    private Long id;

    /**
     * 账户名称
     */
    @Size(max = 100, message = "账户名称长度不能超过100个字符")
    private String accountName;

    /**
     * 账户类型
     */
    private AccountTypeEnum accountType;

    /**
     * 账户状态
     */
    private AccountStatusEnum accountStatus;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 账户余额
     */
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