package com.lawfirm.model.personnel.entity.resume;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.personnel.constant.PersonnelConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 工作经历实体
 */
@Data
@TableName(PersonnelConstants.Table.WORK_EXPERIENCE)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class WorkExperience extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    @TableField(PersonnelConstants.Field.WorkExperience.EMPLOYEE_ID)
    private Long employeeId;
    
    /**
     * 公司名称
     */
    @TableField(PersonnelConstants.Field.WorkExperience.COMPANY_NAME)
    private String companyName;
    
    /**
     * 部门名称
     */
    @TableField(PersonnelConstants.Field.WorkExperience.DEPARTMENT_NAME)
    private String departmentName;
    
    /**
     * 职位名称
     */
    @TableField(PersonnelConstants.Field.WorkExperience.POSITION_NAME)
    private String positionName;
    
    /**
     * 开始日期
     */
    @TableField(PersonnelConstants.Field.WorkExperience.START_DATE)
    private LocalDate startDate;
    
    /**
     * 结束日期
     */
    @TableField(PersonnelConstants.Field.WorkExperience.END_DATE)
    private LocalDate endDate;
    
    /**
     * 是否目前工作
     */
    @TableField(PersonnelConstants.Field.WorkExperience.IS_CURRENT)
    private Boolean isCurrent;
    
    /**
     * 工作内容
     */
    @TableField(PersonnelConstants.Field.WorkExperience.JOB_DESCRIPTION)
    private String jobDescription;
    
    /**
     * 离职原因
     */
    @TableField(PersonnelConstants.Field.WorkExperience.LEAVE_REASON)
    private String leaveReason;
    
    /**
     * 证明人
     */
    @TableField(PersonnelConstants.Field.WorkExperience.REFERENCE_PERSON)
    private String referencePerson;
    
    /**
     * 证明人联系方式
     */
    @TableField(PersonnelConstants.Field.WorkExperience.REFERENCE_CONTACT)
    private String referenceContact;
    
    /**
     * 证明材料URL
     */
    @TableField(PersonnelConstants.Field.WorkExperience.REFERENCE_URL)
    private String referenceUrl;
} 