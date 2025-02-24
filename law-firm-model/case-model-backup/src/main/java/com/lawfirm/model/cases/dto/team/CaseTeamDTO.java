package com.lawfirm.model.cases.dto.team;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.team.TeamMemberRoleEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件团队成员DTO
 */
@Getter
@Setter
@Accessors(chain = true)
public class CaseTeamDTO extends BaseDTO {

    /**
     * 案件ID
     */
    @NotNull(message = "案件ID不能为空")
    private Long caseId;
    
    /**
     * 团队成员ID
     */
    @NotNull(message = "团队成员ID不能为空")
    private Long memberId;
    
    /**
     * 团队成员角色（主办律师、协办律师、实习律师等）
     */
    @NotNull(message = "团队成员角色不能为空")
    private TeamMemberRoleEnum memberRole;
    
    /**
     * 团队成员职责
     */
    private String responsibility;
    
    /**
     * 加入时间
     */
    private LocalDateTime joinTime = LocalDateTime.now();
    
    /**
     * 退出时间
     */
    private LocalDateTime leaveTime;
    
    /**
     * 工作量权重（0-100）
     */
    private Integer workloadWeight;
    
    /**
     * 备注
     */
    private String remark;
    
    private String createBy;
    
    private LocalDateTime createTime;
    
    private String updateBy;
    
    private LocalDateTime updateTime;
} 