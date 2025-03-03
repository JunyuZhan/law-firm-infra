package com.lawfirm.model.finance.dto.transaction;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TransactionUpdateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "交易ID不能为空")
    private Long id;

    /**
     * 交易类型
     */
    private TransactionTypeEnum transactionType;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;

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
    @Size(max = 50, message = "业务类型长度不能超过50个字符")
    private String businessType;

    /**
     * 交易摘要
     */
    @Size(max = 200, message = "交易摘要长度不能超过200个字符")
    private String summary;

    /**
     * 交易备注
     */
    @Size(max = 500, message = "交易备注长度不能超过500个字符")
    private String remark;

    /**
     * 关联部门ID
     */
    private Long departmentId;
} 