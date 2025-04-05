package com.lawfirm.model.schedule.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 日程与任务关联VO
 */
@Data
@Accessors(chain = true)
public class ScheduleTaskRelationVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 关联ID
     */
    private Long id;
    
    /**
     * 日程ID
     */
    private Long scheduleId;
    
    /**
     * 任务ID
     */
    private Long taskId;
    
    /**
     * 任务标题
     */
    private String taskTitle;
    
    /**
     * 任务编号
     */
    private String taskNumber;
    
    /**
     * 任务优先级
     */
    private Integer taskPriority;
    
    /**
     * 任务状态
     */
    private Integer taskStatus;
    
    /**
     * 关联描述
     */
    private String description;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 任务详细信息
     * 存储从任务模块获取的数据
     * 不参与序列化过程
     */
    private transient Map<String, Object> taskInfo;
} 