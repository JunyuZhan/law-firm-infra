package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 职位实体
 */
@Data
@TableName("auth_position")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Position extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 职位名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 职位编码
     */
    @TableField("code")
    private String code;
    
    /**
     * 职位级别（0-高层，1-中层，2-基层）
     */
    @TableField("level")
    private Integer level;
    
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