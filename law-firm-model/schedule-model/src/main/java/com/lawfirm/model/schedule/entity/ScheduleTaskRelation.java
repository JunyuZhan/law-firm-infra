package com.lawfirm.model.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 日程与任务关联实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("schedule_task_relation")
public class ScheduleTaskRelation extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 日程ID
     */
    @TableField("schedule_id")
    private Long scheduleId;
    
    /**
     * 任务ID
     */
    @TableField("task_id")
    private Long taskId;
    
    /**
     * 关联描述
     */
    @TableField("description")
    private String description;
} 