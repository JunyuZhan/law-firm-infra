package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色权限关联实体 - 建立角色与权限的多对多关系
 * 仅包含基本的关联关系，不包含业务特定字段
 */
@Data
@TableName("auth_role_permission")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RolePermission extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;
    
    /**
     * 权限ID
     */
    @TableField("permission_id")
    private Long permissionId;
} 