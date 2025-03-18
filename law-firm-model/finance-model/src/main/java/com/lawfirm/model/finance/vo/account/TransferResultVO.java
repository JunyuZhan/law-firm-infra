package com.lawfirm.model.finance.vo.account;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 转账结果视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TransferResultVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 交易ID
     */
    private Long transactionId;

    /**
     * 交易流水号
     */
    private String transactionNumber;

    /**
     * 源账户ID
     */
    private Long fromAccountId;

    /**
     * 源账户名称
     */
    private String fromAccountName;

    /**
     * 目标账户ID
     */
    private Long toAccountId;

    /**
     * 目标账户名称
     */
    private String toAccountName;

    /**
     * 转账金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 转账时间
     */
    private LocalDateTime transactionTime;

    /**
     * 交易状态
     */
    private Integer status;

    /**
     * 交易状态描述
     */
    private String statusDesc;

    /**
     * 转账备注
     */
    private String remark;

    /**
     * 源账户转账后余额
     */
    private BigDecimal fromAccountBalance;

    /**
     * 目标账户转账后余额
     */
    private BigDecimal toAccountBalance;
} 