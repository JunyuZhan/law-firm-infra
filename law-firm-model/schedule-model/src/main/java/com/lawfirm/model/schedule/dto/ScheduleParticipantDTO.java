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

    /**
     * 兼容getUserId方法，与旧代码兼容
     */
    public Long getUserId() {
        return this.participantId;
    }
    
    /**
     * 兼容getRole方法，与旧代码兼容
     * @return 参与者类型的代码值
     */
    public Integer getRole() {
        return this.participantType != null ? this.participantType.getCode() : null;
    }
    
    /**
     * 兼容getStatus方法，与旧代码兼容
     * @return 响应状态的代码值
     */
    public Integer getStatus() {
        return this.responseStatus != null ? this.responseStatus.getCode() : null;
    }
} 