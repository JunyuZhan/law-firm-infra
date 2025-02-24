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
 * 角色实体
 */
@Data
@Entity
@Table(name = "auth_role")
@TableName("auth_role")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Role extends TenantEntity {
    
    /**
     * 角色名称
     */
    @Column(name = "name", nullable = false, length = 50)
    @TableField("name")
    private String name;
    
    /**
     * 角色编码
     */
    @Column(name = "code", nullable = false, length = 100)
    @TableField("code")
    private String code;
    
    /**
     * 角色类型（0-系统角色，1-自定义角色）
     */
    @Column(name = "type", nullable = false)
    @TableField("type")
    private Integer type;
    
    /**
     * 数据范围（0-全部数据，1-本部门及以下数据，2-本部门数据，3-个人数据，4-自定义数据）
     */
    @Column(name = "data_scope", nullable = false)
    @TableField("data_scope")
    private Integer dataScope;
    
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
     * 备注
     */
    @Column(name = "remark", length = 500)
    @TableField("remark")
    private String remark;
} 