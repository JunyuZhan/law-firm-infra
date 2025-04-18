package com.lawfirm.model.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 费用关联实体类
 * 用于建立财务费用与其他模块费用之间的关联关系
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_fee_relation")
public class FeeRelation extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 财务费用ID
     */
    @TableField("finance_fee_id")
    private Long financeFeeId;
    
    /**
     * 来源类型（CONTRACT-合同费用，CASE-案件费用）
     */
    @TableField("source_type")
    private String sourceType;
    
    /**
     * 来源ID（合同费用ID或案件费用ID）
     */
    @TableField("source_id")
    private Long sourceId;
    
    /**
     * 关联合同ID
     */
    @TableField("contract_id")
    private Long contractId;
    
    /**
     * 关联案件ID
     */
    @TableField("case_id")
    private Long caseId;
    
    /**
     * 关联类型
     */
    @TableField("relation_type")
    private String relationType;
    
    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;
} 