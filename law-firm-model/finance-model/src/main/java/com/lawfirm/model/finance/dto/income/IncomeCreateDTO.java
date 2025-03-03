package com.lawfirm.model.finance.dto.income;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.IncomeTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 收入创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class IncomeCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 收入编号
     */
    @NotBlank(message = "收入编号不能为空")
    @Size(max = 32, message = "收入编号长度不能超过32个字符")
    private String incomeNumber;

    /**
     * 收入类型
     */
    @NotNull(message = "收入类型不能为空")
    private IncomeTypeEnum incomeType;

    /**
     * 收入金额
     */
    @NotNull(message = "收入金额不能为空")
    private BigDecimal amount;

    /**
     * 币种
     */
    @NotNull(message = "币种不能为空")
    private CurrencyEnum currency;

    /**
     * 收入时间
     */
    @NotNull(message = "收入时间不能为空")
    private LocalDateTime incomeTime;

    /**
     * 收款账户ID
     */
    @NotNull(message = "收款账户不能为空")
    private Long accountId;

    /**
     * 关联案件ID
     */
    private Long caseId;

    /**
     * 关联客户ID
     */
    @NotNull(message = "客户不能为空")
    private Long clientId;

    /**
     * 关联律师ID
     */
    private Long lawyerId;

    /**
     * 关联部门ID
     */
    private Long departmentId;

    /**
     * 关联成本中心ID
     */
    private Long costCenterId;

    /**
     * 收入说明
     */
    @Size(max = 500, message = "收入说明长度不能超过500个字符")
    private String description;

    /**
     * 附件URL列表，JSON格式
     */
    @Size(max = 1000, message = "附件URL列表长度不能超过1000个字符")
    private String attachments;
} 