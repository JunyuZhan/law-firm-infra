package com.lawfirm.model.personnel.dto.lawyer;

import com.lawfirm.model.personnel.dto.employee.EmployeeDTO;
import com.lawfirm.model.personnel.enums.LawyerLevelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

/**
 * 律师数据传输对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LawyerDTO extends EmployeeDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 律师执业证号
     */
    private String licenseNumber;

    /**
     * 执业证书发证日期
     */
    private LocalDate licenseIssueDate;

    /**
     * 执业证书失效日期
     */
    private LocalDate licenseExpireDate;

    /**
     * 执业年限
     */
    private Integer practiceYears;

    /**
     * 律师职级
     */
    private LawyerLevelEnum level;

    /**
     * 专业领域
     */
    private transient List<String> practiceAreas;

    /**
     * 擅长业务
     */
    private String expertise;

    /**
     * 个人简介
     */
    private String profile;

    /**
     * 主要业绩
     */
    private String achievements;

    /**
     * 团队ID
     */
    private Long teamId;

    /**
     * 团队名称
     */
    private String teamName;

    /**
     * 导师ID（针对实习律师）
     */
    private Long mentorId;

    /**
     * 导师姓名
     */
    private String mentorName;

    /**
     * 是否合伙人
     */
    private Boolean partner;

    /**
     * 合伙人加入日期
     */
    private LocalDate partnerDate;

    /**
     * 股权比例
     */
    private Double equityRatio;

    /**
     * 执业状态（0-正常执业 1-暂停执业 2-注销执业）
     */
    private Integer practiceStatus;
} 