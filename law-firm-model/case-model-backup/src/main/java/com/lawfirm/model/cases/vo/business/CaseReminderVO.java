package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.reminder.ReminderStatusEnum;
import com.lawfirm.model.cases.enums.reminder.ReminderTypeEnum;
import com.lawfirm.model.cases.enums.reminder.RepeatTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaseReminderVO {

    private Long id;
    
    private Long caseId;
    
    private ReminderTypeEnum type;
    
    private String title;
    
    private String content;
    
    private LocalDateTime reminderTime;
    
    private Integer advanceTime;
    
    private String receiverId;
    
    private String receiverName;
    
    private ReminderStatusEnum status;
    
    private LocalDateTime confirmTime;
    
    private String confirmRemark;
    
    private Boolean repeat;
    
    private RepeatTypeEnum repeatType;
    
    private String repeatRule;
    
    private LocalDateTime repeatEndTime;
    
    private Boolean sent;
    
    private Integer priority;
    
    private Boolean expired;
    
    private String remark;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 