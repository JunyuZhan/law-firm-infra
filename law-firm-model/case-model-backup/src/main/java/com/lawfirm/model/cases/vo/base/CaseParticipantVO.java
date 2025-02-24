package com.lawfirm.model.cases.vo.base;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CaseParticipantVO {
    
    private Long id;
    
    private Long caseId;
    
    private String participantId;
    
    private String participantName;
    
    private String participantType;
    
    private String role;
    
    private String contactInfo;
    
    private String remark;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 