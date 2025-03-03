package com.lawfirm.model.personnel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.personnel.constant.LawyerConstant;
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
@TableName(LawyerConstant.Table.LAWYER)
public class Lawyer extends Employee {

    private static final long serialVersionUID = 1L;

    /**
     * 律师执业证号
     */
    @NotBlank(message = "执业证号不能为空")
    @TableField(LawyerConstant.Field.LICENSE_NUMBER)
    private String licenseNumber;

    /**
     * 执业证书发证日期
     */
    @TableField(LawyerConstant.Field.LICENSE_ISSUE_DATE)
    private LocalDate licenseIssueDate;

    /**
     * 执业证书失效日期
     */
    @TableField(LawyerConstant.Field.LICENSE_EXPIRE_DATE)
    private LocalDate licenseExpireDate;

    /**
     * 执业年限
     */
    @TableField(LawyerConstant.Field.PRACTICE_YEARS)
    private Integer practiceYears;

    /**
     * 律师职级
     */
    @TableField("level")
    private LawyerLevelEnum level;

    /**
     * 专业领域
     */
    @TableField(LawyerConstant.Field.PRACTICE_AREAS)
    private transient List<String> practiceAreas;

    /**
     * 擅长业务
     */
    @TableField("expertise")
    private String expertise;

    /**
     * 个人简介
     */
    @TableField("profile")
    private String profile;

    /**
     * 主要业绩
     */
    @TableField("achievements")
    private String achievements;

    /**
     * 团队ID
     */
    @TableField("team_id")
    private Long teamId;

    /**
     * 导师ID（针对实习律师）
     */
    @TableField("mentor_id")
    private Long mentorId;

    /**
     * 是否合伙人
     */
    @TableField("is_partner")
    private Boolean partner;

    /**
     * 合伙人加入日期
     */
    @TableField("partner_date")
    private LocalDate partnerDate;

    /**
     * 股权比例
     */
    @TableField("equity_ratio")
    private Double equityRatio;
} 