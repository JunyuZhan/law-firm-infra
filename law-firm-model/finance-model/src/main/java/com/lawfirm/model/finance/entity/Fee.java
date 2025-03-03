package com.lawfirm.model.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.FeeTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 费用实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_fee")
public class Fee extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 费用编号
     */
    @NotBlank(message = "费用编号不能为空")
    @Size(max = 32, message = "费用编号长度不能超过32个字符")
    @TableField("fee_number")
    private String feeNumber;

    /**
     * 费用类型
     */
    @NotNull(message = "费用类型不能为空")
    @TableField("fee_type")
    private FeeTypeEnum feeType;

    /**
     * 费用名称
     */
    @NotBlank(message = "费用名称不能为空")
    @Size(max = 100, message = "费用名称长度不能超过100个字符")
    @TableField("fee_name")
    private String feeName;

    /**
     * 费用金额
     */
    @NotNull(message = "费用金额不能为空")
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 币种
     */
    @NotNull(message = "币种不能为空")
    @TableField("currency")
    private CurrencyEnum currency;

    /**
     * 费用发生时间
     */
    @NotNull(message = "费用发生时间不能为空")
    @TableField("fee_time")
    private LocalDateTime feeTime;

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
     * 费用说明
     */
    @Size(max = 500, message = "费用说明长度不能超过500个字符")
    @TableField("description")
    private String description;

    /**
     * 关联部门ID
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    @Override
    public void preInsert() {
        super.preInsert();
        if (this.feeTime == null) {
            this.feeTime = LocalDateTime.now();
        }
    }
} 