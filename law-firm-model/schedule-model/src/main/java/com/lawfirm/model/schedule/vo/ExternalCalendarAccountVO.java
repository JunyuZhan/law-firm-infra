package com.lawfirm.model.schedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 外部日历账号VO
 */
@Data
@Schema(description = "外部日历账号VO")
public class ExternalCalendarAccountVO {

    @Schema(description = "账号ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "账号类型")
    private String type;

    @Schema(description = "账号类型名称")
    private String typeName;

    @Schema(description = "账号邮箱/名称")
    private String accountEmail;
    
    @Schema(description = "连接状态：1-正常, 2-授权过期, 3-已断开")
    private Integer status;
    
    @Schema(description = "状态名称")
    private String statusName;

    @Schema(description = "上次同步时间")
    private LocalDateTime lastSyncTime;
    
    @Schema(description = "日历数量")
    private Integer calendarCount;
    
    @Schema(description = "是否自动同步")
    private Boolean autoSync;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 