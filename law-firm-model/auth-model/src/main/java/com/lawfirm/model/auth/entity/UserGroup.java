package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户组实体
 */
@Data
@TableName("auth_user_group")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserGroup extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户组名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 用户组编码
     */
    @TableField("code")
    private String code;
    
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
     * 备注
     */
    @TableField("remark")
    private String remark;
} 