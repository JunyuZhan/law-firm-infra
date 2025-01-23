package com.lawfirm.model.cases.entity;

import com.lawfirm.model.base.entity.BaseEntity;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.base.status.StatusAware;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "case_info")
@EqualsAndHashCode(callSuper = true)
public class Case extends BaseEntity implements StatusAware {

    @NotBlank(message = "案件编号不能为空")
    @Size(max = 100, message = "案件编号长度不能超过100个字符")
    @Column(nullable = false, length = 100, unique = true)
    private String caseNumber;  // 案件编号

    @NotBlank(message = "案件名称不能为空")
    @Size(max = 200, message = "案件名称长度不能超过200个字符")
    @Column(nullable = false, length = 200)
    private String caseName;  // 案件名称

    @Column(length = 500)
    private String description;  // 案件描述

    @NotNull(message = "案件类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CaseTypeEnum caseType;  // 案件类型

    @NotNull(message = "案件状态不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CaseStatusEnum caseStatus;  // 案件状态

    @NotNull(message = "系统状态不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatusEnum status = StatusEnum.ENABLED;  // 系统状态

    @NotNull(message = "委托人ID不能为空")
    @Column(nullable = false)
    private Long clientId;  // 委托人ID

    @NotNull(message = "主办律师ID不能为空")
    @Column(nullable = false)
    private Long principalId;  // 主办律师ID

    @NotNull(message = "律所ID不能为空")
    @Column(nullable = false)
    private Long lawFirmId;  // 律所ID

    @Column(nullable = false)
    private Long branchId;  // 分所ID

    @Column(nullable = false)
    private Long departmentId;  // 部门ID

    @Column(nullable = false)
    private LocalDateTime filingTime;  // 立案时间

    private LocalDateTime closingTime;  // 结案时间

    @Column(precision = 19, scale = 2)
    private BigDecimal fee;  // 收费金额

    @Column(length = 50)
    private String courtName;  // 法院名称

    @Column(length = 50)
    private String judgeName;  // 法官姓名

    @Column(length = 50)
    private String courtCaseNumber;  // 法院案号

    @Column(length = 500)
    private String remark;  // 案件备注
} 