package com.lawfirm.model.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 合同实体类
 * 主要用于律师事务所与客户之间的法律服务委托代理合同
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
    private String contractType; // 合同类型(诉讼代理、非诉讼法律服务、法律顾问、尽职调查、知识产权代理等)
    
    @TableField("status")
    private Integer status;     // 合同状态
    
    @TableField("amount")
    private Double amount;      // 合同基础金额
    
    @TableField("signing_date")
    private Date signingDate;   // 签约日期
    
    @TableField("client_id")
    private Long clientId;      // 客户ID
    
    @TableField("case_id")
    private Long caseId;        // 关联案件ID
    
    @TableField("effective_date")
    private Date effectiveDate; // 生效日期
    
    @TableField("expiry_date")
    private Date expiryDate;    // 到期日期
    
    @TableField("service_scope")
    private String serviceScope; // 服务范围
    
    @TableField("delegate_power")
    private String delegatePower; // 授权范围(全权委托、特别授权、一般授权等)
    
    @TableField("fee_type")
    private String feeType;     // 收费类型(固定费用、计时收费、风险代理等)
    
    @TableField("payment_terms")
    private String paymentTerms; // 付款条件
    
    @TableField("lead_attorney_id")
    private Long leadAttorneyId; // 主办律师ID
    
    @TableField("department_id")
    private Long departmentId;  // 承办部门ID
    
    @TableField("contract_text")
    private String contractText; // 合同正文内容
    
    @TableField("confidentiality_level")
    private Integer confidentialityLevel; // 保密级别
    
    @TableField("remarks")
    private String remarks;     // 备注说明
    
    @TableField("template_id")
    private Long templateId;    // 合同模板ID
    
    @TableField("version")
    private Integer version;    // 合同版本号
} 