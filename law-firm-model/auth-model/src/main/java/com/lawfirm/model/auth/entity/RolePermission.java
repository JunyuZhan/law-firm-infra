package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色-权限关联实体
 */
@Data
@Entity
@Table(name = "auth_role_permission")
@TableName("auth_role_permission")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RolePermission extends TenantEntity {
    
    /**
     * 角色ID
     */
    @Column(name = "role_id", nullable = false)
    @TableField("role_id")
    private Long roleId;
    
    /**
     * 权限ID
     */
    @Column(name = "permission_id", nullable = false)
    @TableField("permission_id")
    private Long permissionId;
} 