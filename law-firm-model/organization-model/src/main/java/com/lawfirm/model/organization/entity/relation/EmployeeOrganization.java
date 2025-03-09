package com.lawfirm.model.organization.entity.relation;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 员工-组织关系实体
 * 用于记录员工与组织之间的关联关系
 */
@Data
@TableName("org_employee_relation")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class EmployeeOrganization extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    @TableField(value = "employee_id")
    private Long employeeId;
    
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
     * 关系类型（PRIMARY-主要, SECONDARY-次要, TEMPORARY-临时）
     */
    @TableField(value = "relation_type")
    private String relationType;
    
    /**
     * 职位ID
     */
    @TableField(value = "position_id")
    private Long positionId;
    
    /**
     * 职位名称
     */
    @TableField(value = "position_name")
    private String positionName;
    
    /**
     * 开始日期
     */
    @TableField(value = "start_date")
    private LocalDate startDate;
    
    /**
     * 结束日期
     */
    @TableField(value = "end_date")
    private LocalDate endDate;
    
    /**
     * 是否主要组织
     */
    @TableField(value = "is_primary")
    private Boolean isPrimary;
    
    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
} 