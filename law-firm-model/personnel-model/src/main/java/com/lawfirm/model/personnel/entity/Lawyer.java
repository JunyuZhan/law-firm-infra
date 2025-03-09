package com.lawfirm.model.personnel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.personnel.constant.PersonnelConstants;
import com.lawfirm.model.personnel.enums.LawyerLevelEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

/**
 * 律师信息实体
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(PersonnelConstants.Table.LAWYER)
public class Lawyer extends Employee {

    private static final long serialVersionUID = 1L;

    /**
     * 律师执业证号
     */
    @NotBlank(message = "执业证号不能为空")
    @TableField(PersonnelConstants.Field.Lawyer.LICENSE_NUMBER)
    private String licenseNumber;

    /**
     * 执业证书发证日期
     */
    @TableField(PersonnelConstants.Field.Lawyer.LICENSE_ISSUE_DATE)
    private LocalDate licenseIssueDate;

    /**
     * 执业证书失效日期
     */
    @TableField(PersonnelConstants.Field.Lawyer.LICENSE_EXPIRE_DATE)
    private LocalDate licenseExpireDate;

    /**
     * 执业年限
     */
    @TableField(PersonnelConstants.Field.Lawyer.PRACTICE_YEARS)
    private Integer practiceYears;

    /**
     * 律师职级
     */
    @TableField(PersonnelConstants.Field.Lawyer.LEVEL)
    private LawyerLevelEnum level;

    /**
     * 专业领域
     */
    @TableField(PersonnelConstants.Field.Lawyer.PRACTICE_AREAS)
    private transient List<String> practiceAreas;

    /**
     * 擅长业务
     */
    @TableField(PersonnelConstants.Field.Lawyer.EXPERTISE)
    private String expertise;

    /**
     * 个人简介
     */
    @TableField(PersonnelConstants.Field.Lawyer.PROFILE)
    private String profile;

    /**
     * 主要业绩
     */
    @TableField(PersonnelConstants.Field.Lawyer.ACHIEVEMENTS)
    private String achievements;

    /**
     * 团队ID
     */
    @TableField(PersonnelConstants.Field.Lawyer.TEAM_ID)
    private Long teamId;

    /**
     * 导师ID（针对实习律师）
     */
    @TableField(PersonnelConstants.Field.Lawyer.MENTOR_ID)
    private Long mentorId;

    /**
     * 是否合伙人
     */
    @TableField(PersonnelConstants.Field.Lawyer.IS_PARTNER)
    private Boolean partner;

    /**
     * 合伙人加入日期
     */
    @TableField(PersonnelConstants.Field.Lawyer.PARTNER_DATE)
    private LocalDate partnerDate;

    /**
     * 股权比例
     */
    @TableField(PersonnelConstants.Field.Lawyer.EQUITY_RATIO)
    private Double equityRatio;
} 