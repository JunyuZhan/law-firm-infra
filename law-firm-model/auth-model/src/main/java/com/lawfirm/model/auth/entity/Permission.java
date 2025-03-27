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
     * 权限类型 - 菜单
     */
    public static final int TYPE_MENU = 0;
    
    /**
     * 权限类型 - 按钮
     */
    public static final int TYPE_BUTTON = 1;
    
    /**
     * 权限类型 - API接口
     */
    public static final int TYPE_API = 2;
    
    /**
     * 操作类型 - 完整权限（增删改查）
     */
    public static final int OPERATION_FULL = 0;
    
    /**
     * 操作类型 - 只读权限
     */
    public static final int OPERATION_READ = 1;
    
    /**
     * 操作类型 - 申请权限
     */
    public static final int OPERATION_APPLY = 2;
    
    /**
     * 操作类型 - 创建权限
     */
    public static final int OPERATION_CREATE = 3;
    
    /**
     * 操作类型 - 编辑权限
     */
    public static final int OPERATION_EDIT = 4;
    
    /**
     * 操作类型 - 删除权限
     */
    public static final int OPERATION_DELETE = 5;
    
    /**
     * 操作类型 - 审批权限
     */
    public static final int OPERATION_APPROVE = 6;
    
    /**
     * 数据范围 - 全所数据
     */
    public static final int DATA_SCOPE_GLOBAL = 0;
    
    /**
     * 数据范围 - 团队数据
     */
    public static final int DATA_SCOPE_TEAM = 1;
    
    /**
     * 数据范围 - 个人数据
     */
    public static final int DATA_SCOPE_PERSONAL = 2;
    
    /**
     * 数据范围 - 部门数据
     */
    public static final int DATA_SCOPE_DEPARTMENT = 3;
    
    /**
     * 数据范围 - 自定义数据
     */
    public static final int DATA_SCOPE_CUSTOM = 4;
    
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
     * 操作类型（0-完全权限，1-只读权限，2-申请权限，3-创建权限，4-编辑权限，5-删除权限，6-审批权限）
     */
    @TableField("operation_type")
    private Integer operationType;
    
    /**
     * 数据权限范围（0-全所数据，1-团队数据，2-个人数据，3-部门数据，4-自定义数据）
     */
    @TableField("data_scope")
    private Integer dataScope;
    
    /**
     * 业务模块
     */
    @TableField("module")
    private String module;
    
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
    
    /**
     * 获取权限标识（兼容性方法，返回permission或code）
     */
    public String getPermissionKey() {
        return this.permission != null ? this.permission : this.code;
    }
    
    /**
     * 设置权限标识（兼容性方法，设置permission）
     */
    public void setPermissionKey(String permissionKey) {
        this.permission = permissionKey;
    }
    
    /**
     * 获取菜单ID（兼容性方法，对于菜单类型返回自身ID）
     */
    public Long getMenuId() {
        // 如果是菜单类型(type=0)，返回自身ID，否则返回父ID
        return type != null && type == 0 ? this.getId() : this.parentId;
    }
    
    /**
     * 设置菜单ID（兼容性方法）
     */
    public void setMenuId(Long menuId) {
        if (type != null && type != 0) {
            this.parentId = menuId;
        }
    }
    
    /**
     * 获取权限排序（兼容性方法，返回sort）
     */
    public Integer getPermissionSort() {
        return this.sort;
    }
    
    /**
     * 设置权限排序（兼容性方法，设置sort）
     */
    public void setPermissionSort(Integer permissionSort) {
        this.sort = permissionSort;
    }
} 