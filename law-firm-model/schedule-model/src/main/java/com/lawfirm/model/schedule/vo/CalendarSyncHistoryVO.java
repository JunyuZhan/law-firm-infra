package com.lawfirm.model.schedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日历同步历史VO
 */
@Data
@Schema(description = "日历同步历史VO")
public class CalendarSyncHistoryVO {

    @Schema(description = "同步历史ID")
    private Long id;

    @Schema(description = "外部日历ID")
    private Long externalCalendarId;
    
    @Schema(description = "外部日历名称")
    private String calendarName;

    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "同步方向：1-导入, 2-导出, 3-双向")
    private Integer direction;
    
    @Schema(description = "同步方向名称")
    private String directionName;
    
    @Schema(description = "同步开始时间")
    private LocalDateTime startTime;
    
    @Schema(description = "同步结束时间")
    private LocalDateTime endTime;
    
    @Schema(description = "同步状态：1-成功, 2-部分成功, 3-失败")
    private Integer status;
    
    @Schema(description = "状态名称")
    private String statusName;
    
    @Schema(description = "添加的事件数")
    private Integer addedCount;
    
    @Schema(description = "更新的事件数")
    private Integer updatedCount;
    
    @Schema(description = "删除的事件数")
    private Integer deletedCount;
    
    @Schema(description = "失败的事件数")
    private Integer failedCount;
    
    @Schema(description = "错误信息")
    private String errorMessage;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
} 