package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 职位权限关联实体
 */
@Data
@TableName("auth_position_permission")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PositionPermission extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 职位ID
     */
    @TableField("position_id")
    private Long positionId;
    
    /**
     * 权限ID
     */
    @TableField("permission_id")
    private Long permissionId;
} 