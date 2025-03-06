package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 部门-角色关联实体
 */
@Data
@TableName("auth_department_role")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DepartmentRole extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 部门ID
     */
    @TableField("department_id")
    private Long departmentId;
    
    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;
    
    /**
     * 数据范围（0-本部门及以下数据，1-本部门数据，2-自定义数据）
     */
    @TableField("data_scope")
    private Integer dataScope;
} 