package com.lawfirm.model.schedule.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 日程与任务关联DTO
 */
@Data
@Accessors(chain = true)
public class ScheduleTaskRelationDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 日程ID
     */
    private Long scheduleId;
    
    /**
     * 任务ID
     */
    private Long taskId;
    
    /**
     * 关联描述
     */
    private String description;
} 