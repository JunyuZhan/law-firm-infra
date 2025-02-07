package com.lawfirm.schedule.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleDTO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 日程标题
     */
    private String title;
    
    /**
     * 日程内容
     */
    private String content;
    
    /**
     * 日程类型(COURT-庭审/MEETING-会见)
     */
    private String type;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 地点
     */
    private String location;
    
    /**
     * 参与人员
     */
    private String participants;
    
    /**
     * 提醒时间(分钟)
     */
    private Integer reminderTime;
    
    /**
     * 部门ID
     */
    private Long deptId;
    
    /**
     * 日程状态（0-未开始，1-进行中，2-已完成，3-已取消）
     */
    private Integer status;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 参与人ID列表，逗号分隔
     */
    private String participantIds;
} 