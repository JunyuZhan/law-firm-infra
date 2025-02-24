package com.lawfirm.model.cases.vo.team;

import com.lawfirm.model.cases.enums.team.TeamMemberRoleEnum;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CaseTeamVO {
    
    private Long id;
    
    private Long caseId;
    
    private String memberId;
    
    private String memberName;
    
    private TeamMemberRoleEnum role;
    
    private String department;
    
    private String position;
    
    private String responsibilities;
    
    private LocalDateTime joinTime;
    
    private LocalDateTime leaveTime;
    
    private String status;
    
    private String remark;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 