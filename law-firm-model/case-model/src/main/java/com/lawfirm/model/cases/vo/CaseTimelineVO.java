package com.lawfirm.model.cases.vo;

import com.lawfirm.model.cases.enums.TimelineEventTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CaseTimelineVO {

    private Long id;
    
    private Long caseId;
    
    private TimelineEventTypeEnum type;
    
    private String title;
    
    private String description;
    
    private LocalDateTime eventTime;
    
    private String operatorId;
    
    private String operatorName;
    
    private String objectType;
    
    private String objectId;
    
    private String objectName;
    
    private String oldValue;
    
    private String newValue;
    
    private List<String> attachments;
    
    private String remark;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 