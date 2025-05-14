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
 * 合同实体类
 * 主要用于律师事务所与客户之间的法律服务委托代理合同
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("contract_info")
@Schema(description = "合同实体类，主要用于律师事务所与客户之间的法律服务委托代理合同")
public class Contract extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "合同编号")
    @TableField("contract_no")
    private String contractNo;  // 合同编号
    
    @Schema(description = "合同名称")
    @TableField("contract_name")
    private String contractName; // 合同名称
    
    @Schema(description = "合同类型(诉讼代理、非诉讼法律服务、法律顾问、尽职调查、知识产权代理等)")
    @TableField("contract_type")
    private String contractType; // 合同类型(诉讼代理、非诉讼法律服务、法律顾问、尽职调查、知识产权代理等)
    
    @Schema(description = "合同状态")
    @TableField("status")
    private Integer status;     // 合同状态
    
    @Schema(description = "合同基础金额")
    @TableField("amount")
    private Double amount;      // 合同基础金额
    
    @Schema(description = "签约日期")
    @TableField("signing_date")
    private Date signingDate;   // 签约日期
    
    @Schema(description = "客户ID")
    @TableField("client_id")
    private Long clientId;      // 客户ID
    
    @Schema(description = "关联案件ID")
    @TableField("case_id")
    private Long caseId;        // 关联案件ID
    
    @Schema(description = "生效日期")
    @TableField("effective_date")
    private Date effectiveDate; // 生效日期
    
    @Schema(description = "到期日期")
    @TableField("expiry_date")
    private Date expiryDate;    // 到期日期
    
    @Schema(description = "服务范围")
    @TableField("service_scope")
    private String serviceScope; // 服务范围
    
    @Schema(description = "授权范围(全权委托、特别授权、一般授权等)")
    @TableField("delegate_power")
    private String delegatePower; // 授权范围(全权委托、特别授权、一般授权等)
    
    @Schema(description = "收费类型(固定费用、计时收费、风险代理等)")
    @TableField("fee_type")
    private String feeType;     // 收费类型(固定费用、计时收费、风险代理等)
    
    @Schema(description = "付款条件")
    @TableField("payment_terms")
    private String paymentTerms; // 付款条件
    
    @Schema(description = "主办律师ID")
    @TableField("lead_attorney_id")
    private Long leadAttorneyId; // 主办律师ID
    
    @Schema(description = "承办部门ID")
    @TableField("department_id")
    private Long departmentId;  // 承办部门ID
    
    @Schema(description = "合同正文内容")
    @TableField("contract_text")
    private String contractText; // 合同正文内容
    
    @Schema(description = "保密级别")
    @TableField("confidentiality_level")
    private Integer confidentialityLevel; // 保密级别
    
    @Schema(description = "备注说明")
    @TableField("remarks")
    private String remarks;     // 备注说明
    
    @Schema(description = "合同模板ID")
    @TableField("template_id")
    private Long templateId;    // 合同模板ID
    
    @Schema(description = "合同版本号")
    @TableField("version")
    private Integer version;    // 合同版本号
} 