package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 权限申请实体
 */
@Data
@TableName("sys_permission_request")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PermissionRequest extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 状态 - 待审批
     */
    public static final int STATUS_PENDING = 0;
    
    /**
     * 状态 - 已批准
     */
    public static final int STATUS_APPROVED = 1;
    
    /**
     * 状态 - 已拒绝
     */
    public static final int STATUS_REJECTED = 2;
    
    /**
     * 申请用户ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 业务类型
     */
    @TableField("business_type")
    private String businessType;
    
    /**
     * 业务ID
     */
    @TableField("business_id")
    private Long businessId;
    
    /**
     * 权限编码
     */
    @TableField("permission_code")
    private String permissionCode;
    
    /**
     * 申请理由
     */
    @TableField("reason")
    private String reason;
    
    /**
     * 状态：0-待审批, 1-已批准, 2-已拒绝
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 审批人ID
     */
    @TableField("approver_id")
    private Long approverId;
    
    /**
     * 审批时间
     */
    @TableField("approve_time")
    private LocalDateTime approveTime;
    
    /**
     * 审批备注
     */
    @TableField("approve_remark")
    private String approveRemark;
    
    /**
     * 权限过期时间
     */
    @TableField("expire_time")
    private LocalDateTime expireTime;
    
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
} 