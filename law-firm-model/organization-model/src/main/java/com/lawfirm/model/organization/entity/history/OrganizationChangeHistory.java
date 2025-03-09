package com.lawfirm.model.organization.entity.history;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 组织变更历史实体
 * 用于记录组织结构的所有变更
 */
@Data
@TableName("org_change_history")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class OrganizationChangeHistory extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组织ID
     */
    @TableField(value = "organization_id")
    private Long organizationId;
    
    /**
     * 组织类型
     */
    @TableField(value = "organization_type")
    private String organizationType;
    
    /**
     * 变更类型（CREATE、UPDATE、DELETE、MOVE、MERGE、SPLIT）
     */
    @TableField(value = "change_type")
    private String changeType;
    
    /**
     * 变更前数据（JSON格式）
     */
    @TableField(value = "before_change")
    private String beforeChange;
    
    /**
     * 变更后数据（JSON格式）
     */
    @TableField(value = "after_change")
    private String afterChange;
    
    /**
     * 相关联的组织ID（如移动时的目标组织ID）
     */
    @TableField(value = "related_organization_id")
    private Long relatedOrganizationId;
    
    /**
     * 操作人ID
     */
    @TableField(value = "operator_id")
    private Long operatorId;
    
    /**
     * 操作人姓名
     */
    @TableField(value = "operator_name")
    private String operatorName;
    
    /**
     * 操作时间
     */
    @TableField(value = "operate_time")
    private LocalDateTime operateTime;
    
    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
} 