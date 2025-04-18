package com.lawfirm.model.finance.vo.fee;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 费用关联视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FeeRelationVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 财务费用ID
     */
    private Long financeFeeId;
    
    /**
     * 财务费用编号
     */
    private String financeFeeNumber;
    
    /**
     * 财务费用名称
     */
    private String financeFeeTitle;
    
    /**
     * 来源类型显示名称（合同费用/案件费用）
     */
    private String sourceTypeText;
    
    /**
     * 来源ID
     */
    private Long sourceId;
    
    /**
     * 关联合同ID
     */
    private Long contractId;
    
    /**
     * 关联合同编号
     */
    private String contractNumber;
    
    /**
     * 关联合同名称
     */
    private String contractName;
    
    /**
     * 关联案件ID
     */
    private Long caseId;
    
    /**
     * 关联案件编号
     */
    private String caseNumber;
    
    /**
     * 关联案件名称
     */
    private String caseName;
    
    /**
     * 关联客户名称
     */
    private String clientName;
    
    /**
     * 费用金额
     */
    private BigDecimal amount;
    
    /**
     * 已支付金额
     */
    private BigDecimal paidAmount;
    
    /**
     * 未支付金额
     */
    private BigDecimal unpaidAmount;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 费用类型
     */
    private String feeType;
    
    /**
     * 费用类型文本
     */
    private String feeTypeText;
    
    /**
     * 费用状态
     */
    private String feeStatus;
    
    /**
     * 费用状态文本
     */
    private String feeStatusText;
    
    /**
     * 创建时间
     */
    private transient LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private transient LocalDateTime updateTime;
    
    /**
     * 备注
     */
    private String remarks;
} 