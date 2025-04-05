package com.lawfirm.model.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.schedule.entity.enums.ParticipantType;
import com.lawfirm.model.schedule.entity.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 日程参与者实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("schedule_participant")
public class ScheduleParticipant extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 关联的日程ID
     */
    @TableField("schedule_id")
    private Long scheduleId;
    
    /**
     * 参与者ID（用户ID）
     */
    @TableField("participant_id")
    private Long participantId;
    
    /**
     * 参与者类型（组织者、必要参与者、可选参与者）
     */
    @TableField("participant_type")
    private Integer participantType;
    
    /**
     * 响应状态（接受、拒绝、未回复）
     */
    @TableField("response_status")
    private Integer responseStatus;
    
    /**
     * 回复意见
     */
    @TableField("comments")
    private String comments;
} 