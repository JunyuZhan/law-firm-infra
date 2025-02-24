package com.lawfirm.model.cases.dto.base;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.base.*;
import com.lawfirm.model.cases.dto.team.CaseParticipantDTO;
import com.lawfirm.model.cases.dto.team.CaseTeamDTO;
import com.lawfirm.model.cases.dto.business.CaseDocumentDTO;
import com.lawfirm.model.cases.dto.business.CaseReminderDTO;
import com.lawfirm.model.cases.constants.CaseFieldConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import jakarta.validation.constraints.Pattern;

/**
 * 案件创建DTO
 */
@Getter
@Setter
@Accessors(chain = true)
@Data
@Schema(description = "案件创建DTO")
@EqualsAndHashCode(callSuper = true)
public class CaseCreateDTO extends CaseBaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "案件编号")
    @NotBlank(message = "案件编号不能为空")
    @Size(min = CaseFieldConstants.CASE_NUMBER_MIN_LENGTH, 
          max = CaseFieldConstants.CASE_NUMBER_MAX_LENGTH, 
          message = "案件编号长度必须在{min}到{max}之间")
    @Pattern(regexp = CaseFieldConstants.CASE_NUMBER_PATTERN, 
             message = "案件编号格式不正确")
    private String caseNumber;
    
    @Schema(description = "案件名称")
    @NotBlank(message = "案件名称不能为空")
    @Size(min = CaseFieldConstants.CASE_NAME_MIN_LENGTH, 
          max = CaseFieldConstants.CASE_NAME_MAX_LENGTH, 
          message = "案件名称长度必须在{min}到{max}之间")
    private String caseName;
    
    @Schema(description = "案件描述")
    @Size(max = CaseFieldConstants.CASE_DESCRIPTION_MAX_LENGTH, 
          message = "案件描述长度不能超过{max}个字符")
    private String description;
    
    @Schema(description = "案件类型")
    @NotNull(message = "案件类型不能为空")
    private CaseTypeEnum caseType;
    
    @Schema(description = "案件状态")
    @NotNull(message = "案件状态不能为空")
    private CaseStatusEnum caseStatus;
    
    @Schema(description = "案件进展")
    @NotNull(message = "案件进展不能为空")
    private CaseProgressEnum caseProgress;
    
    @Schema(description = "办理方式")
    @NotNull(message = "办理方式不能为空")
    private CaseHandleTypeEnum caseHandleType;
    
    @Schema(description = "案件难度")
    private CaseDifficultyEnum caseDifficulty;
    
    @Schema(description = "案件重要性")
    private CaseImportanceEnum caseImportance;
    
    @Schema(description = "案件优先级")
    private CasePriorityEnum casePriority;
    
    @Schema(description = "案件费用类型")
    private CaseFeeTypeEnum caseFeeType;
    
    @Schema(description = "案件来源")
    private CaseSourceEnum caseSource;
    
    @Schema(description = "主办律师")
    @NotBlank(message = "主办律师不能为空")
    @Size(max = CaseFieldConstants.LAWYER_NAME_MAX_LENGTH, 
          message = "律师姓名长度不能超过{max}个字符")
    private String lawyer;
    
    @Schema(description = "客户ID")
    @NotNull(message = "客户ID不能为空")
    private Long clientId;
    
    @Schema(description = "客户名称")
    private String clientName;
    
    @Schema(description = "律所ID")
    @NotNull(message = "律所ID不能为空")
    private Long lawFirmId;
    
    @Schema(description = "律所名称")
    private String lawFirmName;
    
    @Schema(description = "分支机构ID")
    private Long branchId;
    
    @Schema(description = "部门ID")
    private Long departmentId;
    
    @Schema(description = "立案时间")
    @NotNull(message = "立案时间不能为空")
    private LocalDateTime filingTime;
    
    @Schema(description = "关闭时间")
    private LocalDateTime closingTime;
    
    @Schema(description = "费用")
    @Pattern(regexp = CaseFieldConstants.CASE_FEE_PATTERN, 
             message = "费用格式不正确")
    private BigDecimal fee;
    
    @Schema(description = "法院名称")
    @Size(max = CaseFieldConstants.COURT_NAME_MAX_LENGTH, 
          message = "法院名称长度不能超过{max}个字符")
    private String courtName;
    
    @Schema(description = "法官名称")
    @Size(max = CaseFieldConstants.JUDGE_NAME_MAX_LENGTH, 
          message = "法官姓名长度不能超过{max}个字符")
    private String judgeName;
    
    @Schema(description = "法院案件编号")
    @Size(max = CaseFieldConstants.COURT_CASE_NUMBER_MAX_LENGTH, 
          message = "法院案号长度不能超过{max}个字符")
    private String courtCaseNumber;
    
    @Schema(description = "是否主要案件")
    private Boolean isMajor;
    
    @Schema(description = "是否有冲突")
    private Boolean hasConflict;
    
    @Schema(description = "冲突原因")
    @Size(max = CaseFieldConstants.CONFLICT_REASON_MAX_LENGTH, 
          message = "利益冲突原因长度不能超过{max}个字符")
    private String conflictReason;
    
    @Schema(description = "案件参与方列表")
    @Valid
    private List<CaseParticipantDTO> participants;

    @Schema(description = "案件文档列表")
    @Valid
    private List<CaseDocumentDTO> documents;

    @Schema(description = "案件团队成员列表")
    @NotEmpty(message = "至少需要一个案件团队成员")
    @Valid
    private List<CaseTeamDTO> teamMembers;

    @Schema(description = "案件提醒列表")
    private List<CaseReminderDTO> reminders;

    @Schema(description = "特殊要求")
    @Size(max = CaseFieldConstants.Description.MAX_LENGTH, message = "特殊要求长度不能超过{max}个字符")
    private String specialRequirements;

    @Schema(description = "是否需要利益冲突检查")
    private Boolean needConflictCheck = true;

    @Schema(description = "是否需要审批")
    private Boolean needApproval = false;

    @Schema(description = "审批流程")
    private String approvalFlow;

    @Schema(description = "立案申请书")
    @Size(max = CaseFieldConstants.Description.MAX_LENGTH, message = "立案申请书长度不能超过{max}个字符")
    private String filingApplication;

    @Override
    public CaseCreateDTO setId(Long id) {
        super.setId(id);
        return this;
    }
    
    @Override
    public CaseCreateDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }
    
    @Override
    public CaseCreateDTO setCreateBy(String createBy) {
        super.setCreateBy(createBy);
        return this;
    }
    
    @Override
    public CaseCreateDTO setUpdateBy(String updateBy) {
        super.setUpdateBy(updateBy);
        return this;
    }
} 