package com.lawfirm.model.schedule.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 日程与案件关联DTO
 */
@Data
@Accessors(chain = true)
public class ScheduleCaseRelationDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 日程ID
     */
    private Long scheduleId;
    
    /**
     * 案件ID
     */
    private Long caseId;
    
    /**
     * 关联描述
     */
    private String description;
} 