package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户-职位关联实体
 */
@Data
@TableName("auth_user_position")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserPosition extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 职位ID
     */
    @TableField("position_id")
    private Long positionId;
    
    /**
     * 是否主职位（0-否，1-是）
     */
    @TableField("is_primary")
    private Integer isPrimary;
} 