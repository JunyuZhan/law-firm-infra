package com.lawfirm.model.personnel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.personnel.constant.PersonnelConstant;
import com.lawfirm.model.personnel.enums.ContractTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 合同信息实体
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(PersonnelConstant.Table.CONTRACT)
public class Contract extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 人员ID
     */
    @NotNull(message = "人员ID不能为空")
    @TableField("person_id")
    private Long personId;

    /**
     * 合同编号
     */
    @NotBlank(message = "合同编号不能为空")
    @TableField("contract_number")
    private String contractNumber;

    /**
     * 合同类型
     */
    @NotNull(message = "合同类型不能为空")
    @TableField("type")
    private ContractTypeEnum type;

    /**
     * 合同名称
     */
    @NotBlank(message = "合同名称不能为空")
    @TableField("name")
    private String name;

    /**
     * 合同开始日期
     */
    @NotNull(message = "合同开始日期不能为空")
    @TableField("start_date")
    private LocalDate startDate;

    /**
     * 合同结束日期
     */
    @TableField("end_date")
    private LocalDate endDate;

    /**
     * 合同期限（月）
     */
    @TableField("duration")
    private Integer duration;

    /**
     * 试用期（月）
     */
    @TableField("probation_period")
    private Integer probationPeriod;

    /**
     * 基本工资
     */
    @TableField("base_salary")
    private Double baseSalary;

    /**
     * 岗位工资
     */
    @TableField("position_salary")
    private Double positionSalary;

    /**
     * 绩效工资
     */
    @TableField("performance_salary")
    private Double performanceSalary;

    /**
     * 保密协议
     */
    @TableField("confidentiality_agreement")
    private String confidentialityAgreement;

    /**
     * 竞业限制
     */
    @TableField("non_compete")
    private String nonCompete;

    /**
     * 签订日期
     */
    @TableField("sign_date")
    private LocalDate signDate;

    /**
     * 签订地点
     */
    @TableField("sign_location")
    private String signLocation;

    /**
     * 公司签订人
     */
    @TableField("company_signer")
    private String companySigner;

    /**
     * 合同状态（1-生效 2-终止 3-解除）
     */
    @TableField("status")
    private Integer status;

    /**
     * 终止/解除日期
     */
    @TableField("termination_date")
    private LocalDate terminationDate;

    /**
     * 终止/解除原因
     */
    @TableField("termination_reason")
    private String terminationReason;

    /**
     * 附件URL
     */
    @TableField("attachment_url")
    private String attachmentUrl;
} 