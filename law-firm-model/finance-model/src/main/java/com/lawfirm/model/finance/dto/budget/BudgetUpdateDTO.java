package com.lawfirm.model.finance.dto.budget;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.BudgetStatusEnum;
import com.lawfirm.model.finance.enums.BudgetTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 预算更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BudgetUpdateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "预算ID不能为空")
    private Long id;

    /**
     * 预算名称
     */
    @Size(max = 100, message = "预算名称长度不能超过100个字符")
    private String budgetName;

    /**
     * 预算类型
     */
    private BudgetTypeEnum budgetType;

    /**
     * 预算状态
     */
    private BudgetStatusEnum budgetStatus;

    /**
     * 预算金额
     */
    private BigDecimal amount;

    /**
     * 已使用金额
     */
    private BigDecimal usedAmount;

    /**
     * 剩余金额
     */
    private BigDecimal remainingAmount;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 预算周期开始时间
     */
    private LocalDateTime startTime;

    /**
     * 预算周期结束时间
     */
    private LocalDateTime endTime;

    /**
     * 关联部门ID
     */
    private Long departmentId;

    /**
     * 关联成本中心ID
     */
    private Long costCenterId;

    /**
     * 预算说明
     */
    @Size(max = 500, message = "预算说明长度不能超过500个字符")
    private String description;
} 