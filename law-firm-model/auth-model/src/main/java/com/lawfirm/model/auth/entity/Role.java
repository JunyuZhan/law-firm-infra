package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

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
     * 父级角色ID，用于角色继承
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
     * 备注
     */
    @TableField("remark")
    private String remark;
    
    /**
     * 角色关联的权限列表
     */
    @TableField(exist = false)
    private transient List<Permission> permissions;
    
    /**
     * 获取角色标识（兼容性方法，返回code）
     */
    public String getRoleKey() {
        return this.code;
    }
    
    /**
     * 设置角色标识（兼容性方法，设置code）
     */
    public void setRoleKey(String roleKey) {
        this.code = roleKey;
    }
    
    /**
     * 获取角色排序（兼容性方法，返回sort）
     */
    public Integer getRoleSort() {
        return this.sort;
    }
    
    /**
     * 设置角色排序（兼容性方法，设置sort）
     */
    public void setRoleSort(Integer roleSort) {
        this.sort = roleSort;
    }
    
    /**
     * 获取角色名称（兼容性方法，返回name）
     */
    public String getRoleName() {
        return this.name;
    }
    
    /**
     * 设置角色名称（兼容性方法，设置name）
     */
    public void setRoleName(String roleName) {
        this.name = roleName;
    }
} 