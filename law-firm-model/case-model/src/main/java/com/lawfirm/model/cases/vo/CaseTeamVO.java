package com.lawfirm.model.cases.vo;

import com.lawfirm.model.cases.enums.TeamMemberRoleEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaseTeamVO {

    private Long id;
    
    private Long caseId;
    
    private String lawyerId;
    
    private String lawyerName;
    
    private TeamMemberRoleEnum role;
    
    private String department;
    
    private String title;
    
    private String specialization;
    
    private String responsibilities;
    
    private LocalDateTime joinTime;
    
    private LocalDateTime leaveTime;
    
    private Boolean active;
    
    private String remark;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 