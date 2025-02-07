package com.lawfirm.schedule.model.query;

import com.lawfirm.model.base.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleQuery extends PageQuery {
    
    /**
     * 日程标题
     */
    private String title;
    
    /**
     * 日程类型(COURT-庭审/MEETING-会见)
     */
    private String type;
    
    /**
     * 开始时间-起始
     */
    private LocalDateTime startTimeBegin;
    
    /**
     * 开始时间-结束
     */
    private LocalDateTime startTimeEnd;
    
    /**
     * 创建人ID
     */
    private Long creatorId;
    
    /**
     * 部门ID
     */
    private Long deptId;
    
    /**
     * 日程状态（0-未开始，1-进行中，2-已完成，3-已取消）
     */
    private Integer status;
} 