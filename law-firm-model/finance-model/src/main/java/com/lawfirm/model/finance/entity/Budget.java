package com.lawfirm.model.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.finance.enums.BudgetStatusEnum;
import com.lawfirm.model.finance.enums.BudgetTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 预算实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_budget")
public class Budget extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 预算编号
     */
    @NotBlank(message = "预算编号不能为空")
    @Size(max = 32, message = "预算编号长度不能超过32个字符")
    @TableField("budget_number")
    private String budgetNumber;

    /**
     * 预算名称
     */
    @NotBlank(message = "预算名称不能为空")
    @Size(max = 100, message = "预算名称长度不能超过100个字符")
    @TableField("budget_name")
    private String budgetName;

    /**
     * 预算类型
     */
    @NotNull(message = "预算类型不能为空")
    @TableField("budget_type")
    private BudgetTypeEnum budgetType;

    /**
     * 预算状态
     */
    @NotNull(message = "预算状态不能为空")
    @TableField("budget_status")
    private BudgetStatusEnum budgetStatus;

    /**
     * 预算金额
     */
    @NotNull(message = "预算金额不能为空")
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 已使用金额
     */
    @TableField("used_amount")
    private BigDecimal usedAmount;

    /**
     * 剩余金额
     */
    @TableField("remaining_amount")
    private BigDecimal remainingAmount;

    /**
     * 币种
     */
    @NotNull(message = "币种不能为空")
    @TableField("currency")
    private CurrencyEnum currency;

    /**
     * 预算周期开始时间
     */
    @NotNull(message = "预算周期开始时间不能为空")
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 预算周期结束时间
     */
    @NotNull(message = "预算周期结束时间不能为空")
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 关联部门ID
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 关联成本中心ID
     */
    @TableField("cost_center_id")
    private Long costCenterId;

    /**
     * 预算说明
     */
    @Size(max = 500, message = "预算说明长度不能超过500个字符")
    @TableField("description")
    private String description;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    @Override
    public void preInsert() {
        super.preInsert();
        if (this.usedAmount == null) {
            this.usedAmount = BigDecimal.ZERO;
        }
        if (this.remainingAmount == null) {
            this.remainingAmount = this.amount;
        }
    }
} 