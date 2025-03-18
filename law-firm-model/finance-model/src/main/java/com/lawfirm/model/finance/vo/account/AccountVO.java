package com.lawfirm.model.finance.vo.account;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.AccountStatusEnum;
import com.lawfirm.model.finance.enums.AccountTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AccountVO extends BaseVO {

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
    private String name;

    /**
     * 账户类型
     */
    private Integer type;

    /**
     * 账户类型描述
     */
    private String typeDesc;

    /**
     * 账户状态
     */
    private Integer status;

    /**
     * 账户状态描述
     */
    private String statusDesc;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 币种
     */
    private String currency;

    /**
     * 币种描述
     */
    private String currencyDesc;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 账户持有人
     */
    private String accountHolder;

    /**
     * 关联部门ID
     */
    private Long departmentId;

    /**
     * 关联部门名称
     */
    private String departmentName;

    /**
     * 可用余额
     */
    private BigDecimal availableBalance;

    /**
     * 冻结余额
     */
    private BigDecimal frozenBalance;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 最近交易时间
     */
    private LocalDateTime lastTransactionTime;
} 