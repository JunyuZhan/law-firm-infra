package com.lawfirm.model.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.IncomeTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 收入实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_income")
public class Income extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 收入编号
     */
    @NotBlank(message = "收入编号不能为空")
    @Size(max = 32, message = "收入编号长度不能超过32个字符")
    @TableField("income_number")
    private String incomeNumber;

    /**
     * 收入类型
     */
    @NotNull(message = "收入类型不能为空")
    @TableField("income_type")
    private IncomeTypeEnum incomeType;

    /**
     * 收入金额
     */
    @NotNull(message = "收入金额不能为空")
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 币种
     */
    @NotNull(message = "币种不能为空")
    @TableField("currency")
    private CurrencyEnum currency;

    /**
     * 收入时间
     */
    @NotNull(message = "收入时间不能为空")
    @TableField("income_time")
    private LocalDateTime incomeTime;

    /**
     * 收款账户ID
     */
    @NotNull(message = "收款账户不能为空")
    @TableField("account_id")
    private Long accountId;

    /**
     * 关联案件ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 关联客户ID
     */
    @TableField("client_id")
    private Long clientId;

    /**
     * 关联律师ID
     */
    @TableField("lawyer_id")
    private Long lawyerId;

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
     * 收入说明
     */
    @Size(max = 500, message = "收入说明长度不能超过500个字符")
    @TableField("description")
    private String description;

    /**
     * 附件URL列表，JSON格式
     */
    @TableField("attachments")
    private String attachments;

    /**
     * 确认状态（0-未确认，1-已确认）
     */
    @TableField("confirm_status")
    private Integer confirmStatus;

    /**
     * 确认人ID
     */
    @TableField("confirmer_id")
    private Long confirmerId;

    /**
     * 确认时间
     */
    @TableField("confirm_time")
    private LocalDateTime confirmTime;

    /**
     * 确认备注
     */
    @Size(max = 500, message = "确认备注长度不能超过500个字符")
    @TableField("confirm_remark")
    private String confirmRemark;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    @Override
    public void preInsert() {
        super.preInsert();
        if (this.incomeTime == null) {
            this.incomeTime = LocalDateTime.now();
        }
        if (this.confirmStatus == null) {
            this.confirmStatus = 0;
        }
    }
} 