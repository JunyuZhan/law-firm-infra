package com.lawfirm.model.auth.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 权限申请VO
 */
@Data
public class PermissionRequestVO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 申请用户ID
     */
    private Long userId;
    
    /**
     * 申请用户名称
     */
    private String userName;
    
    /**
     * 业务类型
     */
    private String businessType;
    
    /**
     * 业务ID
     */
    private Long businessId;
    
    /**
     * 业务名称
     */
    private String businessName;
    
    /**
     * 权限编码
     */
    private String permissionCode;
    
    /**
     * 权限名称
     */
    private String permissionName;
    
    /**
     * 申请理由
     */
    private String reason;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 状态名称
     */
    private String statusName;
    
    /**
     * 审批人ID
     */
    private Long approverId;
    
    /**
     * 审批人名称
     */
    private String approverName;
    
    /**
     * 审批时间
     */
    private LocalDateTime approveTime;
    
    /**
     * 审批备注
     */
    private String approveRemark;
    
    /**
     *
     * 权限过期时间
     */
    private LocalDateTime expireTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
} 