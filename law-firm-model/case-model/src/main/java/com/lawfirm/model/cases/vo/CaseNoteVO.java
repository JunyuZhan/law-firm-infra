package com.lawfirm.model.cases.vo;

import com.lawfirm.model.cases.enums.NoteTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CaseNoteVO {

    private Long id;
    
    private Long caseId;
    
    private NoteTypeEnum type;
    
    private String title;
    
    private String content;
    
    private List<String> tags;
    
    private List<String> attachments;
    
    private Boolean isPrivate;
    
    private List<String> sharedWith;
    
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