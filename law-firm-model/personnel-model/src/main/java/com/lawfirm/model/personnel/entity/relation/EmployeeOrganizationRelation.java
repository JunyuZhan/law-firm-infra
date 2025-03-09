package com.lawfirm.model.personnel.entity.relation;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.personnel.constant.PersonnelConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 员工与组织关系实体
 * 用于记录员工与组织之间的关联关系
 */
@Data
@TableName(PersonnelConstants.Table.EMPLOYEE_ORGANIZATION)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class EmployeeOrganizationRelation extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    @TableField(PersonnelConstants.Field.EmployeeOrganization.EMPLOYEE_ID)
    private Long employeeId;
    
    /**
     * 组织ID
     */
    @TableField(PersonnelConstants.Field.EmployeeOrganization.ORGANIZATION_ID)
    private Long organizationId;
    
    /**
     * 组织类型
     */
    @TableField(PersonnelConstants.Field.EmployeeOrganization.ORGANIZATION_TYPE)
    private String organizationType;
    
    /**
     * 职位ID
     */
    @TableField(PersonnelConstants.Field.EmployeeOrganization.POSITION_ID)
    private Long positionId;
    
    /**
     * 是否主要组织
     */
    @TableField(PersonnelConstants.Field.EmployeeOrganization.IS_PRIMARY)
    private Boolean isPrimary;
    
    /**
     * 开始日期
     */
    @TableField(PersonnelConstants.Field.EmployeeOrganization.START_DATE)
    private LocalDate startDate;
    
    /**
     * 结束日期
     */
    @TableField(PersonnelConstants.Field.EmployeeOrganization.END_DATE)
    private LocalDate endDate;
    
    /**
     * 关联状态（1-有效 0-无效）
     */
    @TableField(PersonnelConstants.Field.EmployeeOrganization.STATUS)
    private Integer status;
    
    /**
     * 备注
     */
    @TableField(PersonnelConstants.Field.EmployeeOrganization.REMARK)
    private String remark;
} 