package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色实体
 */
@Data
@TableName("auth_role")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Role extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 角色名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 角色编码
     */
    @TableField("code")
    private String code;
    
    /**
     * 角色类型（0-系统角色，1-自定义角色）
     */
    @TableField("type")
    private Integer type;
    
    /**
     * 数据范围（0-全部数据，1-本部门及以下数据，2-本部门数据，3-个人数据，4-自定义数据）
     */
    @TableField("data_scope")
    private Integer dataScope;
    
    /**
     * 显示顺序
     */
    @TableField("sort")
    private Integer sort;
    
    /**
     * 状态（0-正常，1-禁用）
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
} 