package com.lawfirm.model.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.ExpenseTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支出实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_expense")
public class Expense extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 支出编号
     */
    @NotBlank(message = "支出编号不能为空")
    @Size(max = 32, message = "支出编号长度不能超过32个字符")
    @TableField("expense_number")
    private String expenseNumber;

    /**
     * 支出类型
     */
    @NotNull(message = "支出类型不能为空")
    @TableField("expense_type")
    private ExpenseTypeEnum expenseType;

    /**
     * 支出金额
     */
    @NotNull(message = "支出金额不能为空")
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 币种
     */
    @NotNull(message = "币种不能为空")
    @TableField("currency")
    private CurrencyEnum currency;

    /**
     * 支出时间
     */
    @NotNull(message = "支出时间不能为空")
    @TableField("expense_time")
    private LocalDateTime expenseTime;

    /**
     * 支付账户ID
     */
    @NotNull(message = "支付账户不能为空")
    @TableField("account_id")
    private Long accountId;

    /**
     * 关联预算ID
     */
    @TableField("budget_id")
    private Long budgetId;

    /**
     * 关联成本中心ID
     */
    @TableField("cost_center_id")
    private Long costCenterId;

    /**
     * 关联部门ID
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 关联员工ID
     */
    @TableField("employee_id")
    private Long employeeId;

    /**
     * 支出说明
     */
    @Size(max = 500, message = "支出说明长度不能超过500个字符")
    @TableField("description")
    private String description;

    /**
     * 附件URL列表，JSON格式
     */
    @TableField("attachments")
    private String attachments;

    /**
     * 审批状态（0-待审批，1-已审批，2-已驳回）
     */
    @TableField("approval_status")
    private Integer approvalStatus;

    /**
     * 审批人ID
     */
    @TableField("approver_id")
    private Long approverId;

    /**
     * 审批时间
     */
    @TableField("approval_time")
    private LocalDateTime approvalTime;

    /**
     * 审批意见
     */
    @Size(max = 500, message = "审批意见长度不能超过500个字符")
    @TableField("approval_comment")
    private String approvalComment;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    @Override
    public void preInsert() {
        super.preInsert();
        if (this.expenseTime == null) {
            this.expenseTime = LocalDateTime.now();
        }
        if (this.approvalStatus == null) {
            this.approvalStatus = 0;
        }
    }
} 