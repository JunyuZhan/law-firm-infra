package com.lawfirm.model.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 合同审核实体类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("contract_review")
@Schema(description = "合同审核实体类")
public class ContractReview extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "合同ID")
    @TableField("contract_id")
    private Long contractId;       // 合同ID
    
    @Schema(description = "审核人")
    @TableField("reviewer")
    private String reviewer;        // 审核人
    
    @Schema(description = "审核状态")
    @TableField("review_status")
    private String reviewStatus;    // 审核状态
    
    @Schema(description = "审核意见")
    @TableField("review_comments")
    private String reviewComments;   // 审核意见
} 