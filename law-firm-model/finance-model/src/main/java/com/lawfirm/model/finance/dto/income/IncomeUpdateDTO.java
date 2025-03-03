package com.lawfirm.model.finance.dto.income;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.IncomeTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 收入更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class IncomeUpdateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "收入ID不能为空")
    private Long id;

    /**
     * 收入类型
     */
    private IncomeTypeEnum incomeType;

    /**
     * 收入金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 收入时间
     */
    private LocalDateTime incomeTime;

    /**
     * 收款账户ID
     */
    private Long accountId;

    /**
     * 关联案件ID
     */
    private Long caseId;

    /**
     * 关联客户ID
     */
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
    private String attachments;

    /**
     * 确认状态（0-未确认，1-已确认）
     */
    private Integer confirmStatus;
} 