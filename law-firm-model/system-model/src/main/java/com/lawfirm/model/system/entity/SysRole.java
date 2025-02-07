package com.lawfirm.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lawfirm.common.data.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

/**
 * 系统角色实体
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;
    
    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;
    
    /**
     * 显示顺序
     */
    @TableField("order_num")
    private Integer orderNum;
    
    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @TableField("data_scope")
    private Integer dataScope;
    
    /**
     * 角色描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 是否系统内置（0-否，1-是）
     */
    @TableField("is_system")
    private Boolean isSystem;
    
    @TableField("is_default")
    private Boolean isDefault;
    
    @TableField("status")
    private Integer status;
    
    @Override
    public void setRemark(String remark) {
        super.setRemark(remark);
    }
} 