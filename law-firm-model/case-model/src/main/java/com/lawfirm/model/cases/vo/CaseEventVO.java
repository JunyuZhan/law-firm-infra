package com.lawfirm.model.cases.vo;

import com.lawfirm.model.cases.enums.EventTypeEnum;
import com.lawfirm.model.cases.enums.EventStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CaseEventVO {

    private Long id;
    
    private Long caseId;
    
    private EventTypeEnum type;
    
    private String title;
    
    private String description;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private String location;
    
    private String organizer;
    
    private List<String> participants;
    
    private EventStatusEnum status;
    
    private String result;
    
    private String summary;
    
    private List<String> attachments;
    
    private Boolean needReminder;
    
    private Integer reminderAdvanceTime;
    
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
} 