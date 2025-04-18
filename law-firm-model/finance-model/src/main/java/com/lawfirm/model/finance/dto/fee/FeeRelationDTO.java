package com.lawfirm.model.finance.dto.fee;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 费用关联数据传输对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FeeRelationDTO extends BaseDTO {

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
     * 来源类型（CONTRACT-合同费用，CASE-案件费用）
     */
    private String sourceType;
    
    /**
     * 来源ID（合同费用ID或案件费用ID）
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
     * 关联案件ID
     */
    private Long caseId;
    
    /**
     * 关联案件编号
     */
    private String caseNumber;
    
    /**
     * 关联类型
     */
    private String relationType;
    
    /**
     * 费用金额
     */
    private java.math.BigDecimal amount;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 费用状态
     */
    private String feeStatus;
    
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