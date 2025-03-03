package com.lawfirm.model.finance.dto.budget;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.BudgetStatusEnum;
import com.lawfirm.model.finance.enums.BudgetTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 预算查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BudgetQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

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
     * 预算周期开始时间起始
     */
    private LocalDateTime startTimeStart;

    /**
     * 预算周期开始时间结束
     */
    private LocalDateTime startTimeEnd;

    /**
     * 预算周期结束时间起始
     */
    private LocalDateTime endTimeStart;

    /**
     * 预算周期结束时间结束
     */
    private LocalDateTime endTimeEnd;

    /**
     * 关联部门ID
     */
    private Long departmentId;

    /**
     * 关联成本中心ID
     */
    private Long costCenterId;

    /**
     * 创建时间起始
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