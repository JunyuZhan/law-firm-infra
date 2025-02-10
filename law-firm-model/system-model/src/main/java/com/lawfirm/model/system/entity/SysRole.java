package com.lawfirm.model.system.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色实体
 */
@Data
@Entity
@Table(name = "sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends ModelBaseEntity<SysRole> {

    /**
     * 角色名称
     */
    @Column(name = "name", nullable = false)
    private String name;
    
    /**
     * 角色编码
     */
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    
    /**
     * 角色描述
     */
    @Column(name = "description")
    private String description;
    
    /**
     * 是否启用
     */
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
    
    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @Column(name = "data_scope", nullable = false)
    private Integer dataScope = 1;
} 