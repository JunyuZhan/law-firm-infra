package com.lawfirm.model.cases.dto.base;

import com.lawfirm.model.cases.dto.team.CaseParticipantDTO;
import com.lawfirm.model.cases.dto.team.CaseTeamDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 案件创建数据传输对象
 * 
 * 继承自CaseBaseDTO，包含创建案件时需要的额外属性，如参与方列表、团队成员列表等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseCreateDTO extends CaseBaseDTO {

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
} 