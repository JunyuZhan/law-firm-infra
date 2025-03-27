package com.lawfirm.model.auth.dto.permission;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 权限申请DTO
 */
@Data
public class PermissionRequestDTO {
    
    /**
     * 业务类型
     */
    private String businessType;
    
    /**
     * 业务ID
     */
    private Long businessId;
    
    /**
     * 权限编码
     */
    @NotBlank(message = "权限编码不能为空")
    private String permissionCode;
    
    /**
     * 申请理由
     */
    @NotBlank(message = "申请理由不能为空")
    private String reason;
    
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
} 