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
 * 权限实体
 */
@Data
@Entity
@Table(name = "auth_permission")
@TableName("auth_permission")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Permission extends TenantEntity {
    
    /**
     * 权限名称
     */
    @Column(name = "name", nullable = false, length = 50)
    @TableField("name")
    private String name;
    
    /**
     * 权限编码
     */
    @Column(name = "code", nullable = false, length = 100)
    @TableField("code")
    private String code;
    
    /**
     * 权限类型（0-菜单，1-按钮，2-API）
     */
    @Column(name = "type", nullable = false)
    @TableField("type")
    private Integer type;
    
    /**
     * 菜单图标
     */
    @Column(name = "icon", length = 100)
    @TableField("icon")
    private String icon;
    
    /**
     * 路由路径
     */
    @Column(name = "path", length = 200)
    @TableField("path")
    private String path;
    
    /**
     * 组件路径
     */
    @Column(name = "component", length = 255)
    @TableField("component")
    private String component;
    
    /**
     * 权限标识
     */
    @Column(name = "permission", length = 100)
    @TableField("permission")
    private String permission;
    
    /**
     * 父级ID
     */
    @Column(name = "parent_id")
    @TableField("parent_id")
    private Long parentId;
    
    /**
     * 显示顺序
     */
    @Column(name = "sort", nullable = false)
    @TableField("sort")
    private Integer sort;
    
    /**
     * 状态（0-正常，1-禁用）
     */
    @Column(name = "status", nullable = false)
    @TableField("status")
    private Integer status;
    
    /**
     * 是否外链（0-否，1-是）
     */
    @Column(name = "external", nullable = false)
    @TableField("external")
    private Integer external;
    
    /**
     * 是否缓存（0-否，1-是）
     */
    @Column(name = "cache", nullable = false)
    @TableField("cache")
    private Integer cache;
    
    /**
     * 是否显示（0-否，1-是）
     */
    @Column(name = "visible", nullable = false)
    @TableField("visible")
    private Integer visible;
    
    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    @TableField("remark")
    private String remark;
} 