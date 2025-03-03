package com.lawfirm.model.finance.dto.account;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.AccountStatusEnum;
import com.lawfirm.model.finance.enums.AccountTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AccountQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

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
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 最小余额
     */
    private BigDecimal minBalance;

    /**
     * 最大余额
     */
    private BigDecimal maxBalance;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 开户人
     */
    private String accountHolder;

    /**
     * 关联客户ID
     */
    private Long clientId;

    /**
     * 关联部门ID
     */
    private Long departmentId;

    /**
     * 创建时间开始
     */
    private LocalDateTime createTimeStart;

    /**
     * 创建时间结束
     */
    private LocalDateTime createTimeEnd;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
} 