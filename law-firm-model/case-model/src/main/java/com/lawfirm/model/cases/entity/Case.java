package com.lawfirm.model.cases.entity;

import com.lawfirm.model.base.entity.BaseEntity;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.base.status.StatusAware;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;
import com.lawfirm.model.cases.enums.CaseProgressEnum;
import com.lawfirm.model.cases.enums.CaseHandleTypeEnum;
import com.lawfirm.model.cases.enums.CaseDifficultyEnum;
import com.lawfirm.model.cases.enums.CaseImportanceEnum;
import com.lawfirm.model.cases.enums.CasePriorityEnum;
import com.lawfirm.model.cases.enums.CaseFeeTypeEnum;
import com.lawfirm.model.cases.enums.CaseSourceEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "law_case")
@EqualsAndHashCode(callSuper = true)
public class Case extends BaseEntity implements StatusAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "案件编号不能为空")
    @Column(name = "case_number", unique = true, length = 32)
    @Comment("案件编号")
    private String caseNumber;

    @NotBlank(message = "案件名称不能为空")
    @Column(name = "case_name", length = 128)
    @Comment("案件名称")
    private String caseName;

    @Column(name = "description", length = 512)
    @Comment("案件描述")
    private String description;

    @NotNull(message = "案件类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(name = "case_type", length = 32)
    @Comment("案件类型")
    private CaseTypeEnum caseType;

    @NotNull(message = "案件状态不能为空")
    @Enumerated(EnumType.STRING)
    @Column(name = "case_status", length = 32)
    @Comment("案件状态")
    private CaseStatusEnum caseStatus;

    @NotNull(message = "案件进展不能为空")
    @Enumerated(EnumType.STRING)
    @Column(name = "progress", length = 32)
    @Comment("案件进展")
    private CaseProgressEnum progress;

    @NotNull(message = "办理方式不能为空")
    @Enumerated(EnumType.STRING)
    @Column(name = "handle_type", length = 32)
    @Comment("办理方式")
    private CaseHandleTypeEnum handleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", length = 32)
    @Comment("难度等级")
    private CaseDifficultyEnum difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "importance", length = 32)
    @Comment("重要程度")
    private CaseImportanceEnum importance;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 32)
    @Comment("优先级")
    private CasePriorityEnum priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_type", length = 32)
    @Comment("收费方式")
    private CaseFeeTypeEnum feeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", length = 32)
    @Comment("案件来源")
    private CaseSourceEnum source;

    @NotBlank(message = "主办律师不能为空")
    @Column(name = "lawyer", length = 64)
    @Comment("主办律师")
    private String lawyer;

    @Column(name = "operator", length = 64)
    @Comment("操作人")
    private String operator;

    @NotNull(message = "委托人ID不能为空")
    @Column(name = "client_id")
    @Comment("委托人ID")
    private Long clientId;

    @Column(name = "law_firm_id")
    @Comment("律所ID")
    private Long lawFirmId;

    @Column(name = "branch_id")
    @Comment("分所ID")
    private Long branchId;

    @Column(name = "department_id")
    @Comment("部门ID")
    private Long departmentId;

    @Column(name = "filing_time")
    @Comment("立案时间")
    private LocalDateTime filingTime;

    @Column(name = "closing_time")
    @Comment("结案时间")
    private LocalDateTime closingTime;

    @Column(name = "fee")
    @Comment("收费金额")
    private BigDecimal fee;

    @Column(name = "court_name", length = 50)
    @Comment("法院名称")
    private String courtName;

    @Column(name = "judge_name", length = 50)
    @Comment("法官姓名")
    private String judgeName;

    @Column(name = "court_case_number", length = 50)
    @Comment("法院案号")
    private String courtCaseNumber;

    @Column(name = "is_major")
    @Comment("是否重大案件")
    private Boolean isMajor;

    @Column(name = "has_conflict")
    @Comment("是否存在利益冲突")
    private Boolean hasConflict;

    @NotNull(message = "系统状态不能为空")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private StatusEnum statusEnum;

    @Override
    public StatusEnum getStatus() {
        return statusEnum;
    }

    @Override
    public void setStatus(StatusEnum status) {
        this.statusEnum = status;
    }
} 