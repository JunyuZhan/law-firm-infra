package com.lawfirm.model.schedule.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日程冲突信息值对象
 */
@Data
@Accessors(chain = true)
public class ScheduleConflictVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 冲突类型
     * 1: 日程冲突
     * 2: 事件冲突
     * 3: 会议室预订冲突
     */
    private Integer conflictType;
    
    /**
     * 冲突对象ID
     * 根据冲突类型，可能是日程ID、事件ID或会议室预订ID
     */
    private Long conflictObjectId;
    
    /**
     * 冲突对象标题
     */
    private String title;
    
    /**
     * 冲突的开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 冲突的结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 冲突对象类型
     * 根据冲突类型，可能是日程类型、事件类型或预订类型
     */
    private Integer objectType;
    
    /**
     * 冲突对象所有者ID
     * 日程和事件的拥有者用户ID
     */
    private Long ownerId;
    
    /**
     * 冲突对象所有者名称
     */
    private String ownerName;
    
    /**
     * 位置信息
     */
    private String location;
    
    /**
     * 冲突描述
     */
    private String conflictDescription;
    
    /**
     * 冲突重叠时长（分钟）
     */
    private Integer overlapMinutes;
    
    /**
     * 冲突严重程度
     * 1: 轻微 - 只有少量时间重叠
     * 2: 中等 - 部分时间重叠
     * 3: 严重 - 完全覆盖或大部分时间重叠
     */
    private Integer severityLevel;
} 