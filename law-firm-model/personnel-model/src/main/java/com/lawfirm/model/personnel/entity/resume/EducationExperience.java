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
 * 教育经历实体
 */
@Data
@TableName(PersonnelConstants.Table.EDUCATION_EXPERIENCE)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class EducationExperience extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    @TableField(PersonnelConstants.Field.EducationExperience.EMPLOYEE_ID)
    private Long employeeId;
    
    /**
     * 学校名称
     */
    @TableField(PersonnelConstants.Field.EducationExperience.SCHOOL_NAME)
    private String schoolName;
    
    /**
     * 学院/系名称
     */
    @TableField(PersonnelConstants.Field.EducationExperience.COLLEGE_NAME)
    private String collegeName;
    
    /**
     * 专业名称
     */
    @TableField(PersonnelConstants.Field.EducationExperience.MAJOR)
    private String major;
    
    /**
     * 学位（1-专科 2-本科 3-硕士 4-博士 5-其他）
     */
    @TableField(PersonnelConstants.Field.EducationExperience.DEGREE)
    private Integer degree;
    
    /**
     * 学历（1-专科 2-本科 3-硕士研究生 4-博士研究生 5-其他）
     */
    @TableField(PersonnelConstants.Field.EducationExperience.EDUCATION)
    private Integer education;
    
    /**
     * 开始日期
     */
    @TableField(PersonnelConstants.Field.EducationExperience.START_DATE)
    private LocalDate startDate;
    
    /**
     * 结束日期
     */
    @TableField(PersonnelConstants.Field.EducationExperience.END_DATE)
    private LocalDate endDate;
    
    /**
     * 是否全日制
     */
    @TableField(PersonnelConstants.Field.EducationExperience.IS_FULL_TIME)
    private Boolean isFullTime;
    
    /**
     * 是否最高学历
     */
    @TableField(PersonnelConstants.Field.EducationExperience.IS_HIGHEST)
    private Boolean isHighest;
    
    /**
     * 证书附件URL
     */
    @TableField(PersonnelConstants.Field.EducationExperience.CERTIFICATE_URL)
    private String certificateUrl;
    
    /**
     * 描述
     */
    @TableField(PersonnelConstants.Field.EducationExperience.DESCRIPTION)
    private String description;
} 