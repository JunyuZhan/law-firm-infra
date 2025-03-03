package com.lawfirm.model.finance.vo.budget;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.BudgetTypeEnum;
import com.lawfirm.model.finance.enums.BudgetStatusEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 预算列表VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BudgetListVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 预算编号
     */
    private String budgetNumber;

    /**
     * 预算名称
     */
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
     * 关联部门名称
     */
    private String departmentName;

    /**
     * 关联成本中心名称
     */
    private String costCenterName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;
} 