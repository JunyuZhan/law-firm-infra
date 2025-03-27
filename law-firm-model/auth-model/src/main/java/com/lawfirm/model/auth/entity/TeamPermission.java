package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 团队权限关联实体
 */
@Data
@TableName("sys_team_permission")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TeamPermission extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 团队ID
     */
    @TableField("team_id")
    private Long teamId;
    
    /**
     * 权限ID
     */
    @TableField("permission_id")
    private Long permissionId;
    
    /**
     * 用户ID（可选，如果为null表示团队所有成员都具有该权限）
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 资源类型（可选，用于记录权限针对的具体资源类型）
     */
    @TableField("resource_type")
    private String resourceType;
    
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    
    /**
     * 是否删除：0-未删除，1-已删除
     */
    @TableField("deleted")
    private Integer deleted;
    
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
} 