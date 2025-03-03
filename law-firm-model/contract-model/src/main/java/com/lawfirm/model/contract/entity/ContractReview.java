package com.lawfirm.model.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 合同审核实体类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("contract_review")
public class ContractReview extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @TableField("contract_id")
    private Long contractId;       // 合同ID
    
    @TableField("reviewer")
    private String reviewer;        // 审核人
    
    @TableField("review_status")
    private String reviewStatus;    // 审核状态
    
    @TableField("review_comments")
    private String reviewComments;   // 审核意见
} 