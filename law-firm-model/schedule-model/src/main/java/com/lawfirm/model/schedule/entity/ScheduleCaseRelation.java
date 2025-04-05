package com.lawfirm.model.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 日程与案件关联实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("schedule_case_relation")
public class ScheduleCaseRelation extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 日程ID
     */
    @TableField("schedule_id")
    private Long scheduleId;
    
    /**
     * 案件ID
     */
    @TableField("case_id")
    private Long caseId;
    
    /**
     * 关联描述
     */
    @TableField("description")
    private String description;
} 