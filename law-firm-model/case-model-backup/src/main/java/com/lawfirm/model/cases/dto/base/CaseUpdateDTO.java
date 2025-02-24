package com.lawfirm.model.cases.dto.base;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.constants.CaseFieldConstants;
import com.lawfirm.model.cases.dto.business.CaseDocumentDTO;
import com.lawfirm.model.cases.dto.business.CaseReminderDTO;
import com.lawfirm.model.cases.dto.team.CaseParticipantDTO;
import com.lawfirm.model.cases.dto.team.CaseTeamDTO;
import com.lawfirm.model.cases.enums.base.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件更新DTO
 */
@Data
@Schema(description = "案件更新DTO")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseUpdateDTO extends CaseBaseDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "案件ID")
    @NotNull(message = "案件ID不能为空")
    private Long id;
    
    @Schema(description = "案件名称")
    @Size(max = CaseFieldConstants.CASE_NAME_MAX_LENGTH, 
          message = "案件名称长度不能超过{max}个字符")
    private String caseName;
    
    @Schema(description = "案件描述")
    @Size(max = CaseFieldConstants.CASE_DESCRIPTION_MAX_LENGTH, 
          message = "案件描述长度不能超过{max}个字符")
    private String description;
    
    @Schema(description = "案件类型")
    private CaseTypeEnum caseType;
    
    @Schema(description = "案件状态")
    private CaseStatusEnum caseStatus;
    
    @Schema(description = "案件进展")
    private CaseProgressEnum caseProgress;
    
    @Schema(description = "办理方式")
    private CaseHandleTypeEnum caseHandleType;
    
    @Schema(description = "难度等级")
    private CaseDifficultyEnum caseDifficulty;
    
    @Schema(description = "重要程度")
    private CaseImportanceEnum caseImportance;
    
    @Schema(description = "优先级")
    private CasePriorityEnum casePriority;
    
    @Schema(description = "收费类型")
    private CaseFeeTypeEnum caseFeeType;
    
    @Schema(description = "案件来源")
    private CaseSourceEnum caseSource;
    
    @Schema(description = "主办律师")
    private String lawyer;
    
    @Schema(description = "委托人ID")
    private Long clientId;
    
    @Schema(description = "委托人名称")
    private String clientName;
    
    @Schema(description = "律所ID")
    private Long lawFirmId;
    
    @Schema(description = "律所名称")
    private String lawFirmName;
    
    @Schema(description = "分所ID")
    private Long branchId;
    
    @Schema(description = "部门ID")
    private Long departmentId;
    
    @Schema(description = "立案时间")
    private LocalDateTime filingTime;
    
    @Schema(description = "结案时间")
    private LocalDateTime closingTime;
    
    @Schema(description = "收费金额")
    private BigDecimal fee;
    
    @Schema(description = "法院名称")
    private String court;
    
    @Schema(description = "法官信息")
    private String judgeInfo;
    
    @Schema(description = "法院案号")
    private String courtCaseNumber;
    
    @Schema(description = "是否重大案件")
    private Boolean isMajor;
    
    @Schema(description = "是否有利益冲突")
    private Boolean hasConflict;
    
    @Schema(description = "利益冲突原因")
    @Size(max = CaseFieldConstants.CONFLICT_REASON_MAX_LENGTH, 
          message = "利益冲突原因长度不能超过{max}个字符")
    private String conflictReason;
    
    @Schema(description = "变更原因")
    @Size(max = CaseFieldConstants.Description.MAX_LENGTH, message = "变更原因长度不能超过{max}个字符")
    private String changeReason;
    
    @Schema(description = "待添加的参与方列表")
    @Valid
    private List<CaseParticipantDTO> participantsToAdd;
    
    @Schema(description = "待移除的参与方ID列表")
    @Valid
    private List<Long> participantsToRemove;
    
    @Schema(description = "待添加的文档列表")
    @Valid
    private List<CaseDocumentDTO> documentsToAdd;
    
    @Schema(description = "待移除的文档ID列表")
    @Valid
    private List<Long> documentsToRemove;
    
    @Schema(description = "待添加的团队成员列表")
    @Valid
    private List<CaseTeamDTO> teamMembersToAdd;
    
    @Schema(description = "待移除的团队成员ID列表")
    @Valid
    private List<Long> teamMembersToRemove;
    
    @Schema(description = "待添加的提醒列表")
    private List<CaseReminderDTO> remindersToAdd;
    
    @Schema(description = "待移除的提醒ID列表")
    private List<Long> remindersToRemove;
    
    @Schema(description = "是否需要审批")
    private Boolean needApproval = false;
    
    @Schema(description = "审批流程")
    private String approvalFlow;
    
    @Schema(description = "备注")
    @Size(max = CaseFieldConstants.Description.REMARK_MAX_LENGTH, message = "备注长度不能超过{max}个字符")
    private String remark;
    
    @Schema(description = "版本号")
    private Integer version;

    @Override
    public CaseUpdateDTO setId(Long id) {
        super.setId(id);
        return this;
    }
    
    @Override
    public CaseUpdateDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }
} 