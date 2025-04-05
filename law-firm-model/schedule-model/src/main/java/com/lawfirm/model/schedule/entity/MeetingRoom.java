package com.lawfirm.model.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 会议室实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("schedule_meeting_room")
public class MeetingRoom extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 会议室名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 位置
     */
    @TableField("location")
    private String location;
    
    /**
     * 容量（人数）
     */
    @TableField("capacity")
    private Integer capacity;
    
    /**
     * 设施（JSON格式，例如投影仪、白板等）
     */
    @TableField("facilities")
    private String facilities;
    
    /**
     * 状态（可用、维护中、停用）
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 会议室类型
     */
    @TableField("type")
    private Integer type;
    
    /**
     * 管理员ID
     */
    @TableField("manager_id")
    private Long managerId;
    
    /**
     * 描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 楼层
     */
    @TableField("floor")
    private String floor;
} 