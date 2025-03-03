package com.lawfirm.model.finance.vo.expense;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.ExpenseTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支出详情VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ExpenseDetailVO extends BaseVO {

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
     * 支出金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 支出时间
     */
    private LocalDateTime expenseTime;

    /**
     * 支付账户ID
     */
    private Long accountId;

    /**
     * 支付账户名称
     */
    private String accountName;

    /**
     * 关联预算ID
     */
    private Long budgetId;

    /**
     * 关联预算名称
     */
    private String budgetName;

    /**
     * 关联成本中心ID
     */
    private Long costCenterId;

    /**
     * 关联成本中心名称
     */
    private String costCenterName;

    /**
     * 关联部门ID
     */
    private Long departmentId;

    /**
     * 关联部门名称
     */
    private String departmentName;

    /**
     * 关联员工ID
     */
    private Long employeeId;

    /**
     * 关联员工姓名
     */
    private String employeeName;

    /**
     * 支出说明
     */
    private String description;

    /**
     * 附件URL列表，JSON格式
     */
    private String attachments;

    /**
     * 审批状态（0-未审批，1-审批中，2-已通过，3-已驳回）
     */
    private Integer approvalStatus;

    /**
     * 审批人ID
     */
    private Long approverId;

    /**
     * 审批人姓名
     */
    private String approverName;

    /**
     * 审批时间
     */
    private LocalDateTime approvalTime;

    /**
     * 审批意见
     */
    private String approvalComment;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateBy;
} 