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
 * 职位实体
 */
@Data
@Entity
@Table(name = "auth_position")
@TableName("auth_position")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Position extends TenantEntity {
    
    /**
     * 职位名称
     */
    @Column(name = "name", nullable = false, length = 50)
    @TableField("name")
    private String name;
    
    /**
     * 职位编码
     */
    @Column(name = "code", nullable = false, length = 100)
    @TableField("code")
    private String code;
    
    /**
     * 职位级别（0-高层，1-中层，2-基层）
     */
    @Column(name = "level", nullable = false)
    @TableField("level")
    private Integer level;
    
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