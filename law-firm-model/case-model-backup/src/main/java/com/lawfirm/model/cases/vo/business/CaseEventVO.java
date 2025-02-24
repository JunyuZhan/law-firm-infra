package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.event.EventStatusEnum;
import com.lawfirm.model.cases.enums.event.EventTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseEventVO extends BaseVO {

    private Long caseId;
    
    private EventTypeEnum type;
    
    private String title;
    
    private String description;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private String location;
    
    private String organizer;
    
    private List<String> participants;
    
    private EventStatusEnum eventStatus;
    
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
}