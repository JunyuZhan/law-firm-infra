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
 * 用户-角色关联实体
 */
@Data
@Entity
@Table(name = "auth_user_role")
@TableName("auth_user_role")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserRole extends TenantEntity {
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    @TableField("user_id")
    private Long userId;
    
    /**
     * 角色ID
     */
    @Column(name = "role_id", nullable = false)
    @TableField("role_id")
    private Long roleId;
} 