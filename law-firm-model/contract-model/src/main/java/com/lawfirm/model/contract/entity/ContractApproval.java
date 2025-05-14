package com.lawfirm.model.contract.entity;

import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 合同审批实体类
 */
@Data
@Schema(description = "合同审批实体类")
public class ContractApproval {
    
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;
    
    /**
     * 合同ID
     */
    @Schema(description = "合同ID")
    private Long contractId;
    
    /**
     * 审批人ID
     */
    @Schema(description = "审批人ID")
    private Long approverId;
    
    /**
     * 审批人姓名
     */
    @Schema(description = "审批人姓名")
    private String approverName;
    
    /**
     * 审批节点（1-部门负责人 2-分所负责人 3-法务审核 4-财务审核）
     */
    @Schema(description = "审批节点（1-部门负责人 2-分所负责人 3-法务审核 4-财务审核）")
    private Integer node;
    
    /**
     * 审批状态（0-待审批 1-通过 2-驳回）
     */
    @Schema(description = "审批状态（0-待审批 1-通过 2-驳回）")
    private Integer status;
    
    /**
     * 审批意见
     */
    @Schema(description = "审批意见")
    private String comment;
    
    /**
     * 审批时间
     */
    @Schema(description = "审批时间")
    private LocalDateTime approvalTime;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private Long createBy;
    
    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private Long updateBy;
    
    /**
     * 是否删除
     */
    @Schema(description = "是否删除")
    private Integer deleted;
} 