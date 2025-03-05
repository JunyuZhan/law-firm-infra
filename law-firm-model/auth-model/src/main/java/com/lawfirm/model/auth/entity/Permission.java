package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 权限实体
 */
@Data
@TableName("auth_permission")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Permission extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 权限名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 权限编码
     */
    @TableField("code")
    private String code;
    
    /**
     * 权限类型（0-菜单，1-按钮，2-API）
     */
    @TableField("type")
    private Integer type;
    
    /**
     * 操作类型（0-完全权限，1-只读权限，2-申请权限）
     */
    @TableField("operation_type")
    private Integer operationType;
    
    /**
     * 数据权限范围（0-全部数据，1-部门数据，2-个人数据，3-自定义数据）
     */
    @TableField("data_scope")
    private Integer dataScope;
    
    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;
    
    /**
     * 路由路径
     */
    @TableField("path")
    private String path;
    
    /**
     * 组件路径
     */
    @TableField("component")
    private String component;
    
    /**
     * 权限标识
     */
    @TableField("permission")
    private String permission;
    
    /**
     * 父级ID
     */
    @TableField("parent_id")
    private Long parentId;
    
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
     * 是否外链（0-否，1-是）
     */
    @TableField("external")
    private Integer external;
    
    /**
     * 是否缓存（0-否，1-是）
     */
    @TableField("cache")
    private Integer cache;
    
    /**
     * 是否显示（0-否，1-是）
     */
    @TableField("visible")
    private Integer visible;
    
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
} 