package com.lawfirm.model.finance.vo.account;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.AccountTypeEnum;
import com.lawfirm.model.finance.enums.AccountStatusEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户列表VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AccountListVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 账户编号
     */
    private String accountNumber;

    /**
     * 账户名称
     */
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
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 账户持有人
     */
    private String accountHolder;

    /**
     * 关联部门名称
     */
    private String departmentName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;
} 