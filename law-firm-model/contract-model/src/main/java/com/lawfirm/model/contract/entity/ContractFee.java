package com.lawfirm.model.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * 合同收费实体类
 * 用于详细记录合同的收费结构，支持多种收费方式组合
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("contract_fee")
@Schema(description = "合同收费实体类，用于详细记录合同的收费结构，支持多种收费方式组合")
public class ContractFee extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "关联的合同ID")
    @TableField("contract_id")
    private Long contractId;      // 关联的合同ID
    
    @Schema(description = "收费类型(固定费用、计时收费、风险代理、阶段收费等)")
    @TableField("fee_type")
    private String feeType;       // 收费类型(固定费用、计时收费、风险代理、阶段收费等)
    
    @Schema(description = "费用名称")
    @TableField("fee_name")
    private String feeName;       // 费用名称
    
    @Schema(description = "费用金额")
    @TableField("fee_amount")
    private Double feeAmount;     // 费用金额
    
    @Schema(description = "币种")
    @TableField("currency")
    private String currency;      // 币种
    
    @Schema(description = "费率(适用于风险代理类型)")
    @TableField("rate")
    private Double rate;          // 费率(适用于风险代理类型)
    
    @Schema(description = "计算方式说明")
    @TableField("calculation_method")
    private String calculationMethod; // 计算方式说明
    
    @Schema(description = "应付日期")
    @TableField("due_date")
    private Date dueDate;         // 应付日期
    
    @Schema(description = "支付状态(0-未支付，1-部分支付，2-已支付)")
    @TableField("payment_status")
    private Integer paymentStatus; // 支付状态(0-未支付，1-部分支付，2-已支付)
    
    @Schema(description = "已支付金额")
    @TableField("paid_amount")
    private Double paidAmount;    // 已支付金额
    
    @Schema(description = "是否含税")
    @TableField("is_taxable")
    private Boolean isTaxable;    // 是否含税
    
    @Schema(description = "税率")
    @TableField("tax_rate")
    private Double taxRate;       // 税率
    
    @Schema(description = "收费条件说明")
    @TableField("condition_desc")
    private String conditionDesc; // 收费条件说明
    
    @Schema(description = "备注说明")
    @TableField("remarks")
    private String remarks;       // 备注说明
} 