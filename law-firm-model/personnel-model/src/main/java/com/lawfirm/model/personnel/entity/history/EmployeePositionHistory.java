package com.lawfirm.model.personnel.entity.history;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.personnel.constant.PersonnelConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 员工职位变更历史实体
 * 用于记录员工职位变更的历史记录
 */
@Data
@TableName(PersonnelConstants.Table.EMPLOYEE_POSITION_HISTORY)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class EmployeePositionHistory extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    @TableField(PersonnelConstants.Field.EmployeePositionHistory.EMPLOYEE_ID)
    private Long employeeId;
    
    /**
     * 组织ID
     */
    @TableField(PersonnelConstants.Field.EmployeePositionHistory.ORGANIZATION_ID)
    private Long organizationId;
    
    /**
     * 变更前职位ID
     */
    @TableField(PersonnelConstants.Field.EmployeePositionHistory.FROM_POSITION_ID)
    private Long fromPositionId;
    
    /**
     * 变更前职位名称
     */
    @TableField(PersonnelConstants.Field.EmployeePositionHistory.FROM_POSITION_NAME)
    private String fromPositionName;
    
    /**
     * 变更后职位ID
     */
    @TableField(PersonnelConstants.Field.EmployeePositionHistory.TO_POSITION_ID)
    private Long toPositionId;
    
    /**
     * 变更后职位名称
     */
    @TableField(PersonnelConstants.Field.EmployeePositionHistory.TO_POSITION_NAME)
    private String toPositionName;
    
    /**
     * 变更生效日期
     */
    @TableField(PersonnelConstants.Field.EmployeePositionHistory.EFFECTIVE_DATE)
    private LocalDate effectiveDate;
    
    /**
     * 变更原因
     */
    @TableField(PersonnelConstants.Field.EmployeePositionHistory.CHANGE_REASON)
    private String changeReason;
    
    /**
     * 操作人ID
     */
    @TableField(PersonnelConstants.Field.EmployeePositionHistory.OPERATOR_ID)
    private Long operatorId;
    
    /**
     * 操作人姓名
     */
    @TableField(PersonnelConstants.Field.EmployeePositionHistory.OPERATOR_NAME)
    private String operatorName;
    
    /**
     * 备注
     */
    @TableField(PersonnelConstants.Field.EmployeePositionHistory.REMARK)
    private String remark;
} 