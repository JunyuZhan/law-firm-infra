package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户部门关联实体
 */
@Data
@TableName("auth_user_department")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserDepartment extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 部门ID
     */
    @TableField("department_id")
    private Long departmentId;
    
    /**
     * 是否主部门（0-否，1-是）
     */
    @TableField("is_primary")
    private Integer isPrimary;
} 