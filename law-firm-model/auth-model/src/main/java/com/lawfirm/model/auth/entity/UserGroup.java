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
 * 用户组实体
 */
@Data
@Entity
@Table(name = "auth_user_group")
@TableName("auth_user_group")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserGroup extends TenantEntity {
    
    /**
     * 用户组名称
     */
    @Column(name = "name", nullable = false, length = 50)
    @TableField("name")
    private String name;
    
    /**
     * 用户组编码
     */
    @Column(name = "code", nullable = false, length = 100)
    @TableField("code")
    private String code;
    
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
     * 备注
     */
    @Column(name = "remark", length = 500)
    @TableField("remark")
    private String remark;
} 