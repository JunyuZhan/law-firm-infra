package com.lawfirm.model.schedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日历VO
 */
@Data
@Schema(description = "日历VO")
public class ScheduleCalendarVO {

    @Schema(description = "日历ID")
    private Long id;

    @Schema(description = "日历名称")
    private String name;

    @Schema(description = "日历描述")
    private String description;

    @Schema(description = "颜色（十六进制颜色码）")
    private String color;

    @Schema(description = "所有者用户ID")
    private Long userId;

    @Schema(description = "所有者用户名")
    private String username;

    @Schema(description = "日历类型：1-个人日历，2-共享日历，3-订阅日历")
    private Integer type;

    @Schema(description = "日历类型名称")
    private String typeName;

    @Schema(description = "可见性：1-私密，2-共享给特定人，3-公开")
    private Integer visibility;

    @Schema(description = "可见性名称")
    private String visibilityName;

    @Schema(description = "日程数量")
    private Integer scheduleCount;

    @Schema(description = "是否默认日历")
    private Boolean isDefault;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 