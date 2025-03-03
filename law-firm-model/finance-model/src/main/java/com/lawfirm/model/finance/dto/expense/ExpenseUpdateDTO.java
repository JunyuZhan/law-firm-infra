package com.lawfirm.model.finance.dto.expense;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.ExpenseTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支出更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ExpenseUpdateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "支出ID不能为空")
    private Long id;

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
     * 支出说明
     */
    @Size(max = 500, message = "支出说明长度不能超过500个字符")
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
     * 审批时间
     */
    private LocalDateTime approvalTime;

    /**
     * 审批意见
     */
    @Size(max = 500, message = "审批意见长度不能超过500个字符")
    private String approvalComment;
} 