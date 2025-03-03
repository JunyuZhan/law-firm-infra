package com.lawfirm.model.finance.dto.transaction;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TransactionCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 交易编号
     */
    @NotBlank(message = "交易编号不能为空")
    @Size(max = 32, message = "交易编号长度不能超过32个字符")
    private String transactionNumber;

    /**
     * 交易类型
     */
    @NotNull(message = "交易类型不能为空")
    private TransactionTypeEnum transactionType;

    /**
     * 交易金额
     */
    @NotNull(message = "交易金额不能为空")
    private BigDecimal amount;

    /**
     * 币种
     */
    @NotNull(message = "币种不能为空")
    private CurrencyEnum currency;

    /**
     * 交易时间
     */
    @NotNull(message = "交易时间不能为空")
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