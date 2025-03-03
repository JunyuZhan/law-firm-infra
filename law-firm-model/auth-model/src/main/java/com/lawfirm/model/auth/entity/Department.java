package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 部门实体
 */
@Data
@TableName("auth_department")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Department extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 部门名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 部门编码
     */
    @TableField("code")
    private String code;
    
    /**
     * 部门类型（0-公司，1-部门，2-团队）
     */
    @TableField("type")
    private Integer type;
    
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
     * 负责人ID
     */
    @TableField("leader_id")
    private Long leaderId;
    
    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;
    
    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    
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