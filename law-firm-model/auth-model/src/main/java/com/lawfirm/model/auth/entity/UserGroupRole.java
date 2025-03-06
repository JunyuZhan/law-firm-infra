package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户组-角色关联实体
 */
@Data
@TableName("auth_user_group_role")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserGroupRole extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户组ID
     */
    @TableField("user_group_id")
    private Long userGroupId;
    
    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;
} 