package com.lawfirm.model.finance.dto.transaction;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TransactionQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 交易编号
     */
    private String transactionNumber;

    /**
     * 交易类型
     */
    private TransactionTypeEnum transactionType;

    /**
     * 最小金额
     */
    private BigDecimal minAmount;

    /**
     * 最大金额
     */
    private BigDecimal maxAmount;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 交易时间开始
     */
    private LocalDateTime transactionTimeStart;

    /**
     * 交易时间结束
     */
    private LocalDateTime transactionTimeEnd;

    /**
     * 付款账户ID
     */
    private Long fromAccountId;

    /**
     * 收款账户ID
     */
    private Long toAccountId;

    /**
     * 关联业务ID
     */
    private Long businessId;

    /**
     * 业务类型
     */
    private String businessType;

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