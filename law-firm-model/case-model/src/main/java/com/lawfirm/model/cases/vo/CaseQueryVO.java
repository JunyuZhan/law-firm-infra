package com.lawfirm.model.cases.vo;

import com.lawfirm.model.cases.enums.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CaseQueryVO {

    private Long id;
    
    private String caseNumber;
    
    private String caseName;
    
    private String description;
    
    private CaseTypeEnum caseType;
    
    private CaseStatusEnum caseStatus;
    
    private CaseProgressEnum caseProgress;
    
    private CaseHandleTypeEnum caseHandleType;
    
    private String leadLawyerId;
    
    private String leadLawyerName;
    
    private String clientId;
    
    private String clientName;
    
    private String lawFirmId;
    
    private String lawFirmName;
    
    private BigDecimal estimatedValue;
    
    private String currency;
    
    private LocalDateTime filingTime;
    
    private LocalDateTime closingTime;
    
    private String courtName;
    
    private String judgeInfo;
    
    private String opposingParty;
    
    private String opposingLawyer;
    
    private String opposingLawFirm;
    
    private List<CaseParticipantVO> participants;
    
    private List<CaseDocumentVO> documents;
    
    private List<CaseTeamVO> teamMembers;
    
    private List<CaseReminderVO> reminders;
    
    private String specialRequirements;
    
    private Boolean hasConflict;
    
    private String conflictReason;
    
    private Boolean needApproval;
    
    private Boolean approved;
    
    private String approver;
    
    private LocalDateTime approvalTime;
    
    private String approvalComment;
    
    private String remark;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
    
    private Integer version;
} 