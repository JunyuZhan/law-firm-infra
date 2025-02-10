package com.lawfirm.model.cases.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.model.cases.enums.TeamMemberRoleEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class CaseTeamDTO extends BaseDTO {

    private Long id;
    
    @NotNull(message = "案件ID不能为空")
    private Long caseId;
    
    @NotNull(message = "律师ID不能为空")
    private Long lawyerId;
    
    @NotNull(message = "团队角色不能为空")
    private TeamMemberRoleEnum role;
    
    @Size(max = 200, message = "职责描述长度不能超过200个字符")
    private String responsibilities;
    
    private LocalDateTime joinTime = LocalDateTime.now();
    
    private LocalDateTime leaveTime;
    
    private String leaveReason;
    
    private Integer workloadPercentage = 0;
    
    private Boolean shareProfit = false;
    
    private Integer profitPercentage = 0;
    
    private String department;
    
    private String level;
    
    private Boolean needAssessment = true;
    
    private String assessmentCriteria;
    
    private String remark;
    
    @Override
    public CaseTeamDTO setId(Long id) {
        super.setId(id);
        return this;
    }
    
    @Override
    public CaseTeamDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }
    
    @Override
    public CaseTeamDTO setCreateBy(String createBy) {
        super.setCreateBy(createBy);
        return this;
    }
    
    @Override
    public CaseTeamDTO setUpdateBy(String updateBy) {
        super.setUpdateBy(updateBy);
        return this;
    }
} 