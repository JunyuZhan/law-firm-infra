package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户角色关联实体 - 建立用户与角色的多对多关系
 * 仅包含基本的关联关系，不包含业务特定字段
 */
@Data
@TableName("auth_user_role")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserRole extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;
} 