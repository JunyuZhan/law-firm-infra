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
 * 合同变更实体类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("contract_change")
@Schema(description = "合同变更实体类")
public class ContractChange extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "合同ID")
    @TableField("contract_id")
    private Long contractId;       // 合同ID
    
    @Schema(description = "变更类型")
    @TableField("change_type")
    private String changeType;     // 变更类型
    
    @Schema(description = "变更内容")
    @TableField("change_content")
    private String changeContent;  // 变更内容
    
    @Schema(description = "变更原因")
    @TableField("change_reason")
    private String changeReason;   // 变更原因
    
    @Schema(description = "变更日期")
    @TableField("change_date")
    private Date changeDate;       // 变更日期
    
    @Schema(description = "变更状态（0-待审批 1-已通过 2-已拒绝）")
    @TableField("change_status")
    private Integer changeStatus;  // 变更状态（0-待审批 1-已通过 2-已拒绝）
    
    @Schema(description = "审批人ID")
    @TableField("approver_id")
    private Long approverId;       // 审批人ID
    
    @Schema(description = "审批时间")
    @TableField("approval_time")
    private Date approvalTime;     // 审批时间
    
    @Schema(description = "审批意见")
    @TableField("approval_opinion")
    private String approvalOpinion; // 审批意见
} 