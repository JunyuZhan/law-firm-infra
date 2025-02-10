package com.lawfirm.model.cases.vo;

import com.lawfirm.model.cases.enums.ParticipantTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaseParticipantVO {

    private Long id;
    
    private Long caseId;
    
    private String participantId;
    
    private String participantName;
    
    private ParticipantTypeEnum participantType;
    
    private String contactInfo;
    
    private String address;
    
    private String idNumber;
    
    private String position;
    
    private String organization;
    
    private String remark;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 