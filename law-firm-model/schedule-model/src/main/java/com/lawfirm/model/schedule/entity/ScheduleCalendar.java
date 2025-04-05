package com.lawfirm.model.schedule.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 日历实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("schedule_calendar")
public class ScheduleCalendar extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日历名称
     */
    private String name;

    /**
     * 日历描述
     */
    private String description;

    /**
     * 颜色（十六进制颜色码）
     */
    private String color;

    /**
     * 所有者用户ID
     */
    private Long userId;

    /**
     * 日历类型：1-个人日历，2-共享日历，3-订阅日历
     */
    private Integer type;

    /**
     * 可见性：1-私密，2-共享给特定人，3-公开
     */
    private Integer visibility;

    /**
     * 是否默认日历
     */
    private Boolean isDefault;
} 