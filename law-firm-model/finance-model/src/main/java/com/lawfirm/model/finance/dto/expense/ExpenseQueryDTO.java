package com.lawfirm.model.finance.dto.expense;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.ExpenseTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支出查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ExpenseQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 支出编号
     */
    private String expenseNumber;

    /**
     * 支出类型
     */
    private ExpenseTypeEnum expenseType;

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
     * 支出时间开始
     */
    private LocalDateTime expenseTimeStart;

    /**
     * 支出时间结束
     */
    private LocalDateTime expenseTimeEnd;

    /**
     * 支付账户ID
     */
    private Long accountId;

    /**
     * 关联预算ID
     */
    private Long budgetId;

    /**
     * 关联成本中心ID
     */
    private Long costCenterId;

    /**
     * 关联部门ID
     */
    private Long departmentId;

    /**
     * 关联员工ID
     */
    private Long employeeId;

    /**
     * 审批状态（0-未审批，1-审批中，2-已通过，3-已驳回）
     */
    private Integer approvalStatus;

    /**
     * 审批人ID
     */
    private Long approverId;

    /**
     * 审批时间开始
     */
    private LocalDateTime approvalTimeStart;

    /**
     * 审批时间结束
     */
    private LocalDateTime approvalTimeEnd;

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