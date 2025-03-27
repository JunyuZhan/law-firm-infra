package com.lawfirm.model.auth.dto.permission;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 权限审批DTO
 */
@Data
public class PermissionApprovalDTO {
    
    /**
     * 申请ID
     */
    @NotNull(message = "申请ID不能为空")
    private Long requestId;
    
    /**
     * 是否批准
     */
    @NotNull(message = "审批结果不能为空")
    private Boolean approved;
    
    /**
     * 审批备注
     */
    private String remark;
}
