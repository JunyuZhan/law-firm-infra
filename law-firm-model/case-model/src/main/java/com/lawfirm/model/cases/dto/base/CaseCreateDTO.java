package com.lawfirm.model.cases.dto.base;

import com.lawfirm.model.cases.dto.team.CaseParticipantDTO;
import com.lawfirm.model.cases.dto.team.CaseTeamDTO;
import com.lawfirm.model.cases.enums.base.CaseTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 案件创建数据传输对象
 * 
 * 继承自CaseBaseDTO，包含创建案件时需要的额外属性，如参与方列表、团队成员列表等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema(description = "案件创建DTO")
public class CaseCreateDTO extends CaseBaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 案件参与方列表
     */
    private transient List<CaseParticipantDTO> participants;

    /**
     * 团队成员列表
     */
    private transient List<CaseTeamDTO> teamMembers;

    /**
     * 特殊要求
     */
    private String specialRequirements;

    /**
     * 是否需要审批
     */
    private Boolean needApproval;

    /**
     * 审批人ID
     */
    private Long approverId;

    /**
     * 审批人姓名
     */
    private String approverName;

    /**
     * 是否需要冲突检查
     */
    private Boolean needConflictCheck;

    /**
     * 是否需要自动分配团队
     */
    private Boolean autoAssignTeam;

    /**
     * 是否需要创建默认任务
     */
    private Boolean createDefaultTasks;

    /**
     * 是否需要创建默认提醒
     */
    private Boolean createDefaultReminders;

    /**
     * 是否需要通知客户
     */
    private Boolean notifyClient;

    /**
     * 通知内容
     */
    private String notificationContent;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 创建人姓名
     */
    private String creatorName;

    @NotBlank(message = "案件名称不能为空")
    @Schema(description = "案件名称")
    private String name;

    @NotNull(message = "客户ID不能为空")
    @Schema(description = "客户ID")
    private Long clientId;

    @NotNull(message = "主办律师ID不能为空")
    @Schema(description = "主办律师ID")
    private Long leaderId;

    @Schema(description = "案件类型")
    private CaseTypeEnum caseType;

    @Schema(description = "案件描述")
    private String description;

    @Schema(description = "案件标签")
    private transient List<String> tags;

    @Schema(description = "团队成员ID列表")
    private transient List<Long> teamMemberIds;

    @Schema(description = "对方当事人")
    private transient List<String> oppositeParties;

    @Schema(description = "备注")
    private String remark;
} 