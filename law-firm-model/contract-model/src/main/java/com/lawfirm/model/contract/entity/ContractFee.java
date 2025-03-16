package com.lawfirm.model.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 合同收费实体类
 * 用于详细记录合同的收费结构，支持多种收费方式组合
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("contract_fee")
public class ContractFee extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @TableField("contract_id")
    private Long contractId;      // 关联的合同ID
    
    @TableField("fee_type")
    private String feeType;       // 收费类型(固定费用、计时收费、风险代理、阶段收费等)
    
    @TableField("fee_name")
    private String feeName;       // 费用名称
    
    @TableField("fee_amount")
    private Double feeAmount;     // 费用金额
    
    @TableField("currency")
    private String currency;      // 币种
    
    @TableField("rate")
    private Double rate;          // 费率(适用于风险代理类型)
    
    @TableField("calculation_method")
    private String calculationMethod; // 计算方式说明
    
    @TableField("due_date")
    private Date dueDate;         // 应付日期
    
    @TableField("payment_status")
    private Integer paymentStatus; // 支付状态(0-未支付，1-部分支付，2-已支付)
    
    @TableField("paid_amount")
    private Double paidAmount;    // 已支付金额
    
    @TableField("is_taxable")
    private Boolean isTaxable;    // 是否含税
    
    @TableField("tax_rate")
    private Double taxRate;       // 税率
    
    @TableField("condition_desc")
    private String conditionDesc; // 收费条件说明
    
    @TableField("remarks")
    private String remarks;       // 备注说明
} 