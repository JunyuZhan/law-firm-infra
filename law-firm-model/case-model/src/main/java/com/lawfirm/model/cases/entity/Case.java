package com.lawfirm.model.cases.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lawfirm.model.base.entity.ModelBaseEntity;
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
@Table(name = "case_info")
@EqualsAndHashCode(callSuper = true)
public class Case extends ModelBaseEntity implements StatusAware {

    @TableId(type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "案件编号不能为空")
    @TableField("case_number")
    @Comment("案件编号")
    private String caseNumber;

    @NotBlank(message = "案件名称不能为空")
    @TableField("case_name")
    @Comment("案件名称")
    private String caseName;

    @TableField("description")
    @Comment("案件描述")
    private String description;

    @NotNull(message = "案件类型不能为空")
    @TableField("case_type")
    @Comment("案件类型")
    private CaseTypeEnum caseType;

    @NotNull(message = "案件状态不能为空")
    @TableField("case_status")
    @Comment("案件状态")
    private CaseStatusEnum caseStatus;

    @NotNull(message = "案件进展不能为空")
    @TableField("case_progress")
    @Comment("案件进展")
    private CaseProgressEnum caseProgress;

    @NotNull(message = "办理方式不能为空")
    @TableField("case_handle_type")
    @Comment("办理方式")
    private CaseHandleTypeEnum caseHandleType;

    @TableField("case_difficulty")
    @Comment("难度等级")
    private CaseDifficultyEnum caseDifficulty;

    @TableField("case_importance")
    @Comment("重要程度")
    private CaseImportanceEnum caseImportance;

    @TableField("case_priority")
    @Comment("优先级")
    private CasePriorityEnum casePriority;

    @TableField("case_fee_type")
    @Comment("收费方式")
    private CaseFeeTypeEnum caseFeeType;

    @TableField("case_source")
    @Comment("案件来源")
    private CaseSourceEnum caseSource;

    @NotBlank(message = "主办律师不能为空")
    @TableField("lawyer")
    @Comment("主办律师")
    private String lawyer;

    @TableField("operator")
    @Comment("操作人")
    private String operator;

    @NotNull(message = "委托人ID不能为空")
    @TableField("client_id")
    @Comment("委托人ID")
    private Long clientId;

    @TableField("law_firm_id")
    @Comment("律所ID")
    private Long lawFirmId;

    @TableField("branch_id")
    @Comment("分所ID")
    private Long branchId;

    @TableField("department_id")
    @Comment("部门ID")
    private Long departmentId;

    @TableField("filing_time")
    @Comment("立案时间")
    private LocalDateTime filingTime;

    @TableField("closing_time")
    @Comment("结案时间")
    private LocalDateTime closingTime;

    @TableField("fee")
    @Comment("收费金额")
    private BigDecimal fee;

    @TableField("court_name")
    @Comment("法院名称")
    private String courtName;

    @TableField("judge_name")
    @Comment("法官姓名")
    private String judgeName;

    @TableField("court_case_number")
    @Comment("法院案号")
    private String courtCaseNumber;

    @TableField("is_major")
    @Comment("是否重大案件")
    private Boolean isMajor;

    @TableField("has_conflict")
    @Comment("是否有利益冲突")
    private Boolean hasConflict;

    @TableField("conflict_reason")
    @Comment("利益冲突原因")
    private String conflictReason;

    @TableField("remark")
    @Comment("备注")
    private String remark;

    @NotNull(message = "系统状态不能为空")
    @TableField("status")
    private StatusEnum status;

    @Override
    public StatusEnum getStatus() {
        return status;
    }

    @Override
    public void setStatus(StatusEnum status) {
        this.status = status;
    }
} 