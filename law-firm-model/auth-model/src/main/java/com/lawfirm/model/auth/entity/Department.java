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
 * 部门实体
 */
@Data
@Entity
@Table(name = "auth_department")
@TableName("auth_department")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Department extends TenantEntity {
    
    /**
     * 部门名称
     */
    @Column(name = "name", nullable = false, length = 50)
    @TableField("name")
    private String name;
    
    /**
     * 部门编码
     */
    @Column(name = "code", nullable = false, length = 100)
    @TableField("code")
    private String code;
    
    /**
     * 部门类型（0-公司，1-部门，2-团队）
     */
    @Column(name = "type", nullable = false)
    @TableField("type")
    private Integer type;
    
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
     * 负责人ID
     */
    @Column(name = "leader_id")
    @TableField("leader_id")
    private Long leaderId;
    
    /**
     * 联系电话
     */
    @Column(name = "phone", length = 20)
    @TableField("phone")
    private String phone;
    
    /**
     * 邮箱
     */
    @Column(name = "email", length = 100)
    @TableField("email")
    private String email;
    
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