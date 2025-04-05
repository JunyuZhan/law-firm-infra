package com.lawfirm.model.schedule.vo;

import com.lawfirm.model.schedule.entity.enums.ParticipantType;
import com.lawfirm.model.schedule.entity.enums.ResponseStatus;
import lombok.Data;

/**
 * 日程参与者VO类
 */
@Data
public class ScheduleParticipantVO {
    
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
    private Long participantId;
    
    /**
     * 参与者名称
     */
    private String participantName;
    
    /**
     * 参与者头像
     */
    private String participantAvatar;
    
    /**
     * 参与者部门
     */
    private String participantDepartment;
    
    /**
     * 参与者职位
     */
    private String participantPosition;
    
    /**
     * 参与者类型（组织者、必要参与者、可选参与者）
     */
    private ParticipantType participantType;
    
    /**
     * 参与者类型描述
     */
    private String participantTypeDesc;
    
    /**
     * 响应状态（接受、拒绝、未回复）
     */
    private ResponseStatus responseStatus;
    
    /**
     * 响应状态描述
     */
    private String responseStatusDesc;
    
    /**
     * 回复意见
     */
    private String comments;
} 