package com.lawfirm.model.schedule.dto;

import com.lawfirm.model.schedule.entity.enums.ParticipantType;
import com.lawfirm.model.schedule.entity.enums.ResponseStatus;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * 日程参与者DTO类
 */
@Data
public class ScheduleParticipantDTO {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 关联的日程ID
     */
    private Long scheduleId;
    
    /**
     * 参与者ID（用户ID）
     */
    @NotNull(message = "参与者ID不能为空")
    private Long participantId;
    
    /**
     * 参与者名称
     */
    private String participantName;
    
    /**
     * 参与者类型（组织者、必要参与者、可选参与者）
     */
    @NotNull(message = "参与者类型不能为空")
    private ParticipantType participantType;
    
    /**
     * 响应状态（接受、拒绝、未回复）
     */
    private ResponseStatus responseStatus;
    
    /**
     * 回复意见
     */
    private String comments;
} 