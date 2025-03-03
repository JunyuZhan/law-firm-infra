package com.lawfirm.model.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 合同实体类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("contract_info")
public class Contract extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @TableField("contract_no")
    private String contractNo;  // 合同编号
    
    @TableField("contract_name")
    private String contractName; // 合同名称
    
    @TableField("contract_type")
    private String contractType; // 合同类型
    
    @TableField("status")
    private Integer status;       // 合同状态
    
    @TableField("amount")
    private double amount;       // 合同金额
    
    @TableField("signing_date")
    private String signingDate;  // 签约日期
    
    @TableField("client_id")
    private Long clientId;       // 客户ID
} 