package com.lawfirm.model.finance.dto.budget;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.BudgetStatusEnum;
import com.lawfirm.model.finance.enums.BudgetTypeEnum;
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
 * 预算创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BudgetCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 预算编号
     */
    @NotBlank(message = "预算编号不能为空")
    @Size(max = 32, message = "预算编号长度不能超过32个字符")
    private String budgetNumber;

    /**
     * 预算名称
     */
    @NotBlank(message = "预算名称不能为空")
    @Size(max = 100, message = "预算名称长度不能超过100个字符")
    private String budgetName;

    /**
     * 预算类型
     */
    @NotNull(message = "预算类型不能为空")
    private BudgetTypeEnum budgetType;

    /**
     * 预算状态
     */
    @NotNull(message = "预算状态不能为空")
    private BudgetStatusEnum budgetStatus;

    /**
     * 预算金额
     */
    @NotNull(message = "预算金额不能为空")
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
    @NotNull(message = "币种不能为空")
    private CurrencyEnum currency;

    /**
     * 预算周期开始时间
     */
    @NotNull(message = "预算周期开始时间不能为空")
    private LocalDateTime startTime;

    /**
     * 预算周期结束时间
     */
    @NotNull(message = "预算周期结束时间不能为空")
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