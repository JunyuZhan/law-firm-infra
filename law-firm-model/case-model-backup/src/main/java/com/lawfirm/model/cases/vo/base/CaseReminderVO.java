package com.lawfirm.model.cases.vo.base;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CaseReminderVO {
    
    private Long id;
    
    private Long caseId;
    
    private String title;
    
    private String content;
    
    private LocalDateTime reminderTime;
    
    private String reminderType;
    
    private String status;
    
    private String priority;
    
    private String assigneeId;
    
    private String assigneeName;
    
    private String creatorId;
    
    private String creatorName;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 