package com.lawfirm.model.schedule.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会议室预订数据传输对象
 */
@Data
@Accessors(chain = true)
public class MeetingRoomBookingDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 会议室ID
     */
    @NotNull(message = "会议室ID不能为空")
    private Long meetingRoomId;
    
    /**
     * 关联的日程ID
     */
    private Long scheduleId;
    
    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;
    
    /**
     * 会议标题
     */
    @NotBlank(message = "会议标题不能为空")
    @Size(max = 100, message = "会议标题长度不能超过100个字符")
    private String title;
    
    /**
     * 会议描述
     */
    @Size(max = 500, message = "会议描述长度不能超过500个字符")
    private String description;
    
    /**
     * 预订类型
     * 1: 常规会议
     * 2: 视频会议
     * 3: 培训
     * 4: 面试
     */
    @NotNull(message = "预订类型不能为空")
    private Integer bookingType;
    
    /**
     * 预计参会人数
     */
    private Integer attendeeCount;
    
    /**
     * 需要的设备
     */
    @Size(max = 255, message = "设备描述长度不能超过255个字符")
    private String equipment;
    
    /**
     * 备注
     */
    @Size(max = 255, message = "备注长度不能超过255个字符")
    private String remarks;
} 