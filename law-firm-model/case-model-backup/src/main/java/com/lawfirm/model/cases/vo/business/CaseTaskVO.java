package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.task.TaskPriorityEnum;
import com.lawfirm.model.cases.enums.task.TaskStatusEnum;
import com.lawfirm.model.cases.enums.task.TaskTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CaseTaskVO {

    private Long id;
    
    private Long caseId;
    
    private TaskTypeEnum type;
    
    private String title;
    
    private String description;
    
    private TaskPriorityEnum priority;
    
    private TaskStatusEnum status;
    
    private String assignerId;
    
    private String assignerName;
    
    private String assigneeId;
    
    private String assigneeName;
    
    private List<String> watchers;
    
    private LocalDateTime startTime;
    
    private LocalDateTime dueTime;
    
    private LocalDateTime completedTime;
    
    private Integer progress;
    
    private String result;
    
    private List<String> attachments;
    
    private List<String> dependencies;
    
    private List<String> subtasks;
    
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