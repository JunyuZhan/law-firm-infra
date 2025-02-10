package com.lawfirm.cases.model.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 案件立案审批DTO
 */
@Data
public class CaseApprovalDTO {
    
    /**
     * 审批人ID
     */
    @NotNull(message = "审批人ID不能为空")
    private Long approverId;
    
    /**
     * 审批人姓名
     */
    @NotBlank(message = "审批人姓名不能为空")
    private String approverName;
    
    /**
     * 审批意见
     */
    @NotBlank(message = "审批意见不能为空")
    private String approvalOpinion;
    
    /**
     * 审批结果（true-通过，false-驳回）
     */
    @NotNull(message = "审批结果不能为空")
    private Boolean approved;
    
    /**
     * 审批时间
     */
    @NotNull(message = "审批时间不能为空")
    private LocalDateTime approvalTime;
    
    /**
     * 备注
     */
    private String remarks;
} 