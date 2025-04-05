package com.lawfirm.model.schedule.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日程事件实体类
 */
@Data
@Accessors(chain = true)
@TableName("schedule_event")
public class ScheduleEvent implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 事件ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 日程ID
     */
    private Long scheduleId;
    
    /**
     * 事件类型
     * 1: 创建日程
     * 2: 更新日程
     * 3: 取消日程
     * 4: 完成日程
     * 5: 添加参与者
     * 6: 移除参与者
     * 7: 响应邀请
     * 8: 会议室预订确认
     * 9: 日程提醒
     * 10: 其他
     */
    private Integer eventType;
    
    /**
     * 操作用户ID
     */
    private Long operatorId;
    
    /**
     * 操作用户名称
     */
    private String operatorName;
    
    /**
     * 事件内容（JSON格式）
     */
    private String content;
    
    /**
     * 事件备注
     */
    private String remarks;
    
    /**
     * 是否通知
     * 0: 不通知
     * 1: 通知
     */
    private Integer notifyFlag;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}